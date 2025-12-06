package dev.pizzutti.stock_groovy.infrastructure.input.rest.exception

import java.time.LocalDateTime

record RestExceptionDto(String error, String path, LocalDateTime timestamp) {
}
