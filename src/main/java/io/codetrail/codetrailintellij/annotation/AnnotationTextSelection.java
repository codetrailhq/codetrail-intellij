package io.codetrail.codetrailintellij.annotation;

public class AnnotationTextSelection {
    private String code;
    private int startLine;
    private int startCharacter;
    private int endLine;
    private int endCharacter;

    public AnnotationTextSelection(String code, int startLine, int startCharacter, int endLine, int endCharacter) {
        this.code = code;
        this.startLine = startLine;
        this.startCharacter = startCharacter;
        this.endLine = endLine;
        this.endCharacter = endCharacter;
    }

    public AnnotationTextSelection() {

    }

    public String getCode() {
        return code;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getStartCharacter() {
        return startCharacter;
    }

    public int getEndLine() {
        return endLine;
    }

    public int getEndCharacter() {
        return endCharacter;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public void setStartCharacter(int startCharacter) {
        this.startCharacter = startCharacter;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public void setEndCharacter(int endCharacter) {
        this.endCharacter = endCharacter;
    }
}
