package dev.pizzutti.stock_groovy.infrastructure.input.rest.exception

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

import java.time.LocalDateTime

@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestExceptionDto> handler( Exception exception, HttpServletRequest request) {
        def dto = new RestExceptionDto(exception.getMessage(), request.requestURI, LocalDateTime.now())
        return ResponseEntity.status(400).body(dto)
    }

}
