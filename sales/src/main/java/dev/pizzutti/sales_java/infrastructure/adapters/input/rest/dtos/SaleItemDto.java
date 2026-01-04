package dev.pizzutti.sales_java.infrastructure.adapters.input.rest.dtos;

import dev.pizzutti.sales_java.domain.entities.SaleItem;

public record SaleItemDto(
        ProductDto product,
        Integer quantity
) {
    SaleItem toDomain() {
        return new SaleItem(null, product.toDomain(), quantity);
    }
}