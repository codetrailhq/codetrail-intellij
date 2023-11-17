package io.codetrail.codetrailintellij.annotation;

public class FileAnnotationLocation extends AnnotationLocation {
    private String path;

    FileAnnotationLocation(String path) {
        this.path = path;
    }

    String getPath() {
        return path;
    }

    @Override
    String getKind() {
        return "file";
    }
}
