package io.codetrail.codetrailintellij.annotation;

import javax.annotation.Nullable;

public class AnnotationSelectedText {
    private AnnotationSnippet snippet;
    private AnnotationTextSelection selected;

    private AnnotationSymbol symbol;

    private String language;

    public AnnotationSelectedText(AnnotationSnippet snippet, AnnotationTextSelection selected, AnnotationSymbol symbol, String language) {
        this.snippet = snippet;
        this.selected = selected;
        this.symbol = symbol;
    }

    public AnnotationSnippet getSnippet() {
        return snippet;
    }

    public AnnotationTextSelection getSelected() {
        return selected;
    }

    @Nullable
    public AnnotationSymbol getSymbol() {
        return symbol;
    }

    public String getLanguage() {
        return language;
    }

    private void setLanguage(String language) {
        // todo: validate that the language is valid
        this.language = language;
    }
}