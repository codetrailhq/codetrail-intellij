package io.codetrail.codetrailintellij.rpc;

public class RPCResponse<T> {
    private String action;
    private T data;

    public RPCResponse(String action, T data) {
        this.action = action;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public T getData() {
        return data;
    }
}
