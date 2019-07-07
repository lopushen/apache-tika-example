package net.omisoft.tikaapp.fileprocessing;

import java.io.File;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ConcurrentLinkedQueue;

final class StatefulSimpleFileVisitor extends SimpleFileVisitor<Path> {
    private ConcurrentLinkedQueue<File> files = new ConcurrentLinkedQueue<>();

    @Override
    public FileVisitResult visitFile(
            Path aFile, BasicFileAttributes aAttrs
    ) {
        files.add(aFile.toFile());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(
            Path aDir, BasicFileAttributes aAttrs
    ) {
        return FileVisitResult.CONTINUE;
    }

    public ConcurrentLinkedQueue<File> getFiles() {
        return files;
    }
}
