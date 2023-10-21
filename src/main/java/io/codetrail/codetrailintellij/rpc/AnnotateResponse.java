package io.codetrail.codetrailintellij.rpc;

public class AnnotateResponse extends RPCResponse {
    private String action;
    private Object data;
    private RPCResponseError error;

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public RPCResponseError getError() {
        return error;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setError(RPCResponseError error) {
        this.error = error;
    }
}
