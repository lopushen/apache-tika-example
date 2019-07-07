package net.omisoft.tikaapp.fileprocessing;

import net.omisoft.tikaapp.tikautils.EmailContentHandler;
import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class FileParser {
    private static org.apache.log4j.Logger log = Logger.getLogger(FileParser.class);


    public void parseAll(List<File> files) {
        files.forEach(this::parseSingleFile);
    }

    private List<String> parseSingleFile(File file) {
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

        return Arrays.stream(emails).collect(Collectors.toList());
    }
}
