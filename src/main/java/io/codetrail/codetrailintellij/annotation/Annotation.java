package io.codetrail.codetrailintellij.annotation;

public class Annotation {
    private AnnotationLocation location;
    private AnnotationSelectedText selectedText;
    private String content;
    private String id;
    private AnnotationGitInfo git;

    public Annotation(AnnotationLocation location, AnnotationSelectedText selectedText, String content) {
        this.location = location;
        this.selectedText = selectedText;
        this.content = content;
    }

    public Annotation() {

    }


    public AnnotationLocation getLocation() {
        return location;
    }

    public AnnotationSelectedText getSelectedText() {
        return selectedText;
    }

    public String getContent() {
        return content;
    }

    public void setLocation(AnnotationLocation location) {
        this.location = location;
    }

    public void setSelectedText(AnnotationSelectedText selectedText) {
        this.selectedText = selectedText;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AnnotationGitInfo getGit() {
        return git;
    }

    public void setGit(AnnotationGitInfo git) {
        this.git = git;
    }
}
