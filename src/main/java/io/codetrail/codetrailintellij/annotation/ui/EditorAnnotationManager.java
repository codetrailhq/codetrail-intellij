package io.codetrail.codetrailintellij.annotation.ui;

import com.intellij.collaboration.ui.codereview.diff.EditorComponentInlaysManager;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
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
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EditorAnnotationManager {
    private static final Logger log = Logger.getInstance(EditorAnnotationManager.class.getName());
    private Project project;

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

    private void displayAnnotationInEditor(Annotation annotation, Editor editor) {
        JComponent inlay = AnnotationInlayFactory.create(annotation);
        EditorComponentInlaysManager inlaysManager = new EditorComponentInlaysManager((EditorImpl) editor);

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

    private Editor findEditorForAnnotation(Annotation annotation) {
        String fullAnnotationPath = Paths.get(project.getBasePath(), annotation.getLocation().getPath()).toString();

        Editor[] allEditors = EditorFactory.getInstance().getAllEditors();
        for (Editor e: allEditors) {
            if (e.getVirtualFile().getCanonicalPath().contentEquals(fullAnnotationPath)) {
                return e;
            }
        }

        return null;
    }
}
