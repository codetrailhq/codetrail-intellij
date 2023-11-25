package io.codetrail.codetrailintellij;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import io.codetrail.codetrailintellij.annotation.Annotation;
import io.codetrail.codetrailintellij.annotation.AnnotationLocation;
import io.codetrail.codetrailintellij.annotation.AnnotationSelectedText;
import io.codetrail.codetrailintellij.annotation.ui.EditorAnnotationManager;
import io.codetrail.codetrailintellij.rpc.ConnectionConfiguration;
import io.codetrail.codetrailintellij.rpc.extension.*;
import io.codetrail.codetrailintellij.story.IDEStory;
import org.jetbrains.ide.BuiltInServerManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExtensionService {
    private static ExtensionService instance;
    private static final Logger log = Logger.getInstance(ExtensionService.class.getName());

    private boolean connectedToDesktop = false;
    private String sessionId;
    private ConnectionConfiguration connection;

    private static int IDE_PING_INTERVAL = 1000 * 5;

    private Project currentProject;
    private EditorAnnotationManager annotationManager;

    private boolean isEditing = false;

    public static ExtensionService getInstance() {
        if (instance == null) {
            instance = new ExtensionService();
        }

        return instance;
    }

    public void annotate(AnnotationLocation location, String codebasePath, AnnotationSelectedText selectedText) {
        if (!connectedToDesktop) {
            log.info("not connected to desktop companion");
            dialogWithWarning("Not connected to desktop companion", "Please start CodeTrail before writing an annotation!");
            return;
        }

        RPCRequest req = new AnnotateRequest(new AnnotateRequestPayload(sessionId, codebasePath, location, selectedText));

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        RequestRunner runner = new RequestRunner(req, connection);
        Future<AnnotateResponse> r = executorService.submit(runner);

        isEditing = true;

        try {
            AnnotateResponse resp = r.get();

            if (resp == null) {
                log.warn("AnnotateResponse is null");
            } else if (resp.getError() != null) {
                log.warn("could not annotate due to error");
            }
        } catch (Exception e) {
            log.warn("failed to annotate");
            log.warn(e);
        }
    }

    public void activate(Project project, String projectPath) {
        this.sessionId = generateSessionId();

        if (!connectedToDesktop) {
            log.info("not connected to desktop companion, trying to connect");
            connectToDesktop(projectPath);
            stayConnectedToDesktop(projectPath);
        }

        currentProject = project;
        annotationManager = new EditorAnnotationManager(currentProject);
    }

    public void addAnnotation(Annotation annotation) {
        if (!isEditing) {
            isEditing = true;
        }

        annotationManager.displayRecordedAnnotation(annotation);
    }

    public void removeAnnotation(String annotationId) {
        annotationManager.clearAnnotation(annotationId);
    }

    public void reset() {
        annotationManager.clearAnnotations();
        isEditing = false;
    }

    public void displayStory(IDEStory story) {
        if (!connectedToDesktop) {
            log.info("not connected to desktop companion");
            dialogWithWarning("Not connected to desktop companion", "Please start CodeTrail before playing a story!");
            return;
        }

        annotationManager.displayAnnotationsForStory(story);
    }

    public void editAnnotation(Annotation annotation) {
        RPCRequest req = new EditAnnotationRequest(new EditAnnotationRequestPayload(sessionId, annotation.getId()));

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        RequestRunner runner = new RequestRunner(req, connection);
        Future<EditAnnotationResponse> r = executorService.submit(runner);

        isEditing = true;

        try {
            EditAnnotationResponse resp = r.get();

            if (resp == null) {
                log.warn("EditAnnotationResponse is null");
            } else if (resp.getError() != null) {
                log.warn("could not edit annotation due to error");
            }
        } catch (Exception e) {
            log.warn("failed to edit annotation");
            log.warn(e);
        }
    }

    public void deleteAnnotation(Annotation annotation) {
        RPCRequest req = new DeleteAnnotationRequest(new DeleteAnnotationRequestPayload(sessionId, annotation.getId()));

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        RequestRunner runner = new RequestRunner(req, connection);
        Future<DeleteAnnotationResponse> r = executorService.submit(runner);

        isEditing = true;

        try {
            DeleteAnnotationResponse resp = r.get();

            if (resp == null) {
                log.warn("DeleteAnnotationResponse is null");
            } else if (resp.getError() != null) {
                log.warn("could not delete annotation due to error");
            }
        } catch (Exception e) {
            log.warn("failed to delete annotation");
            log.warn(e);
        }
    }

    /**
     * We need to keep sending ide_ping to desktop companion to keep the connection alive.
     * FIXME: this needs to be more stable, the desktop companion could be quit at any time
     *
     * @param projectPath
     */
    private void stayConnectedToDesktop(String projectPath) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                log.info("sending ide_ping to desktop companion");
                connectToDesktop(projectPath);
            }
        }, IDE_PING_INTERVAL, IDE_PING_INTERVAL);
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    private String getIDEName() {
        String productName = ApplicationInfo.getInstance().getFullApplicationName();

        if (productName.startsWith("IntelliJ IDEA")) {
            return "jetbrainsIntelliJUltimate";
        }

        if (productName.startsWith("GoLand")) {
            return "jetbrainsGoLand";
        }

        throw new RuntimeException("unknown ide name");
    }

    private String getVersion() {
        // Read resources/version.properties
        String version = "";
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("version.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            version = properties.getProperty("version");
        } catch (IOException e) {
            log.warn("could not read version.properties");
            log.warn(e);
        }

        return version;
    }

    private void connectToDesktop(String projectPath) {
        int port = BuiltInServerManager.getInstance().getPort();
        String path = "/api_codetrail";
        RPCRequest req = new IDEPingRequest(new IDEPingRequestPayload(sessionId, getIDEName(), getVersion(), port, path, projectPath));
        log.info("connecting to desktop companion communicating ide port " + port + " and project path " + projectPath);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        connection = new ConnectionConfiguration("http://localhost", 31545);

        int retries = 0;
        while (retries < 5) {
            retries++;

            RequestRunner runner = new RequestRunner(req, connection);
            Future<IDEPingResponse> r = executorService.submit(runner);

            try {
                IDEPingResponse resp = r.get();

                if (resp == null) {
                    log.warn("failed to connect to desktop companion (empty response), retrying");
                    connectedToDesktop = false;
                    continue;
                }

                this.connectedToDesktop = true;
                log.info("connected to desktop companion!!!");
                break;
            } catch (InterruptedException e) {
                log.warn("interrupted, failed to connect to desktop companion");
                break;
            } catch (ExecutionException e) {
                log.warn("execution whatever, could not connect to desktop companion, retrying");
                log.warn(e);
            } catch (Exception e) {
                log.warn("general exception, could not connect to desktop companion, retrying");
                log.warn(e);
            }
        }

        executorService.shutdown();
        if (!connectedToDesktop) {
            log.warn("failed to connect to desktop companion");
            notifyWithWarning("Could not connect", "Could not connect to the desktop companion!");
        }
    }

    public void notifyWithWarning(String title, String message) {
        Notification notification = new Notification("io.codetrail.codetrail-intellij",
                title,
                message,
                NotificationType.WARNING);
        Notifications.Bus.notify(notification, currentProject);
    }

    public void dialogWithWarning(String title, String message) {
        Messages.showWarningDialog(currentProject, message, title);
    }

    public void nextAnnotation(Annotation viewedAnnotation) {
        annotationManager.displayNextAnnotation(viewedAnnotation);
    }

    public void previousAnnotation(Annotation annotation) {
        annotationManager.displayPreviousAnnotation(annotation);
    }
}
