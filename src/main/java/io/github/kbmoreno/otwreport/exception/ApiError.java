package io.github.kbmoreno.otwreport.exception;

import java.time.LocalDateTime;

public record ApiError(
        String status,
        String message,
        LocalDateTime timestamp,
        String path,
        int httpStatus
) { }
