package io.codetrail.codetrailintellij.annotation;

public class AnnotationSnippet {
    private int startLine;
    private int endLine;

    private String code;

    public AnnotationSnippet(int startLine, int endLine, String code) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.code = code;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public String getCode() {
        return code;
    }
}
