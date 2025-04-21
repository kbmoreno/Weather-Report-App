package io.github.kbmoreno.otwreport.exception;

public class MissingJsonNodeException extends RuntimeException {
    public MissingJsonNodeException(String message) {
        super(message);
    }

    public MissingJsonNodeException(Throwable cause) {
        super(cause);
    }

    public MissingJsonNodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
