package dev.pizzutti.sales_java.infrastructure.adapters.input.rest.dtos;

import dev.pizzutti.sales_java.domain.entities.Sale;

import java.util.List;

public record SaleDto(
        String buyerEmail,
        List<SaleItemDto> items
) {
    public Sale toDomain() {
        var listItems = items.stream().map(SaleItemDto::toDomain).toList();
        return new Sale(null, buyerEmail, listItems);
    }
}
