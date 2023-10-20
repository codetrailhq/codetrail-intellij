package io.codetrail.codetrailintellij.rpc;
public class RPCRequest<T>  {
    private String action;
    private T payload;

    public RPCRequest(String action, T payload) {
        this.action = action;
        this.payload = payload;
    }

    public String getAction() {
        return action;
    }

    public T getPayload() {
        return payload;
    }
}