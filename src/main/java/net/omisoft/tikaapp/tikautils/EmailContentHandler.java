package net.omisoft.tikaapp.tikautils;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.ContentHandlerDecorator;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.util.Arrays;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class EmailContentHandler extends ContentHandlerDecorator {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", CASE_INSENSITIVE);

    private static final String EMAILS = "emails";

    private Metadata metadata;
    private StringBuilder stringBuilder;

    public EmailContentHandler(ContentHandler handler, Metadata metadata) {
        super(handler);
        this.metadata = metadata;
        this.stringBuilder = new StringBuilder();
    }

    /**
     * This method basically collects the text in the document into a
     * big single string
     * @param ch character array
     * @param start the start position
     * @param length the end position
     * @throws SAXException
     */

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        try {
            String text = new String(Arrays.copyOfRange(ch, start, start + length));
            stringBuilder.append(text);
            super.characters(ch, start, length);
        } catch (SAXException e) {
            handleException(e);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
