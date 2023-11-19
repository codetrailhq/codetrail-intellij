package io.codetrail.codetrailintellij.rpc.extension;

import io.codetrail.codetrailintellij.annotation.AnnotationLocation;
import io.codetrail.codetrailintellij.annotation.AnnotationSelectedText;

public class AnnotateRequestPayload {
    private String sessionId;
    private String codebasePath;
    private AnnotationLocation location;
    private AnnotationSelectedText selectedText;

    public AnnotateRequestPayload(String sessionId, String codebasePath, AnnotationLocation location, AnnotationSelectedText selectedText) {
        this.sessionId = sessionId;
        this.codebasePath = codebasePath;
        this.location = location;
        this.selectedText = selectedText;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getCodebasePath() {
        return codebasePath;
    }

    public AnnotationLocation getLocation() {
        return location;
    }

    public AnnotationSelectedText getSelectedText() {
        return selectedText;
    }
}
