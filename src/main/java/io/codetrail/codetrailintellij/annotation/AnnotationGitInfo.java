package io.codetrail.codetrailintellij.annotation;

public class AnnotationGitInfo {
    private String repositoryRemoteUrl;
    private String gitHeadRef;
    private String gitBranch;

    public String getRepositoryRemoteUrl() {
        return repositoryRemoteUrl;
    }

    public void setRepositoryRemoteUrl(String repositoryRemoteUrl) {
        this.repositoryRemoteUrl = repositoryRemoteUrl;
    }

    public String getGitHeadRef() {
        return gitHeadRef;
    }

    public void setGitHeadRef(String gitHeadRef) {
        this.gitHeadRef = gitHeadRef;
    }

    public String getGitBranch() {
        return gitBranch;
    }

    public void setGitBranch(String gitBranch) {
        this.gitBranch = gitBranch;
    }
}
