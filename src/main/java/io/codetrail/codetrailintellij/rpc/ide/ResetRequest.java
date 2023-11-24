package io.codetrail.codetrailintellij.rpc.ide;

public class ResetRequest extends RPCIDERequest {

    @Override
    public String getAction() {
        return "reset";
    }
}
