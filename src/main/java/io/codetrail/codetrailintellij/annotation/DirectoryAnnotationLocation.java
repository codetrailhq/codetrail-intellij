package io.codetrail.codetrailintellij.annotation;

public class DirectoryAnnotationLocation extends AnnotationLocation {
    private String path;

    DirectoryAnnotationLocation(String path) {
        this.path = path;
    }

    String getPath() {
        return path;
    }

    @Override
    String getKind() {
        return "directory";
    }
}
