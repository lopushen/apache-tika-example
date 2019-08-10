package net.omisoft.tikaapp;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class Main {
    private static org.apache.log4j.Logger log = Logger.getLogger(Main.class);

    public static void main(String... aArgs) throws IOException {
        log.info("Starting search...");
        FileProvider fileProvider = new FileProvider();
        List<File> files = fileProvider.getFiles("files");
        FileParser parser = new FileParser();
        parser.parseAll(files);
    }
} 