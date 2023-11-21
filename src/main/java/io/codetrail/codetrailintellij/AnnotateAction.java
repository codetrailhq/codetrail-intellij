package io.codetrail.codetrailintellij;

import com.intellij.collaboration.ui.codereview.diff.EditorComponentInlaysManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiFile;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.components.JBLabel;
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
            AnnotationSelectedText selectedText = getSelectedText(caret, file, editor.getDocument());
            String containingDirectory = file.getVirtualFile().getParent().getPath();

            ExtensionService.getInstance().annotate(location, containingDirectory, selectedText);
        }
    }

    private AnnotationLocation getLocation(Caret caret, PsiFile file) {
        VisualPosition start = caret.getSelectionStartPosition();
        VisualPosition end = caret.getSelectionEndPosition();

        if (start.getLine() == end.getLine() && start.getColumn() == end.getColumn()) {
            // single line comment
            return new LineAnnotationLocation(file.getName(), start.getLine() + 1);
        } else if (start.getLine() != end.getLine() || start.getColumn() != end.getColumn()) {
            // selection within line or multiline selection
            return new RangeAnnotationLocation(file.getName(), start.getLine() + 1, start.getColumn(), end.getLine() + 1, end.getColumn());
        }

        // could not establish matching location, so we'll fail here
        log.error("location is null, something is wrong with the selection");

        // fixme: what happens when location is null?
        return null;
    }

    private AnnotationSelectedText getSelectedText(Caret caret, PsiFile file, Document document) {
        VisualPosition start = caret.getSelectionStartPosition();
        VisualPosition end = caret.getSelectionEndPosition();

        int lineSnippetBefore = Math.max(0, start.getLine() - SNIPPET_LINES_BEFORE);
        int lineSnippetAfter = Math.min(document.getLineCount() - 1, end.getLine() + SNIPPET_LINES_AFTER);
        String snippetCode = document.getText(new TextRange(document.getLineStartOffset(lineSnippetBefore), document.getLineStartOffset(lineSnippetAfter)));
        AnnotationSnippet snippet = new AnnotationSnippet(lineSnippetBefore + 1, lineSnippetAfter + 1, snippetCode);

        String selectedText = document.getText(new TextRange(caret.getSelectionStart(), caret.getSelectionEnd()));
        // fixme: this needs to be normalized, also IntelliJ does sometimes not recognize the language correctly
        String language = file.getLanguage().getID().toLowerCase();

        // todo: determine closest symbol
        AnnotationSymbol symbol = null;

        return new AnnotationSelectedText(snippet, new AnnotationTextSelection(selectedText, start.getLine() + 1, start.getColumn(), end.getLine() + 1, end.getColumn()), symbol, language);
    }
}
