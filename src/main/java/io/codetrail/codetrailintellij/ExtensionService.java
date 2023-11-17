package io.codetrail.codetrailintellij;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import io.codetrail.codetrailintellij.annotation.AnnotationLocation;
import io.codetrail.codetrailintellij.rpc.*;
import io.codetrail.codetrailintellij.rpc.requests.*;
import org.jetbrains.ide.BuiltInServerManager;
import com.intellij.openapi.ui.Messages;

import java.util.Timer;
import java.util.TimerTask;
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

    public static ExtensionService getInstance() {
        if (instance == null) {
            instance = new ExtensionService();
        }

        return instance;
    }

    public void annotate(AnnotationLocation location, String codebasePath, String selectedText) {
        if (!connectedToDesktop) {
            log.info("not connected to desktop companion");
            dialogWithWarning("Not connected to desktop companion", "Please start CodeTrail before writing an annotation!");
            return;
        }

        RPCRequest req = new AnnotateRequest(new AnnotateRequestPayload(sessionId, codebasePath, location, selectedText));

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        RequestRunner runner = new RequestRunner(req, connection);
        Future<AnnotateResponse> r = executorService.submit(runner);

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
        if (!connectedToDesktop) {
            log.info("not connected to desktop companion, trying to connect");
            connectToDesktop(projectPath);
            stayConnectedToDesktop(projectPath);
        }

        currentProject = project;
    }

    /**
     * We need to keep sending ide_ping to desktop companion to keep the connection alive.
     * FIXME: this needs to be more stable, the desktop companion could be quit at any time
     * @param projectPath
     */
    private void stayConnectedToDesktop(String projectPath) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override public void run() {
                log.info("sending ide_ping to desktop companion");
                connectToDesktop(projectPath);
            }
        }, IDE_PING_INTERVAL, IDE_PING_INTERVAL);
    }

    private void connectToDesktop(String projectPath) {
        int port = BuiltInServerManager.getInstance().getPort();
        RPCRequest req = new IDEPingRequest(new IDEPingRequestPayload("my-own-session", "intellij", port, projectPath));
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
                    log.warn("failed to connect to desktop companion");
                    continue;
                }

                String sessionId = resp.getData().getSessionId();
                this.sessionId = sessionId;
                this.connectedToDesktop = true;
                log.info("connected to desktop companion!!!");
                break;
            } catch (InterruptedException e) {
                log.warn("interrupted, failed to connect to desktop companion");
            } catch (ExecutionException e) {
                log.warn("could not connect to desktop companion, retrying");
                log.warn(e);
            } catch (Exception e) {
                log.warn("could not connect to desktop companion, retrying");
                log.warn(e);
            }
        }

        executorService.shutdown();
        if (!connectedToDesktop) {
            log.warn("failed to connect to desktop companion");
            notifyWithWarning("Could not connect", "Could not connect to the desktop companion!");
        }
    }

    private void notifyWithWarning(String title, String message) {
        Notification notification = new Notification("io.codetrail.codetrail-intellij",
                title,
                message,
                NotificationType.WARNING);
        Notifications.Bus.notify(notification, currentProject);
    }

    private void dialogWithWarning(String title, String message) {
        Messages.showWarningDialog(currentProject, message, title);
    }
}
