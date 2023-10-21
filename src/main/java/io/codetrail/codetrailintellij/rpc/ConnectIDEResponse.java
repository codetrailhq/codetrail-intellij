package io.codetrail.codetrailintellij.rpc;

public class ConnectIDEResponse extends RPCResponse {
    private ConnectIDEResponseData data;
    private String action;
    private RPCResponseError error;

    public ConnectIDEResponse() {
    }

    @Override
    public ConnectIDEResponseData getData() {
        return data;
    }

    public void setData(ConnectIDEResponseData data) {
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

