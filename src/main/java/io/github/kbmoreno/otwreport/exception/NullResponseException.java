package io.github.kbmoreno.otwreport.exception;

public class NullResponseException extends RuntimeException {
    public NullResponseException(String message) {
        super(message);
    }

    public NullResponseException(Throwable cause) {
        super(cause);
    }

    public NullResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
