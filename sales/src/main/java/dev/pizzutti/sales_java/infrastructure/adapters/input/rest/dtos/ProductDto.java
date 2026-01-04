package dev.pizzutti.sales_java.infrastructure.adapters.input.rest.dtos;

import dev.pizzutti.sales_java.domain.entities.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDto(
        String id,
        String name,
        String description,
        String codBar,
        BigDecimal price
) {
    Product toDomain() {
        return new Product(UUID.fromString(id), name, description, codBar, price);
    }
}