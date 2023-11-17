package io.codetrail.codetrailintellij.annotation;

public class LineAnnotationLocation extends AnnotationLocation {
    private String path;
    private int line;

    public LineAnnotationLocation(String path, int line) {
        this.path = path;
        this.line = line;
    }

    String getPath() {
        return path;
    }

    int getLine() {
        return line;
    }

    @Override
    String kind() {
        return "line";
    }

    String getKind() { return kind(); }
}
