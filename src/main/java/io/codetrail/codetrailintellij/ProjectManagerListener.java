package io.codetrail.codetrailintellij;

public class ProjectManagerListener implements com.intellij.openapi.project.ProjectManagerListener {
    @Override
    public void projectOpened(com.intellij.openapi.project.Project project) {
        String path = project.getBasePath();
        ExtensionService.getInstance().activate(path);
    }
}
