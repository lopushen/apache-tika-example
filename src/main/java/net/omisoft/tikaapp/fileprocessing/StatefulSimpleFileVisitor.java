package net.omisoft.tikaapp.fileprocessing;

import java.io.File;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

final class StatefulSimpleFileVisitor extends SimpleFileVisitor<Path> {
    private List<File> files = new ArrayList<>();

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

    public List<File> getFiles() {
        return files;
    }
}
