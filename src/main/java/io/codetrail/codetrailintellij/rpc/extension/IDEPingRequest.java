package io.codetrail.codetrailintellij.rpc.extension;

public class IDEPingRequest extends RPCRequest {
    private IDEPingRequestPayload payload;

    public IDEPingRequest() {

    }

    public IDEPingRequest(IDEPingRequestPayload payload) {
        this.payload = payload;
    }

    @Override
    public String getAction() {
        return "idePing";
    }

    @Override
    public IDEPingRequestPayload getPayload() {
        return this.payload;
    }

    public void setPayload(IDEPingRequestPayload payload) {
        this.payload = payload;
    }
}
