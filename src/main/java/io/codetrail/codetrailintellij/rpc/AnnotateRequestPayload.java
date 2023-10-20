package io.codetrail.codetrailintellij.rpc;

import io.codetrail.codetrailintellij.annotation.AnnotationLocation;

public class AnnotateRequestPayload {
    private String sessionId;
    private String codebasePath;
    private AnnotationLocation location;
    private String selectedText;

    AnnotateRequestPayload(String sessionId, String codebasePath, AnnotationLocation location, String selectedText) {
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

    public String getSelectedText() {
        return selectedText;
    }
}
