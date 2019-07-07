package net.omisoft.tikaapp;

import net.omisoft.tikaapp.fileprocessing.FileProvider;
import net.omisoft.tikaapp.fileprocessing.ReportWriter;
import net.omisoft.tikaapp.fileprocessing.FileParser;
import net.omisoft.tikaapp.utils.PropertiesHolder;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import static net.omisoft.tikaapp.utils.PropertiesHolder.inputFolderName;
import static net.omisoft.tikaapp.utils.PropertiesHolder.outputFolderName;


public final class Main {
    private static org.apache.log4j.Logger log = Logger.getLogger(Main.class);
    public static String fileName;


    public static void main(String... aArgs) {
        PropertiesHolder.init(aArgs);
        PropertyConfigurator.configure("log4j.properties");
        log.info("Processing folder " + inputFolderName + ", Report file dir: " + outputFolderName);
        // create the actual filename and delete the existing if exists
        fileName = outputFolderName + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        new File(fileName).delete();
        new File(outputFolderName).mkdirs();
        ReportWriter reportGenerator = ReportWriter.getInstance();
        reportGenerator.createFileIfNotExists();
        FileProvider fileProvider = new FileProvider();
        ConcurrentLinkedQueue<File> files = fileProvider.getFiles(inputFolderName);
        FileParser parser = new FileParser();
//        parser.parseAll(files);
    }
} 