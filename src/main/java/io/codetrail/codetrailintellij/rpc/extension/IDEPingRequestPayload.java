package io.codetrail.codetrailintellij.rpc.extension;

public class IDEPingRequestPayload {
    private String sessionId;
    private String ide;
    private int port;
    private String codebasePath;

    public IDEPingRequestPayload(String sessionId, String ide, int port, String codebasePath) {
        this.sessionId = sessionId;
        this.ide = ide;
        this.port = port;
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

    public String getCodebasePath() {
        return codebasePath;
    }
}