package io.codetrail.codetrailintellij.rpc;

public class IDEPingResponseData {
    private String sessionId;

    public IDEPingResponseData() {}

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
