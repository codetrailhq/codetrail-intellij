package io.codetrail.codetrailintellij.rpc.extension;

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
