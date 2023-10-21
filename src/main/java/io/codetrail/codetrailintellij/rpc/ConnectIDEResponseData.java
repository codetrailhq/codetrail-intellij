package io.codetrail.codetrailintellij.rpc;

public class ConnectIDEResponseData {
    private String sessionId;

    public ConnectIDEResponseData() {}

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
