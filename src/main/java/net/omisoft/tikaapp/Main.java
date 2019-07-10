package net.omisoft.tikaapp;

import net.omisoft.tikaapp.fileprocessing.FileParser;
import net.omisoft.tikaapp.fileprocessing.FileProvider;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class Main {
    private static org.apache.log4j.Logger log = Logger.getLogger(Main.class);
    private static final String inputFolderName = "files";


    public static void main(String... aArgs) throws IOException {
        log.info("Starting search...");
        FileProvider fileProvider = new FileProvider();
        List<File> files = fileProvider.getFiles(inputFolderName);
        FileParser parser = new FileParser();
        parser.parseAll(files);
    }
} 