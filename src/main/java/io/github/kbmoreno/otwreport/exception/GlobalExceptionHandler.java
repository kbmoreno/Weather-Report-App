package io.github.kbmoreno.otwreport.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExternalApiDataNotFoundException.class)
    public ResponseEntity<ApiError> handleException(ExternalApiDataNotFoundException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                "data_not_found",
                e.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.BAD_GATEWAY.value()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                "invalid_args",
                e.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidApiKeyException.class)
    public ResponseEntity<ApiError> handleException(InvalidApiKeyException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                "bad_gateway",
                e.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.BAD_GATEWAY.value()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_GATEWAY);
    }
}

