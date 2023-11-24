package io.codetrail.codetrailintellij.annotation.ui;

import com.intellij.collaboration.ui.codereview.diff.EditorComponentInlaysManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import io.codetrail.codetrailintellij.annotation.Annotation;
import io.codetrail.codetrailintellij.annotation.LineAnnotationLocation;
import io.codetrail.codetrailintellij.annotation.RangeAnnotationLocation;
import io.codetrail.codetrailintellij.story.IDEStory;

import javax.swing.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class EditorAnnotationManager {
    private static final Logger log = Logger.getInstance(EditorAnnotationManager.class.getName());
    private Project project;

    private Map<String, Inlay> inlays = new java.util.HashMap<>();

    public EditorAnnotationManager(Project project) {
        this.project = project;
    }

    public void displayRecordedAnnotation(Annotation annotation) {
        Editor e = findEditorForAnnotation(annotation);
        if (e == null) {
            log.error("could not find editor for newly recorded annotation");
            return;
        }

        displayAnnotationInEditor(annotation, e);
    }

    public void displayAnnotationsForStory(IDEStory story) {
        // first, remove all existing annotations
        clearAnnotations();

        List<Annotation> annotations = story.getFilteredAnnotations();
        for (Annotation a: annotations) {
            // first check if we have an editor
            Editor existing = findEditorForAnnotation(a);
            if (existing != null) {
                displayAnnotationInEditor(a, existing);
            } else {
                // if not, create one
                Editor newEditor = createEditorForAnnotation(a);
                displayAnnotationInEditor(a, newEditor);
            }
        }
    }

    private void displayAnnotationInEditor(Annotation annotation, Editor editor) {
        JComponent inlay = AnnotationInlayFactory.create(annotation);
        EditorComponentInlaysManager inlaysManager = new EditorComponentInlaysManager((EditorImpl) editor);

        if (annotation.getLocation() instanceof RangeAnnotationLocation) {
            RangeAnnotationLocation location = (RangeAnnotationLocation) annotation.getLocation();
            addOrUpdateInlayAtPosition(location.getEndLine() - 1, inlay, annotation, inlaysManager);
        } else if (annotation.getLocation() instanceof LineAnnotationLocation) {
            LineAnnotationLocation location = (LineAnnotationLocation) annotation.getLocation();
            addOrUpdateInlayAtPosition(location.getLine() - 1, inlay, annotation, inlaysManager);
        } else {
            log.error("Unknown annotation location type");
        }
    }

    private void addOrUpdateInlayAtPosition(int line, JComponent inlay, Annotation annotation, EditorComponentInlaysManager manager) {
        if (inlays.containsKey(annotation.getId())) {
            inlays.get(annotation.getId()).dispose();
            inlays.remove(annotation.getId());
        }

        Inlay i = manager.insertAfter(line, inlay, 100, null);
        inlays.put(annotation.getId(), i);
    }

    private Editor createEditorForAnnotation(Annotation annotation) {
        // we don't have an editor for this annotation yet, so we need to create one
        String fullAnnotationPath = getFilePath(annotation);

        FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
        FileEditor[] fileEditors = fileEditorManager.openFile(fileEditorManager.getProject().getBaseDir().findFileByRelativePath(fullAnnotationPath), true);

        Editor[] allEditors = EditorFactory.getInstance().getAllEditors();
        for (Editor e: allEditors) {
            if (e.getVirtualFile().getCanonicalPath().contentEquals(fullAnnotationPath)) {
                return e;
            }
        }

        return null;
    }

    private Editor findEditorForAnnotation(Annotation annotation) {
        String fullAnnotationPath = getFilePath(annotation);

        Editor[] allEditors = EditorFactory.getInstance().getAllEditors();
        for (Editor e: allEditors) {
            if (e.getVirtualFile().getCanonicalPath().contentEquals(fullAnnotationPath)) {
                return e;
            }
        }

        return null;
    }

    private void clearAnnotations() {
        for (Inlay i: inlays.values()) {
            i.dispose();
        }
        inlays.clear();
    }

    private String getFilePath(Annotation annotation) {
        return Paths.get(project.getBasePath(), annotation.getLocation().getPath()).toString();
    }
}
