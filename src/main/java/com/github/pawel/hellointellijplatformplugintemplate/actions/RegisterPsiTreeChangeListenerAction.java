package com.github.pawel.hellointellijplatformplugintemplate.actions;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiTreeChangeAdapter;
import com.intellij.psi.PsiTreeChangeEvent;
import com.intellij.psi.tree.IFileElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class RegisterPsiTreeChangeListenerAction extends AnAction implements Disposable {

    private static final String ACTION_INITIAL_TEXT = "Register Psi Tree Change Listener Action";
    private static final String ACTION_ALTERNATE_TEXT = "Unregister Psi Tree Change Listener Action";
    private static final LoggingService loggingService = LoggingService.getInstance();
    private static final Path rootPath = Path.of("/p", "tmpidelog");
    private Project project = null;
    private MyPsiTreeAnyChangeListener listener;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (listener == null || project == null) {
            this.project = e.getProject();
            this.listener = MyPsiTreeAnyChangeListener.getInstance();
            final PsiManager psiManager = PsiManager.getInstance(project);
            if (e.getPresentation().getText().equals(ACTION_INITIAL_TEXT)) {
                psiManager.addPsiTreeChangeListener(listener, this);
                e.getPresentation().setText(ACTION_ALTERNATE_TEXT);
            } else if (e.getPresentation().getText().equals(ACTION_ALTERNATE_TEXT)) {
                psiManager.removePsiTreeChangeListener(listener);
                e.getPresentation().setText(ACTION_INITIAL_TEXT);
            }
        }
    }

    @Override
    public void dispose() {
        PsiManager.getInstance(project).removePsiTreeChangeListener(listener);
        this.project = null;
    }

    private static final class MyPsiTreeAnyChangeListener extends PsiTreeChangeAdapter {

        private MyPsiTreeAnyChangeListener() {
        }

        public static MyPsiTreeAnyChangeListener getInstance() {
            return new MyPsiTreeAnyChangeListener();
        }

        @Override
        public void childAdded(@NotNull PsiTreeChangeEvent event) {
            onChange(event);
        }

        @Override
        public void childRemoved(@NotNull PsiTreeChangeEvent event) {
            onChange(event);
        }

        @Override
        public void childReplaced(@NotNull PsiTreeChangeEvent event) {
            onChange(event);
        }

        @Override
        public void childMoved(@NotNull PsiTreeChangeEvent event) {
            onChange(event);
        }

        @Override
        public void childrenChanged(@NotNull PsiTreeChangeEvent event) {
            onChange(event);
        }

        @Override
        public void propertyChanged(@NotNull PsiTreeChangeEvent event) {
            onChange(event);
        }

        private void onChange(@Nullable PsiTreeChangeEvent event) {
            if(event == null) {
                return;
            }

            System.out.println("\t[pwl]\tMyPsiTreeAnyChangeListener.onChange");
            System.out.println("\t[pwl]\tevent.getPropertyName() = " + event.getPropertyName());
            if (event.getElement() != null) {
                System.out.println("\t[pwl]\tevent.getElement().getText() = " + event.getElement().getText());
                System.out.println("\t[pwl]\tevent.getElement().getNode() = " + event.getElement().getNode());
            } else {
                System.out.println("\t[pwl]\tevent.getElement() == null");
            }

            final PsiFile eventFile = event.getFile();
            if (eventFile != null) {
                final IFileElementType fileElementType = eventFile.getFileElementType();
                final String text = eventFile.getText();
                System.out.println("\t[pwl]\tevent.getFile().isValid() = " + eventFile.isValid());
                System.out.println("\t[pwl]\tevent.getFile().getFileElementType() = " + fileElementType);
                System.out.println("\t[pwl]\tevent.getFile().getText() = " + text);
                try {
                    Path pathz = Files.createTempFile(rootPath, eventFile.getName() + "_", "_" + System.nanoTime());
                    byte[] bytez = eventFile.getVirtualFile().contentsToByteArray();
                    loggingService.logToFile(System.nanoTime() + ";" + eventFile.getVirtualFile().getName() + ";" + eventFile.getVirtualFile().getPresentableUrl());
                    loggingService.logToNewFile(bytez, pathz);
                    loggingService.logToNewFile(text, Path.of("/p", "tmpidelog", Long.toString(System.nanoTime())));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("\t[pwl]\tevent.getFile() == null");
            }
        }
    }

    private static class LoggingService { // TODO create interface and use injected service

        private static final LoggingService INSTANCE = new LoggingService();
        private final Path path;


        private LoggingService() {
            try {
                // TODO move to plugin configuration
                Path PATH = Path.of("/p", "tmpidelog", "psi_tree_change_listener");
                this.path = Files.notExists(PATH) ? Files.createFile(PATH) : PATH;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static LoggingService getInstance() {
            return INSTANCE;
        }

        void logToNewFile(String msg, Path file) {
            try {
                Path possiblyNewPath = Files.notExists(file) ? Files.createFile(file) : file;
                Files.writeString(possiblyNewPath, msg + "\n", StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        void logToNewFile(final byte[] bytez, final Path pathz) {
            try {
                Files.write(pathz, bytez);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        void logToFile(final String msg) {
            logToNewFile(msg, this.path);
        }
    }
}
