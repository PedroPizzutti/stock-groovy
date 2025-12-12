package dev.pizzutti.stock_groovy.infrastructure.adapters.input.rest.exception

import dev.pizzutti.stock_groovy.domain.exception.DomainException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

import java.time.LocalDateTime

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(DomainException.class)
    ResponseEntity<RestExceptionDto> handlerDomain( DomainException exception, HttpServletRequest request) {
        def dto = new RestExceptionDto(
                exception.getMessage(),
                request.requestURI,
                LocalDateTime.now(),
                exception.getErrors().collect {it.toString()}
        )
        return ResponseEntity.status(400).body(dto)
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<RestExceptionDto> handlerUnknown( Exception exception, HttpServletRequest request) {
        def dto = new RestExceptionDto(
                "Unknown error occurred",
                request.requestURI,
                LocalDateTime.now(),
                List.of(exception.getMessage())
        )
        return ResponseEntity.status(500).body(dto)
    }

}
