package io.codetrail.codetrailintellij.rpc.ide;

public class DisplayRecordedAnnotationRequest extends RPCIDERequest {
    private DisplayRecordedAnnotationRequestPayload payload;

    @Override public String getAction() {
        return "displayRecordedAnnotation";
    }

    public DisplayRecordedAnnotationRequestPayload getPayload() {
        return payload;
    }

    public void setPayload(DisplayRecordedAnnotationRequestPayload payload) {
        this.payload = payload;
    }
}
