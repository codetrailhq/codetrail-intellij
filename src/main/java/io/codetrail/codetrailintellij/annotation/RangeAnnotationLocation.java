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

    String getPath() {
        return path;
    }

    int getStartLine() {
        return startLine;
    }

    int getStartCharacter() {
        return startCharacter;
    }

    int getEndLine() {
        return endLine;
    }

    int getEndCharacter() {
        return endCharacter;
    }

    @Override
    String kind() {
        return "range";
    }

    String getKind() { return kind(); }
}
