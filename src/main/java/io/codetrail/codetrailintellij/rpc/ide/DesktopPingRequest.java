package io.codetrail.codetrailintellij.rpc.ide;

public class DesktopPingRequest extends RPCIDERequest {

    @Override
    public String getAction() {
        return "desktopPing";
    }
}
