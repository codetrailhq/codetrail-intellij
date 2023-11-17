package io.codetrail.codetrailintellij.annotation;

public class AnnotationTextSelection {
    private String path;
    private int startLine;
    private int startCharacter;
    private int endLine;
    private int endCharacter;

    public AnnotationTextSelection(String path, int startLine, int startCharacter, int endLine, int endCharacter) {
        this.path = path;
        this.startLine = startLine;
        this.startCharacter = startCharacter;
        this.endLine = endLine;
        this.endCharacter = endCharacter;
    }

    public String getPath() {
        return path;
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
}
