package io.codetrail.codetrailintellij.annotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "kind")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RangeAnnotationLocation.class, name = "range"),
        @JsonSubTypes.Type(value = LineAnnotationLocation.class, name = "line"),
        @JsonSubTypes.Type(value = FileAnnotationLocation.class, name = "file"),
        @JsonSubTypes.Type(value = DirectoryAnnotationLocation.class, name = "directory"),
})
public abstract class AnnotationLocation {
    abstract String getKind();
}

