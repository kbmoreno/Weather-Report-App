package io.github.kbmoreno.otwreport.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<LinkedHashMap<String, String>> handlerArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        LinkedHashMap<String, String> badResponse = new LinkedHashMap<>();
        badResponse.put("status", "invalid_args");
        badResponse.put("message", "Invalid value provided for the parameter");
        badResponse.put("parameter", e.getName());
        
        return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncompleteApiResponseException.class)
    public ResponseEntity<LinkedHashMap<String, String>> handlerIncompleteApiResponse(IncompleteApiResponseException e) {
        LinkedHashMap<String, String> badResponse = new LinkedHashMap<>();
        badResponse.put("status", "incomplete_data");
        badResponse.put("message", e.getMessage());

        return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
    }
}

