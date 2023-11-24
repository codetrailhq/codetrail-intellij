package io.codetrail.codetrailintellij.rpc.extension;

public class EditAnnotationRequestPayload {
    private String sessionId;
    private String annotationId;

    public EditAnnotationRequestPayload(String sessionId, String annotationId) {
        this.sessionId = sessionId;
        this.annotationId = annotationId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(String annotationId) {
        this.annotationId = annotationId;
    }
}
