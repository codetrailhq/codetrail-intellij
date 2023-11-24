package io.codetrail.codetrailintellij.rpc.extension;

public class DeleteAnnotationRequest extends RPCRequest {
    private DeleteAnnotationRequestPayload payload;

    public DeleteAnnotationRequest(DeleteAnnotationRequestPayload deleteAnnotationRequestPayload) {
        this.payload = deleteAnnotationRequestPayload;
    }

    @Override
    public String getAction() {
        return "deleteAnnotation";
    }

    @Override
    public Object getPayload() {
        return payload;
    }
}
