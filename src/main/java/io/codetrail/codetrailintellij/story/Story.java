package io.codetrail.codetrailintellij.story;

import javax.annotation.Nullable;

public class Story {
    private String id;
    private String workspace;
    private int number;
    @Nullable private String team;
    @Nullable private String title;
    private boolean isDraft;
    private StoryContent content;
    private String createdBy;
    private String createdAt;
    @Nullable private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Nullable
    public String getTeam() {
        return team;
    }

    public void setTeam(@Nullable String team) {
        this.team = team;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    public boolean isDraft() {
        return isDraft;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
    }

    public StoryContent getContent() {
        return content;
    }

    public void setContent(StoryContent content) {
        this.content = content;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Nullable
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@Nullable String updatedAt) {
        this.updatedAt = updatedAt;
    }
}