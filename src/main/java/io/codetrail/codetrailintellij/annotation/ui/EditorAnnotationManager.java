package io.codetrail.codetrailintellij.annotation.ui;

import com.intellij.collaboration.ui.codereview.diff.EditorComponentInlaysManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
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
    private IDEStory currentStory;

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
        for (Annotation a : annotations) {
            displayAnnotationInExistingOrNewEditor(a);
        }

        currentStory = story;
    }

    private Editor displayAnnotationInExistingOrNewEditor(Annotation a) {
        // first check if we have an editor
        Editor existing = findEditorForAnnotation(a);
        if (existing != null) {
            displayAnnotationInEditor(a, existing);
            return existing;
        } else {
            // if not, create one
            Editor newEditor = createEditorForAnnotation(a);
            displayAnnotationInEditor(a, newEditor);
            return newEditor;
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

        VirtualFile foundFile = fileEditorManager.getProject().getBaseDir().findFileByRelativePath(annotation.getLocation().getPath());
        if (foundFile == null) {
            log.error("could not find file for annotation");
            return null;
        }

        FileEditor[] fileEditors = fileEditorManager.openFile(foundFile, true);

        Editor[] allEditors = EditorFactory.getInstance().getAllEditors();
        for (Editor e : allEditors) {
            if (e.getVirtualFile().getCanonicalPath().contentEquals(fullAnnotationPath)) {
                return e;
            }
        }

        return null;
    }

    private Editor findEditorForAnnotation(Annotation annotation) {
        String fullAnnotationPath = getFilePath(annotation);

        Editor[] allEditors = EditorFactory.getInstance().getAllEditors();
        for (Editor e : allEditors) {
            if (e.getVirtualFile().getCanonicalPath().contentEquals(fullAnnotationPath)) {
                return e;
            }
        }

        return null;
    }

    public void clearAnnotations() {
        for (Inlay i : inlays.values()) {
            i.dispose();
        }
        inlays.clear();
    }

    public void clearAnnotation(String annotationId) {
        if (inlays.containsKey(annotationId)) {
            inlays.get(annotationId).dispose();
            inlays.remove(annotationId);
        }
    }

    private String getFilePath(Annotation annotation) {
        return Paths.get(project.getBasePath(), annotation.getLocation().getPath()).toString();
    }

    public void displayNextAnnotation(Annotation viewedAnnotation) {
        displayAnnotationAtOffset(viewedAnnotation, 1);
    }

    public void displayPreviousAnnotation(Annotation viewedAnnotation) {
        displayAnnotationAtOffset(viewedAnnotation, -1);
    }

    private void displayAnnotationAtOffset(Annotation annotation, int offset) {
        if (currentStory == null) {
            return;
        }

        // first, find the index in the filteredSequence of the currently viewed annotation
        int index = currentStory.getFilteredSequence().indexOf(annotation.getId());
        if (index == -1) {
            return;
        }

        // then, find the annotation in the filteredSequence at the given offset
        if (index + offset >= 0 && index + offset < currentStory.getFilteredSequence().size()) {
            String nextAnnotationId = currentStory.getFilteredSequence().get(index + offset);
            Annotation nextAnnotation = currentStory.getFilteredAnnotations().stream().filter((a) -> a.getId().contentEquals(nextAnnotationId)).findFirst().orElse(null);
            if (nextAnnotation != null) {
                Editor e = findEditorForAnnotation(nextAnnotation);
                if (e == null) {
                    // fixme: if there are multiple annotations in a file, it only creates the next one
                    e = displayAnnotationInExistingOrNewEditor(nextAnnotation);
                }

                jumpToAnnotation(nextAnnotation, e);
            }
        }
    }

    private void jumpToAnnotation(Annotation nextAnnotation, Editor e) {
        FileEditorManager.getInstance(project).openFile(e.getVirtualFile(), true, true);

        if (nextAnnotation.getLocation() instanceof RangeAnnotationLocation) {
            RangeAnnotationLocation location = (RangeAnnotationLocation) nextAnnotation.getLocation();
            e.getCaretModel().moveToLogicalPosition(new com.intellij.openapi.editor.LogicalPosition(location.getEndLine() - 1, 0));
        } else if (nextAnnotation.getLocation() instanceof LineAnnotationLocation) {
            LineAnnotationLocation location = (LineAnnotationLocation) nextAnnotation.getLocation();
            e.getCaretModel().moveToLogicalPosition(new com.intellij.openapi.editor.LogicalPosition(location.getLine() - 1, 0));
        } else {
            log.error("Unknown annotation location type");
        }

        ScrollingModel scrollingModel = e.getScrollingModel();
        scrollingModel.scrollToCaret(ScrollType.CENTER);

        e.getComponent().requestFocus();
    }
}
