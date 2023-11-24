package io.codetrail.codetrailintellij.rpc.extension;

public class DeleteAnnotationResponse extends RPCResponse {
    @Override
    public String getAction() {
        return "deleteAnnotation";
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
