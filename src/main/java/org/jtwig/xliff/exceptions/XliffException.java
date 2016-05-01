package org.jtwig.xliff.exceptions;

public class XliffException extends RuntimeException {
    public XliffException(Throwable cause) {
        super(cause);
    }

    public XliffException(String message) {
        super(message);
    }
}
