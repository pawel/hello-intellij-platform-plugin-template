package com.github.pawel.hellointellijplatformplugintemplate.listeners;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class DumbBulkFileListener implements BulkFileListener {

    private static final Function<String, Path> PATH_PRODUCER = fn -> Path.of("/p", "tmpidelog", fn);
    private static final Logger LOG = Logger.getInstance(DumbBulkFileListener.class);

    @Override
    public void before(@NotNull List<? extends VFileEvent> events) {
        events.forEach(event -> logContent("before", event));
    }

    @Override
    public void after(@NotNull List<? extends VFileEvent> events) {
        events.forEach(event -> logContent("after", event));
    }

    private void logContent(final String postfix, final VFileEvent event) {
        if (event == null) {
            System.err.println("\t[pwl]\t event == null");
            return;
        }
        if (event.getFile() == null) {
            System.err.println("\t[pwl]\tevent.getFile() == null");
            return;
        }
        final String filename = String.format("%s-%d-%s-%s",
                event.getFile().getNameSequence(),
                event.getFile().getModificationStamp(),
                event.getFile().getModificationCount(),
                postfix);
        final Path path = PATH_PRODUCER.apply(filename);
        try {
            java.nio.file.Files.write(path, event.getFile().contentsToByteArray());
            LOG.debug("\t[pwl]\t" + path.toAbsolutePath() + " written");
        } catch (IOException e) {
            System.err.println("\t[pwl]\tUnable to write to file " + path.toAbsolutePath());
        }
    }
}
