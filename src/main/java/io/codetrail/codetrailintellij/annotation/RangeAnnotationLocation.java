package io.codetrail.codetrailintellij.annotation;

public class RangeAnnotationLocation extends AnnotationLocation {
    private String path;
    private int startLine;
    private int startCharacter;
    private int endLine;
    private int endCharacter;

    public RangeAnnotationLocation(String path, int startLine, int startCharacter, int endLine, int endCharacter) {
        this.path = path;
        this.startLine = startLine;
        this.startCharacter = startCharacter;
        this.endLine = endLine;
        this.endCharacter = endCharacter;
    }

    public RangeAnnotationLocation() {
    }

    public String getPath() {
        return path;
    }

    void setPath(String path) {
        this.path = path;
    }

    public int getStartLine() {
        return startLine;
    }

    void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getStartCharacter() {
        return startCharacter;
    }

    void setStartCharacter(int startCharacter) {
        this.startCharacter = startCharacter;
    }

    public int getEndLine() {
        return endLine;
    }

    void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public int getEndCharacter() {
        return endCharacter;
    }

    void setEndCharacter(int endCharacter) {
        this.endCharacter = endCharacter;
    }

    @Override
    String getKind() {
        return "range";
    }
}
