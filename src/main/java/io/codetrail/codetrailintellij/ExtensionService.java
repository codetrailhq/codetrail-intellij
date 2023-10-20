package io.codetrail.codetrailintellij;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import io.codetrail.codetrailintellij.rpc.ConnectIDERequestPayload;
import io.codetrail.codetrailintellij.rpc.ConnectionConfiguration;
import io.codetrail.codetrailintellij.rpc.RPCRequest;
import io.codetrail.codetrailintellij.rpc.RequestRunner;
import org.jetbrains.ide.BuiltInServerManager;

public class ExtensionService {
    private static ExtensionService instance;
    private static final Logger log = Logger.getInstance(ExtensionService.class.getName());

    private boolean connectedToDesktop = false;

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

        RequestRunner runner = new RequestRunner(req, new ConnectionConfiguration("http://localhost", 31545));
        Thread t = new Thread(runner);
        t.start();

        try {
            t.join();
            connectedToDesktop = true;
            log.info("connected to desktop companion");
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.warn("failed to connect to desktop companion");
        }
    }
}
