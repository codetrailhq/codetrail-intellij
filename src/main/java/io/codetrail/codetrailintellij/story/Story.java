package io.codetrail.codetrailintellij.story;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

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

    @JsonProperty("is_draft")
    public boolean getIsDraft() {
        return isDraft;
    }

    @JsonProperty("is_draft")
    public void setIsDraft(boolean draft) {
        isDraft = draft;
    }

    public StoryContent getContent() {
        return content;
    }

    public void setContent(StoryContent content) {
        this.content = content;
    }

    @JsonProperty("created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("created_by")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    @JsonProperty("created_at")

    public String getCreatedAt() {
        return createdAt;
    }
    @JsonProperty("created_at")

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updated_at")
    @Nullable
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(@Nullable String updatedAt) {
        this.updatedAt = updatedAt;
    }
}