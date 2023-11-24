package io.codetrail.codetrailintellij.rpc.ide;

public class RemoveAnnotationRequest extends RPCIDERequest {
    private RemoveAnnotationRequestPayload payload;

    @Override
    public String getAction() {
        return "removeAnnotation";
    }

    public RemoveAnnotationRequestPayload getPayload() {
        return payload;
    }

    public void setPayload(RemoveAnnotationRequestPayload payload) {
        this.payload = payload;
    }
}
