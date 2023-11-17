package io.codetrail.codetrailintellij;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.psi.PsiFile;
import com.intellij.openapi.diagnostic.Logger;
import io.codetrail.codetrailintellij.annotation.*;

public class AnnotateAction extends AnAction {
    private static final Logger log = Logger.getInstance(AnnotateAction.class.getName());

    private static final int SNIPPET_LINES_BEFORE = 20;
    private static final int SNIPPET_LINES_AFTER = 20;

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiFile file = e.getData(CommonDataKeys.PSI_FILE);
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();

        // we only support single caret annotations for now
        // todo: think of how to handle if we have more than one caret
        if (caretModel.getCaretCount() != 1) {
            return;
        }

        for (Caret caret: caretModel.getAllCarets()) {
            AnnotationLocation location = getLocation(caret, file);
            AnnotationSelectedText selectedText = getSelectedText(caret, file);
            String containingDirectory = file.getVirtualFile().getParent().getPath();

            ExtensionService.getInstance().annotate(location, containingDirectory, selectedText);
        }
    }

    private AnnotationLocation getLocation(Caret caret, PsiFile file) {
        VisualPosition start = caret.getSelectionStartPosition();
        VisualPosition end = caret.getSelectionEndPosition();

        AnnotationLocation location = null;

        if (start.getLine() == end.getLine() && start.getColumn() == end.getColumn()) {
            // single line comment
            location = new LineAnnotationLocation(file.getName(), start.getLine());
        } else if (start.getLine() != end.getLine() || start.getColumn() != end.getColumn()) {
            // selection within line or multiline selection
            location = new RangeAnnotationLocation(file.getName(), start.getLine(), start.getColumn(), end.getLine(), end.getColumn());
        } else {
            // could not establish matching location, so we'll fail here
            log.error("location is null, something is wrong with the selection");
            // fixme: what happens when location is null at this point?
            return null;
        }

        return location;
    }

    private AnnotationSelectedText getSelectedText(Caret caret, PsiFile file) {
        VisualPosition start = caret.getSelectionStartPosition();
        VisualPosition end = caret.getSelectionEndPosition();

        // fixme: this does not actually return the correct selected text

        int lineSnippetBefore = Math.max(0, start.getLine() - SNIPPET_LINES_BEFORE);
        int lineSnippetAfter = (int) Math.min(file.getVirtualFile().getLength(), end.getLine() + SNIPPET_LINES_AFTER);
        String code = file.getText().substring(lineSnippetBefore, lineSnippetAfter);

        AnnotationSnippet snippet = new AnnotationSnippet(lineSnippetBefore + 1, lineSnippetAfter + 1, code);

        return new AnnotationSelectedText(snippet, new AnnotationTextSelection(file.getName(), start.getLine(), start.getColumn(), end.getLine(), end.getColumn()));
    }
}
