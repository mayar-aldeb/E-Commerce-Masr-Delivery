package org.route.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.route.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<ErrorResponse> handleException(HttpClientErrorException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message(ex.getMessage())
                        .status(ex.getStatusCode())
                        .path(request.getRequestURI())
                        .error(request.getMethod())
                        .build(),
                ex.getStatusCode());
    }

}
