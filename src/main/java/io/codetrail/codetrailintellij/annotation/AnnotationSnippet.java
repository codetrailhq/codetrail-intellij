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

    public AnnotationSnippet() {

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

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
