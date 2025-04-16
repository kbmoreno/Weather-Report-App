package io.github.kbmoreno.otwreport.exception;

public class ExternalApiDataNotFoundException extends RuntimeException {
    public ExternalApiDataNotFoundException(String message) {
        super(message);
    }

    public ExternalApiDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalApiDataNotFoundException(Throwable cause) {
        super(cause);
    }
}
