package io.codetrail.codetrailintellij.rpc.extension;

public class AnnotateRequest extends RPCRequest {
    private AnnotateRequestPayload payload;

    public AnnotateRequest() {

    }

    public AnnotateRequest(AnnotateRequestPayload payload) {
        this.payload = payload;
    }

    @Override
    public String getAction() {
        return "annotate";
    }

    @Override
    public AnnotateRequestPayload getPayload() {
        return payload;
    }

    public void setPayload(AnnotateRequestPayload payload) {
        this.payload = payload;
    }
}
