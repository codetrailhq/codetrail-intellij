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
import io.codetrail.codetrailintellij.annotation.LineAnnotationLocation;
import io.codetrail.codetrailintellij.annotation.RangeAnnotationLocation;

public class AnnotateAction extends AnAction {
    private static final Logger log = Logger.getInstance(AnnotateAction.class.getName());

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
            VisualPosition start = caret.getSelectionStartPosition();
            VisualPosition end = caret.getSelectionEndPosition();

            AnnotationLocation location = null;

            if (start.getLine() == end.getLine() && start.getColumn() == end.getColumn()) {
                // single line comment
                location = new LineAnnotationLocation(file.getName(), start.getLine());
            } else if (start.getLine() != end.getLine()) {
                location = new RangeAnnotationLocation(file.getName(), start.getLine(), start.getColumn(), end.getLine(), end.getColumn());
            }

            if (location == null) {
                log.error("location is null, something is wrong with the selection");
                // fixme: what happens when location is null at this point?
                return;
            }

            ExtensionService.getInstance().annotate(location, file.getContainingDirectory().getName(), caret.getSelectedText());
        }
    }
}
