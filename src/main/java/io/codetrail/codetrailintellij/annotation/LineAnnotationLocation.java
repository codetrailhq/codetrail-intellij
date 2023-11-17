package io.codetrail.codetrailintellij.annotation;

public class LineAnnotationLocation extends AnnotationLocation {
    private String path;
    private int line;

    public LineAnnotationLocation(String path, int line) {
        this.path = path;
        this.line = line;
    }

    public String getPath() {
        return path;
    }

    public int getLine() {
        return line;
    }

    @Override
    String getKind() {
        return "line";
    }
}
