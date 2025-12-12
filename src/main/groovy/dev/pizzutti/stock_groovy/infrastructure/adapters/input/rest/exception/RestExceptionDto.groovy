package dev.pizzutti.stock_groovy.infrastructure.adapters.input.rest.exception

import java.time.LocalDateTime

record RestExceptionDto(String message, String path, LocalDateTime timestamp, List<String> errors) {}
