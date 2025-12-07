package dev.pizzutti.stock_groovy.infrastructure.input.rest.exception

import dev.pizzutti.stock_groovy.domain.exception.DomainException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

import java.time.LocalDateTime

@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<RestExceptionDto> handlerDomain( DomainException exception, HttpServletRequest request) {
        def dto = new RestExceptionDto(
                exception.getMessage(),
                request.requestURI,
                LocalDateTime.now(),
                exception.getErrors().collect {it.toString()}
        )
        return ResponseEntity.status(400).body(dto)
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestExceptionDto> handlerUnknown( Exception exception, HttpServletRequest request) {
        def dto = new RestExceptionDto(
                "Unknown error occurred",
                request.requestURI,
                LocalDateTime.now(),
                List.of(exception.getMessage())
        )
        return ResponseEntity.status(500).body(dto)
    }

}
