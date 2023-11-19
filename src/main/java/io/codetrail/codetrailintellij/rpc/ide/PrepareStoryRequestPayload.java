package io.codetrail.codetrailintellij.rpc.ide;

import io.codetrail.codetrailintellij.story.IDEStory;

public class PrepareStoryRequestPayload {
    private String sessionId;
    private IDEStory story;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public IDEStory getStory() {
        return story;
    }

    public void setStory(IDEStory story) {
        this.story = story;
    }
}
