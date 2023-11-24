package io.codetrail.codetrailintellij.rpc.extension;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "action")
@JsonSubTypes({
        @JsonSubTypes.Type(value = IDEPingResponse.class, name = "idePing"),
        @JsonSubTypes.Type(value = AnnotateResponse.class, name = "annotate")
})
public abstract class RPCResponse {

    public RPCResponse() {
    }

    public abstract String getAction();

    public abstract Object getData();

    public abstract RPCResponseError getError();
}

