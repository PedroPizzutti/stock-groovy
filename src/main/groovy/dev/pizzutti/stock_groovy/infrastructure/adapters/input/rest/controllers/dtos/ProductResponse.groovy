package dev.pizzutti.stock_groovy.infrastructure.adapters.input.rest.controllers.dtos

import dev.pizzutti.stock_groovy.domain.entities.Product

import java.time.LocalDateTime

record ProductResponse (
        UUID id,
        String name,
        String codBar,
        String storageArea,
        Long quantity,
        LocalDateTime createdAt) {

    static fromDomain(Product product) {
        return new ProductResponse(
                product.id,
                product.name,
                product.codBar,
                product.storageArea,
                product.quantity,
                product.createdAt
        )
    }

}
