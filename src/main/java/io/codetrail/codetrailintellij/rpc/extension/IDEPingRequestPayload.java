package io.codetrail.codetrailintellij.rpc.extension;

public class IDEPingRequestPayload {
    private String sessionId;
    private String ide;
    private int port;
    private String rpcPath;
    private String codebasePath;

    public IDEPingRequestPayload(String sessionId, String ide, int port, String rpcPath, String codebasePath) {
        this.sessionId = sessionId;
        this.ide = ide;
        this.port = port;
        this.rpcPath = rpcPath;
        this.codebasePath = codebasePath;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getIde() {
        return ide;
    }

    public int getPort() {
        return port;
    }

    public String getRpcPath() { return rpcPath; }

    public String getCodebasePath() {
        return codebasePath;
    }
}
