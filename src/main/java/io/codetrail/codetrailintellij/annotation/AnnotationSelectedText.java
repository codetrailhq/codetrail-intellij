package io.codetrail.codetrailintellij.annotation;

public class AnnotationSelectedText {
    private AnnotationSnippet snippet;
    private AnnotationTextSelection selection;

    public AnnotationSelectedText(AnnotationSnippet snippet, AnnotationTextSelection selection) {
        this.snippet = snippet;
        this.selection = selection;
    }

    public AnnotationSnippet getSnippet() {
        return snippet;
    }

    public AnnotationTextSelection getSelection() {
        return selection;
    }

    // fixme: this also needs to include the language as well as the nearestSymbol
}