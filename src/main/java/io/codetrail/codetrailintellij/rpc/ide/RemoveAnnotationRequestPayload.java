package io.codetrail.codetrailintellij.rpc.ide;

public class RemoveAnnotationRequestPayload {
    private String annotationId;
    private String sessionId;

    public RemoveAnnotationRequestPayload(String annotationId) {
        this.annotationId = annotationId;
    }

    public RemoveAnnotationRequestPayload() {
    }

    public String getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(String annotationId) {
        this.annotationId = annotationId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
