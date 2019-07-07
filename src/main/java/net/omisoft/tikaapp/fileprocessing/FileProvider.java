package net.omisoft.tikaapp.fileprocessing;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FileProvider {
    private static org.apache.log4j.Logger log = Logger.getLogger(FileProvider.class);

    public ConcurrentLinkedQueue<File> getFiles(String inputFolderName) {

        StatefulSimpleFileVisitor fileVisitor = new StatefulSimpleFileVisitor();
        try {
            Files.walkFileTree(Paths.get(inputFolderName), fileVisitor);
        } catch (IOException e) {
            log.debug("Problem opening the file " + e.getMessage());
        }
        return fileVisitor.getFiles();
    }
}
