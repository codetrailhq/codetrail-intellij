package io.codetrail.codetrailintellij.rpc;

public class IDEPingResponse extends RPCResponse {
    private IDEPingResponseData data;
    private String action;
    private RPCResponseError error;

    public IDEPingResponse() {
    }

    @Override
    public IDEPingResponseData getData() {
        return data;
    }

    public void setData(IDEPingResponseData data) {
        this.data = data;
    }

    public void setError(RPCResponseError error) {
        this.error = error;
    }

    @Override
    public RPCResponseError getError() {
        return error;
    }

    @Override
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

