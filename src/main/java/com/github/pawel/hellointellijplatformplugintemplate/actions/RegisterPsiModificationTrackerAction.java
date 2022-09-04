package com.github.pawel.hellointellijplatformplugintemplate.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.annotations.NotNull;

public class RegisterPsiModificationTrackerAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = e.getProject();
        if (project == null) {
            return;
        }
        PsiModificationTracker psiModificationTracker = PsiModificationTracker.getInstance(project);

        System.out.println("\t[pwl]\tpsiModificationTracker.getModificationCount() = " + psiModificationTracker.getModificationCount());
        System.out.println("\t[pwl]\tActionEvent.getData(CommonDataKeys.PSI_ELEMENT) = " + e.getData(CommonDataKeys.PSI_ELEMENT));
    }
}
