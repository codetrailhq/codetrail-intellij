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

        JBLabel contentLabel = new JBLabel(annotation.getContent());
        contentLabel.setOpaque(false);
        contentLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));

        JBLabel idLabel = new JBLabel(annotation.getId());
        idLabel.setOpaque(false);
        idLabel.setForeground(UIUtil.getContextHelpForeground());
        idLabel.setFont(idLabel.getFont().deriveFont(10.0f));

        inner.add(contentLabel, BorderLayout.CENTER);
        inner.add(idLabel, BorderLayout.SOUTH);

        BorderLayout topLayout = new BorderLayout();
        topLayout.setHgap(8);
        Panel topPanel = new Panel(topLayout);

        JBLabel titleLabel = new JBLabel("Annotation");
        titleLabel.setOpaque(false);
        titleLabel.setForeground(UIUtil.getContextHelpForeground());
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        topPanel.add(titleLabel, BorderLayout.LINE_START);

        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.RIGHT));

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> {
            ExtensionService.getInstance().editAnnotation(annotation);
        });
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            ExtensionService.getInstance().deleteAnnotation(annotation);
        });
        buttonPanel.add(deleteButton);

        topPanel.add(buttonPanel, BorderLayout.LINE_END);

        component.add(topPanel, BorderLayout.NORTH);
        component.add(inner, BorderLayout.CENTER);

        return component;
    }
}
