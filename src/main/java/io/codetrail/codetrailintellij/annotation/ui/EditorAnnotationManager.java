package io.codetrail.codetrailintellij.annotation.ui;

import com.intellij.collaboration.ui.codereview.diff.EditorComponentInlaysManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import io.codetrail.codetrailintellij.annotation.Annotation;
import io.codetrail.codetrailintellij.annotation.LineAnnotationLocation;
import io.codetrail.codetrailintellij.annotation.RangeAnnotationLocation;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class EditorAnnotationManager {
    private static final Logger log = Logger.getInstance(EditorAnnotationManager.class.getName());
    private Project project;

    private Editor editor;

    public EditorAnnotationManager(Project project) {
        this.project = project;
    }


    public void donateEditor(Editor editor) {
        this.editor = editor;
    }

    public void displayRecordedAnnotation(Annotation annotation) {
        Editor e = findEditorForAnnotation(annotation);
        if (e == null) {
            log.error("Could not find editor for annotation");
            return;
        }

        JComponent inlay = AnnotationInlayFactory.create(annotation);
        EditorComponentInlaysManager inlaysManager = new EditorComponentInlaysManager((EditorImpl) e);

        if (annotation.getLocation() instanceof RangeAnnotationLocation) {
            RangeAnnotationLocation location = (RangeAnnotationLocation) annotation.getLocation();
            inlaysManager.insertAfter(location.getEndLine() - 1, inlay, 100, null);
        } else if (annotation.getLocation() instanceof LineAnnotationLocation) {
            LineAnnotationLocation location = (LineAnnotationLocation) annotation.getLocation();
            inlaysManager.insertAfter(location.getLine() - 1, inlay, 100, null);
        } else {
            log.error("Unknown annotation location type");
        }
    }

    @Nullable
    private Editor findEditorForAnnotation(Annotation annotation) {
        /*FileEditor[] allEditors = FileEditorManager.getInstance(project).getAllEditors();
        String annotationFilePath = project.getBasePath() + "/" + annotation.getLocation().getPath();
        for (FileEditor editor : allEditors) {
            if (editor.getFile().getCanonicalPath().equalsIgnoreCase(annotationFilePath)) {
                // return editor.();

            }
        }
        return null;*/

        // todo: this is a hack, we should find the correct editor for the annotation
        // I also think this might introduce a race condition

        return editor;
    }
}
