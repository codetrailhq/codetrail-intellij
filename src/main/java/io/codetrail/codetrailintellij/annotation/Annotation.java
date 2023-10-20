package io.codetrail.codetrailintellij.annotation;

public class Annotation {
    private String repository;
    private String gitRef;
    private AnnotationLocation location;
    private String selectedText;
    private String content;

    public Annotation(String repository, String gitRef, AnnotationLocation location, String selectedText, String content) {
        this.repository = repository;
        this.gitRef = gitRef;
        this.location = location;
        this.selectedText = selectedText;
        this.content = content;
    }

    public String getRepository() {
        return repository;
    }

    public String getGitRef() {
        return gitRef;
    }

    public AnnotationLocation getLocation() {
        return location;
    }

    public String getSelectedText() {
        return selectedText;
    }

    public String getContent() {
        return content;
    }
}
