package io.codetrail.codetrailintellij.rpc.ide;

public class PrepareStoryRequest extends RPCIDERequest {
    @Override
    public String getAction() {
        return "prepareStory";
    }

    private PrepareStoryRequestPayload payload;

    public PrepareStoryRequestPayload getPayload() {
        return payload;
    }

    public void setPayload(PrepareStoryRequestPayload payload) {
        this.payload = payload;
    }
}
