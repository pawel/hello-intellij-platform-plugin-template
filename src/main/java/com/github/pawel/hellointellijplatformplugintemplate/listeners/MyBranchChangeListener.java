package com.github.pawel.hellointellijplatformplugintemplate.listeners;

import com.intellij.openapi.vcs.BranchChangeListener;
import org.jetbrains.annotations.NotNull;

public class MyBranchChangeListener implements BranchChangeListener {
    @Override
    public void branchWillChange(@NotNull String branchName) {
        System.out.println("\t[pwl]\tMyBranchChangeListener.branchWillChange");
        System.out.println("\t[pwl]\tbranchName = " + branchName);
    }

    @Override
    public void branchHasChanged(@NotNull String branchName) {
        System.out.println("\t[pwl]\tMyBranchChangeListener.branchHasChanged");
        System.out.println("\t[pwl]\tbranchName = " + branchName);

    }
}
