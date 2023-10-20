package io.codetrail.codetrailintellij.rpc;

public class LoadStoryRequestPayload {
    private String sessionId;
    private String storyId;

    LoadStoryRequestPayload(String sessionId, String storyId) {
        this.sessionId = sessionId;
        this.storyId = storyId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getStoryId() {
        return storyId;
    }
}
