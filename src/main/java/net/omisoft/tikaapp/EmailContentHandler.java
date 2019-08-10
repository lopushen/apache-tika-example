package net.omisoft.tikaapp;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.ContentHandlerDecorator;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class EmailContentHandler extends ContentHandlerDecorator {

    // Scary RFC email regular expression
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|" +
                            "\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
                            "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])" +
                            "|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]" +
                            ":(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",
                    CASE_INSENSITIVE);

    private Metadata metadata;
    private StringBuilder stringBuilder;

    public EmailContentHandler(ContentHandler handler, Metadata metadata) {
        super(handler);
        this.metadata = metadata;
        this.stringBuilder = new StringBuilder();
    }

    /**
     * This method basically collects the text in the document into a
     * big single string. This is not an option for production, but still lets
     * demonstrate the capabilities of Apache Tika
     *
     * @param ch     character array
     * @param start  the start position
     * @param length the end position
     * @throws SAXException
     */

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String text = new String(Arrays.copyOfRange(ch, start, start + length));
        stringBuilder.append(text);
        super.characters(ch, start, length);
    }

    @Override
    public void endDocument() throws SAXException {
        String input = stringBuilder.toString();
        Matcher m = VALID_EMAIL_ADDRESS_REGEX.matcher(input);
        while (m.find()) {
            String email = m.group();
            metadata.add("emails", email);
        }

        super.endDocument();
    }
}
