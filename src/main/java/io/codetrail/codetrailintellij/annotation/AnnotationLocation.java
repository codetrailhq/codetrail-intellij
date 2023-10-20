package io.codetrail.codetrailintellij.annotation;

public class AnnotationLocation {
    String kind() {
        return "AnnotationLocation";
    }
}

class FileAnnotationLocation extends AnnotationLocation {
    private String path;

    FileAnnotationLocation(String path) {
        this.path = path;
    }

    String getPath() {
        return path;
    }

    @Override
    String kind() {
        return "file";
    }
}

class DirectoryAnnotationLocation extends AnnotationLocation {
    private String path;

    DirectoryAnnotationLocation(String path) {
        this.path = path;
    }

    String getPath() {
        return path;
    }

    @Override
    String kind() {
        return "directory";
    }
}

class LineAnnotationLocation extends AnnotationLocation {
    private String path;
    private int line;

    LineAnnotationLocation(String path, int line) {
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
}

class RangeAnnotationLocation extends AnnotationLocation {
    private String path;
    private int startLine;
    private int startCharacter;
    private int endLine;
    private int endCharacter;

    RangeAnnotationLocation(String path, int startLine, int startCharacter, int endLine, int endCharacter) {
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
}