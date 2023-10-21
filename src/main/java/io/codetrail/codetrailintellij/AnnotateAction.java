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
import io.codetrail.codetrailintellij.annotation.AnnotationLocation;
import io.codetrail.codetrailintellij.annotation.RangeAnnotationLocation;

public class AnnotateAction extends AnAction {
    private static final Logger log = Logger.getInstance(AnnotateAction.class.getName());

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiFile file = e.getData(CommonDataKeys.PSI_FILE);
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();

        log.debug("****annotate opened****");
        log.debug("currently in file " + file.getName());
        log.debug("any carets " + caretModel.getCaretCount());

        for (Caret caret: caretModel.getAllCarets()) {
            log.debug("annotate in file " + file.getName() + " with code code " + caret.getSelectedText());

            ExtensionService.getInstance();

            VisualPosition start = caret.getSelectionStartPosition();
            VisualPosition end = caret.getSelectionEndPosition();

            AnnotationLocation location = new RangeAnnotationLocation(file.getName(), start.getLine(), start.getColumn(), end.getLine(), end.getColumn());

            ExtensionService.getInstance().annotate(location, file.getVirtualFile().getPath(), caret.getSelectedText());
        }
    }
}
