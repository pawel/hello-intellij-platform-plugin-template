package com.github.pawel.hellointellijplatformplugintemplate.editor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class EditorAreaIllustration extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        Caret primaryCaret = caretModel.getPrimaryCaret();
        LogicalPosition logicalPosition = primaryCaret.getLogicalPosition();
        VisualPosition visualPosition = primaryCaret.getVisualPosition();
        String report = logicalPosition + "\n" +
                visualPosition + "\n" +
                "Offset: " + primaryCaret.getOffset();
        Messages.showInfoMessage(report, "Caret Parameters Inside The Editor");
    }

}
