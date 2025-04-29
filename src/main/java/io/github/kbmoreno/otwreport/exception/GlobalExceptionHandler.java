package io.github.kbmoreno.otwreport.exception;

import io.github.kbmoreno.otwreport.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        Object invalidValue = e.getValue();
        String paramName = e.getName();
        Class<?> expectedType = e.getRequiredType();
        String expectedTypeName = expectedType != null ? expectedType.getSimpleName().toLowerCase() : " correct type.";
        String message = String.format("Invalid value '%s' for parameter '%s'. Expected a %s.", invalidValue, paramName, expectedTypeName);

        ErrorResponseDto error = new ErrorResponseDto(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDto> handleUnsatisfiedRequestParameter(UnsatisfiedServletRequestParameterException e, HttpServletRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Unsatisfied parameter conditions",
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponseDto> handleClientError(HttpClientErrorException e, HttpServletRequest request) {
        HttpStatusCode statusCode = e.getStatusCode();

        if (statusCode == HttpStatus.UNAUTHORIZED) {
            log.error("Invalid API key used for Open Weather Map's API.");
            ErrorResponseDto error = new ErrorResponseDto(
                    Instant.now(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    "A problem has occurred. Please try again.",
                    request.getRequestURI()
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ErrorResponseDto error = new ErrorResponseDto(
                Instant.now(),
                statusCode.value(),
                statusCode.toString(),
                e.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, statusCode);
    }

    @ExceptionHandler(NullResponseException.class)
    public ResponseEntity<ErrorResponseDto> handleNullResponse(NullResponseException e, HttpServletRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
                Instant.now(),
                HttpStatus.BAD_GATEWAY.value(),
                HttpStatus.BAD_GATEWAY.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(MissingJsonNodeException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingJsonNode(MissingJsonNodeException e, HttpServletRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
                Instant.now(),
                HttpStatus.BAD_GATEWAY.value(),
                HttpStatus.BAD_GATEWAY.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
    }
}

