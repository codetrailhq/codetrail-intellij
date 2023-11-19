package io.codetrail.codetrailintellij.story;

import io.codetrail.codetrailintellij.annotation.Annotation;

import java.util.List;

public class IDEStory {
    private Story story;
    private List<Annotation> filteredAnnotations;
    private List<String> filteredSequence;
    private List<String> globalSequence;

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public List<Annotation> getFilteredAnnotations() {
        return filteredAnnotations;
    }

    public void setFilteredAnnotations(List<Annotation> filteredAnnotations) {
        this.filteredAnnotations = filteredAnnotations;
    }

    public List<String> getFilteredSequence() {
        return filteredSequence;
    }

    public void setFilteredSequence(List<String> filteredSequence) {
        this.filteredSequence = filteredSequence;
    }

    public List<String> getGlobalSequence() {
        return globalSequence;
    }

    public void setGlobalSequence(List<String> globalSequence) {
        this.globalSequence = globalSequence;
    }
}
