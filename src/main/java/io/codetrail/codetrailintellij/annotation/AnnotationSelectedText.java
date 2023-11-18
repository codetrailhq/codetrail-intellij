package io.codetrail.codetrailintellij.annotation;

import javax.annotation.Nullable;

public class AnnotationSelectedText {
    private AnnotationSnippet snippet;
    private AnnotationTextSelection selection;

    private AnnotationSymbol symbol;

    private String language;

    public AnnotationSelectedText(AnnotationSnippet snippet, AnnotationTextSelection selection, AnnotationSymbol symbol, String language) {
        this.snippet = snippet;
        this.selection = selection;
        this.symbol = symbol;
    }

    public AnnotationSnippet getSnippet() {
        return snippet;
    }

    public AnnotationTextSelection getSelection() {
        return selection;
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