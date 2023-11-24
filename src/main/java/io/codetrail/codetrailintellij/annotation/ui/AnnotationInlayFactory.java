package io.codetrail.codetrailintellij.annotation.ui;

import com.intellij.collaboration.ui.codereview.comment.RoundedPanel;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.UIUtil;
import io.codetrail.codetrailintellij.ExtensionService;
import io.codetrail.codetrailintellij.annotation.Annotation;

import javax.swing.*;
import java.awt.*;

public class AnnotationInlayFactory {
    public static JComponent create(Annotation annotation) {
        JComponent component = new RoundedPanel(new BorderLayout(), 8);
        component.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        JPanel inner = new JPanel(new BorderLayout());
        inner.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        JBLabel titleLabel = new JBLabel("Annotation");
        titleLabel.setOpaque(false);
        titleLabel.setForeground(UIUtil.getContextHelpForeground());
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        JBLabel contentLabel = new JBLabel(annotation.getContent());
        contentLabel.setOpaque(false);
        contentLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));

        JBLabel idLabel = new JBLabel(annotation.getId());
        idLabel.setOpaque(false);
        idLabel.setForeground(UIUtil.getContextHelpForeground());
        idLabel.setFont(idLabel.getFont().deriveFont(10.0f));

        inner.add(titleLabel, BorderLayout.NORTH);
        inner.add(contentLabel, BorderLayout.CENTER);
        inner.add(idLabel, BorderLayout.SOUTH);

        JButton button = new JButton("Edit");
        button.addActionListener(e -> {
            ExtensionService.getInstance().dialogWithWarning("Not implemented", "Editing annotations is not yet implemented.");
        });

        component.add(button, BorderLayout.EAST);

        component.add(inner, BorderLayout.CENTER);

        return component;
    }
}
