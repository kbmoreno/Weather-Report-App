package io.github.kbmoreno.otwreport.exception;

public class IncompleteApiResponseException extends RuntimeException {
    public IncompleteApiResponseException(String message) {
        super(message);
    }

    public IncompleteApiResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncompleteApiResponseException(Throwable cause) {
        super(cause);
    }
}
