package io.codetrail.codetrailintellij;

import com.intellij.openapi.diagnostic.Logger;
import io.codetrail.codetrailintellij.rpc.*;
import org.jetbrains.ide.BuiltInServerManager;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExtensionService {
    private static ExtensionService instance;
    private static final Logger log = Logger.getInstance(ExtensionService.class.getName());

    private boolean connectedToDesktop = false;
    private String sessionId;

    public static ExtensionService getInstance() {
        if (instance == null) {
            instance = new ExtensionService();
        }

        return instance;
    }

    public void activate(String projectPath) {
        if (!connectedToDesktop) {
            log.info("not connected to desktop companion, trying to connect");
            connectToDesktop(projectPath);
        }
    }

    private void connectToDesktop(String projectPath) {
        int port = BuiltInServerManager.getInstance().getPort();
        RPCRequest req = new RPCRequest("connectIDE", new ConnectIDERequestPayload("", "intellij", port, projectPath));
        log.info("connecting to desktop companion communicating ide port " + port + " and project path " + projectPath);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        int retries = 0;
        while (retries < 5) {
            retries++;

            RequestRunner runner = new RequestRunner(req, new ConnectionConfiguration("http://localhost", 31545));
            Future<ConnectIDEResponse> r = executorService.submit(runner);

            try {
                ConnectIDEResponse resp = r.get();

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
        }
    }
}
