package io.codetrail.codetrailintellij.rpc.ide;

import io.codetrail.codetrailintellij.annotation.Annotation;
import io.codetrail.codetrailintellij.story.IDEStory;

public class DisplayRecordedAnnotationRequestPayload {
    private String sessionId;
    private Annotation annotation;
    private IDEStory draftStory;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public IDEStory getDraftStory() {
        return draftStory;
    }

    public void setDraftStory(IDEStory draftStory) {
        this.draftStory = draftStory;
    }
}
