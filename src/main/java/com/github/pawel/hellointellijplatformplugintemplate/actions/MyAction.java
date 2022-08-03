package com.github.pawel.hellointellijplatformplugintemplate.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.pom.Navigatable;
import org.jetbrains.annotations.NotNull;

public class MyAction extends AnAction {

    @Override
    public void update(@NotNull AnActionEvent e) {
        final Project currentProject = e.getProject();
        e.getPresentation().setEnabledAndVisible(currentProject != null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project currentProject = e.getProject();
        StringBuilder message = new StringBuilder(e.getPresentation().getText()).append(" Selected!");
        Navigatable selectedElement = e.getData(CommonDataKeys.NAVIGATABLE);
        if (selectedElement != null) {
            message.append("\nSelected Element: ").append(selectedElement);
        }

        String title = StringUtil.capitalize(e.getPresentation().getDescription());
        Messages.showMessageDialog(currentProject, message.toString(), title, Messages.getInformationIcon());

    }
}
