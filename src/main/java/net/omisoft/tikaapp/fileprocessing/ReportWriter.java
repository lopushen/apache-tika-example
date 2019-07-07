package net.omisoft.tikaapp.fileprocessing;

import net.omisoft.tikaapp.Main;
import net.omisoft.tikaapp.domain.Row;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ReportWriter {
    private static final ReportWriter inst = new ReportWriter();

    private static org.apache.log4j.Logger log = Logger.getLogger(ReportWriter.class);

    private ReportWriter() {
        super();
    }

    private static final String[] HEADER = {"folder", "filename", "addresses count", "phone count", "phone_flag"};
    private static final String DELIMETER = ";";

    public void createFileIfNotExists() {
        File yourFile = new File(Main.fileName);
        try (FileOutputStream oFile = new FileOutputStream(yourFile, false);
            PrintWriter pw = new PrintWriter(oFile)) {
            yourFile.createNewFile();
            pw.println(Arrays.stream(HEADER).collect(Collectors.joining(DELIMETER)));
        } catch (IOException e) {
            log.error("Error writing to the file " + Main.fileName + " " + e.getMessage());
        }
    }

    public synchronized void writeToFile(Row row) {
        try (FileWriter fw = new FileWriter(Main.fileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(row.getFile().getParentFile().getAbsolutePath() + ";" +
                    row.getFile().getName() + ";" + String.valueOf(row.getAddressCount()) + ";" + String.valueOf(row.getPhoneNumberCount())
                    + ";" + row.getContainsDictionary());
        } catch (IOException e) {
            log.error("Error writing to the file " + Main.fileName + " " + e.getMessage());
        }
    }

    public static ReportWriter getInstance() {
        return inst;
    }

}