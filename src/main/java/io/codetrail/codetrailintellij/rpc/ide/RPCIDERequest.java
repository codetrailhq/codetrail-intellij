package io.codetrail.codetrailintellij.rpc.ide;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "action")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PrepareStoryRequest.class, name = "prepareStory"),
        @JsonSubTypes.Type(value = DisplayRecordedAnnotationRequest.class, name = "displayRecordedAnnotation"),
        @JsonSubTypes.Type(value = DesktopPingRequest.class, name = "desktopPing")
})
public abstract class RPCIDERequest {
    abstract public String getAction();
}
