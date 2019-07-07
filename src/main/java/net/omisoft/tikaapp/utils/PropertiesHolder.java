package net.omisoft.tikaapp.utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHolder {
    private static org.apache.log4j.Logger log = Logger.getLogger(PropertiesHolder.class);
    public static String inputFolderName = "tikatest1";
    public static String outputFolderName = "report";
    public static String locationsFileName = "allCountries.txt";
    public static String phoneDictionary = "tel|phone|telephone";
    public static Integer numberOfThreads = 8;
    public static Integer parsingTimeout = 60000;
    public static Integer maxNumberOfFiles = 100;

    public static  void init(String... aArgs) {
        if (aArgs.length < 2 && aArgs.length != 0) {
            log.error("Insufficient arguments: 1st argument should be input directory, 2nd - the name of the report file");
            throw new RuntimeException("Insufficient arguments: 1st argument should be input directory, 2nd - the name of the report file");
        } else if (aArgs.length > 2) {
            log.error("Too many arguments: 1st argument should be input directory, 2nd - the name of the report file");
            throw new RuntimeException("Too many arguments: 1st argument should be input directory, 2nd - the name of the report file");
        } else if (aArgs.length == 2) {
            // first, load all the props from the file
            loadProperies();
            inputFolderName = aArgs[0];
            outputFolderName = aArgs[1];
        } else {
            loadProperies();
            log.info("Running with default arguments");
        }
    }

    private static void loadProperies() {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream("./app.properties"));
            log.info("Loaded file from app.properties");
            locationsFileName = appProps.getProperty("app.default.countryfile");
            outputFolderName = appProps.getProperty("app.default.reportfile");
            inputFolderName = appProps.getProperty("app.default.inputfolder");
            phoneDictionary = appProps.getProperty("app.address.dictionary");
            numberOfThreads = Integer.valueOf(appProps.getProperty("app.default.threads"));
            parsingTimeout = Integer.valueOf(appProps.getProperty("app.parsing.timeout.milliseconds"));
            maxNumberOfFiles = Integer.valueOf(appProps.getProperty("app.parsing.max.files"));
        } catch (IOException e) {
            log.info("No property file found, running with predefined properties :\n" +
                    "Locations file name = " + locationsFileName + " \n" +
                    "Report file name = " + outputFolderName + " \n" +
                    "Input folder name = " + inputFolderName + " \n" +
                    "Threads count = " + String.valueOf(numberOfThreads)+ " \n"+
                    "Parsing timeout = " + String.valueOf(parsingTimeout) + " \n"+
                    "Max number of files = " + String.valueOf(maxNumberOfFiles) + " \n"
            );
        }
    }
}
