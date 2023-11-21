package io.codetrail.codetrailintellij.annotation.ui;

import com.intellij.collaboration.ui.codereview.comment.RoundedPanel;
import io.codetrail.codetrailintellij.annotation.Annotation;

import javax.swing.*;
import java.awt.*;

public class AnnotationInlayFactory {
    public static JComponent create(Annotation annotation) {
        JComponent component = new RoundedPanel(new BorderLayout(), 8);
        component.add(new JLabel(annotation.getContent()), BorderLayout.CENTER);

        return component;
    }
}
