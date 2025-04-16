package io.github.kbmoreno.otwreport.exception;

public class InvalidApiKeyException extends RuntimeException {

    public InvalidApiKeyException(String message) {
        super(message);
    }

    public InvalidApiKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidApiKeyException(Throwable cause) {
        super(cause);
    }
}
