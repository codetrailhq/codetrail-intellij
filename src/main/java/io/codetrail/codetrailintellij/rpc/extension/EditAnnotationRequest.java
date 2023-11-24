package io.codetrail.codetrailintellij.rpc.extension;

public class EditAnnotationRequest extends RPCRequest {
    private EditAnnotationRequestPayload payload;

    public EditAnnotationRequest(EditAnnotationRequestPayload editAnnotationRequestPayload) {
        this.payload = editAnnotationRequestPayload;
    }

    @Override
    public String getAction() {
        return "editAnnotation";
    }

    @Override
    public Object getPayload() {
        return payload;
    }
}
