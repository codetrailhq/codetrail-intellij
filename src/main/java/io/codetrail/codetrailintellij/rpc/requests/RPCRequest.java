package io.codetrail.codetrailintellij.rpc.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.codetrail.codetrailintellij.rpc.AnnotateResponse;
import io.codetrail.codetrailintellij.rpc.IDEPingResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "action")
@JsonSubTypes({
        @JsonSubTypes.Type(value = IDEPingRequest.class, name = "ide_ping"),
        @JsonSubTypes.Type(value = AnnotateRequest.class, name = "annotate")
})
public abstract class RPCRequest {

    public RPCRequest() {
    }

    public abstract String getAction();

    public abstract Object getPayload();
}