package io.codetrail.codetrailintellij.annotation;

import org.jetbrains.annotations.Nullable;

public class AnnotationSelectedText {
    private AnnotationSnippet snippet;
    private AnnotationTextSelection selected;

    private AnnotationSymbol nearestSymbol;

    private String language;

    public AnnotationSelectedText(AnnotationSnippet snippet, AnnotationTextSelection selected, AnnotationSymbol nearestSymbol, String language) {
        this.snippet = snippet;
        this.selected = selected;
        this.nearestSymbol = nearestSymbol;
    }

    public AnnotationSelectedText() {

    }

    public AnnotationSnippet getSnippet() {
        return snippet;
    }

    public AnnotationTextSelection getSelected() {
        return selected;
    }

    @Nullable
    public AnnotationSymbol getNearestSymbol() {
        return nearestSymbol;
    }

    public String getLanguage() {
        return language;
    }

    private void setLanguage(String language) {
        // todo: validate that the language is valid
        this.language = language;
    }

    public void setSnippet(AnnotationSnippet snippet) {
        this.snippet = snippet;
    }

    public void setSelected(AnnotationTextSelection selected) {
        this.selected = selected;
    }

    public void setNearestSymbol(AnnotationSymbol nearestSymbol) {
        this.nearestSymbol = nearestSymbol;
    }
}