package io.codetrail.codetrailintellij.rpc.extension;

public class EditAnnotationResponse extends RPCResponse {
    @Override
    public String getAction() {
        return "editAnnotation";
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public RPCResponseError getError() {
        return null;
    }
}
