package net.omisoft.tikaapp;

import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class FileParser {
    private static org.apache.log4j.Logger log = Logger.getLogger(FileParser.class);


    public void parseAll(List<File> files) throws IOException {
        List<ParsingResult> parsingResults = files.stream().map(this::parseSingleFile).collect(Collectors.toList());
        try (PrintWriter pw = new PrintWriter(new FileWriter("report.csv"))) {
            pw.println("Email,File");
            parsingResults.stream().filter(r -> r.emails != null && r.emails.length > 0).forEach(r -> {
                Arrays.stream(r.emails).forEach(email -> pw.println(String.format("%s,%s", email, r.fileName)));
            });
        }
    }

    private ParsingResult parseSingleFile(File file) {
        log.info("Started parsing file " + file.getName());
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        EmailContentHandler addressHandler = new EmailContentHandler(new BodyContentHandler(-1), metadata);

        try (InputStream stream = new FileInputStream(file)) {
            ParseContext context = new ParseContext();
            parser.parse(stream, addressHandler, metadata, context);
        } catch (SAXException | TikaException | IOException e) {
            log.error("Error parsing text " + e.getMessage());
        }

        String[] emails = metadata.getValues("emails");
        log.info("Ended parsing file " + file.getName());

        return new ParsingResult(file.getName(), emails);
    }

    private static class ParsingResult {
        private String fileName;
        private String[] emails;

        public ParsingResult(String fileName, String[] emails) {
            this.fileName = fileName;
            this.emails = emails;
        }
    }
}
