package dev.pizzutti.sales_java.infrastructure.adapters.input.rest.exception;

import java.time.LocalDateTime;
import java.util.List;

public record RestExceptionDto(String message, String path, LocalDateTime timestamp, List<String> errors) {}