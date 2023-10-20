package io.codetrail.codetrailintellij.rpc;

public class DisconnectIDERequestPayload {
    private String sessionId;

    DisconnectIDERequestPayload(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
