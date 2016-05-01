package org.jtwig.xliff.exceptions;

public class XliffParsingException extends Exception {
    public XliffParsingException(Throwable cause) {
        super(cause);
    }

    public XliffParsingException(String message) {
        super(message);
    }
}
