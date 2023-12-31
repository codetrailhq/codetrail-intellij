package io.codetrail.codetrailintellij.rpc.extension;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "action")
@JsonSubTypes({
        @JsonSubTypes.Type(value = IDEPingRequest.class, name = "idePing"),
        @JsonSubTypes.Type(value = AnnotateRequest.class, name = "annotate"),
        @JsonSubTypes.Type(value = EditAnnotationRequest.class, name = "editAnnotation")
})
public abstract class RPCRequest {

    public RPCRequest() {
    }

    public abstract String getAction();

    public abstract Object getPayload();
}