package dev.pizzutti.sales_java.infrastructure.adapters.input.rest.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    ResponseEntity<RestExceptionDto> handlerUnknown(Exception exception, HttpServletRequest request) {
        var dto = new RestExceptionDto(
                "Unknown error occurred",
                request.getRequestURI(),
                LocalDateTime.now(),
                List.of(exception.getMessage())
        );
        return ResponseEntity.status(500).body(dto);
    }

}