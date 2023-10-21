package io.codetrail.codetrailintellij.rpc;

public class ConnectIDEResponse {
    private String sessionId;

    public ConnectIDEResponse(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
