package io.codetrail.codetrailintellij.annotation;

public class AnnotationSymbol {
    private String name;
    private String kind;

    public AnnotationSymbol(String name, String kind) {
        this.name = name;
        setKind(kind);
    }

    public String getName() {
        return name;
    }

    public String getKind() {
        return kind;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKind(String kind) {
        switch (kind) {
            case "class":
            case "function":
                break;
            default:
                throw new IllegalArgumentException("Invalid kind: " + kind);
        }

        this.kind = kind;
    }
}