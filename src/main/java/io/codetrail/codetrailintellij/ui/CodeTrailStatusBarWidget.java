package io.codetrail.codetrailintellij.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.impl.status.EditorBasedWidget;
import com.intellij.util.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class CodeTrailStatusBarWidget extends EditorBasedWidget implements com.intellij.openapi.wm.StatusBarWidget.IconPresentation {
    protected CodeTrailStatusBarWidget(@NotNull Project myProject) {
        super(myProject);
    }

    @NotNull
    @Override
    public String ID() {
        return "io.codetrail.codetrail-intellij.widget";
    }

    @Nullable
    @Override
    public WidgetPresentation getPresentation() {
        return this;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return IconLoader.getIcon("icon.svg");
    }

    @Nullable
    @Override
    public Consumer<MouseEvent> getClickConsumer() {
        return mouseEvent -> JOptionPane.showMessageDialog(null, "Widget clicked!");
    }

    @Nullable
    @Override
    public String getShortcutText() {
        return IconPresentation.super.getShortcutText();
    }

    @Nullable
    @Override
    public String getTooltipText() {
        return "CodeTrail";
    }
}
