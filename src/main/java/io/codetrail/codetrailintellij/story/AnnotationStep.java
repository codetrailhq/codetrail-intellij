package io.codetrail.codetrailintellij.story;

import io.codetrail.codetrailintellij.annotation.Annotation;

public class AnnotationStep {
    private String kind = "annotation";
    private Annotation content;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Annotation getContent() {
        return content;
    }

    public void setContent(Annotation content) {
        this.content = content;
    }
}
