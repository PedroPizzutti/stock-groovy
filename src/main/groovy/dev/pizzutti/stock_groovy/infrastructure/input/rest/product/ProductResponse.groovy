package dev.pizzutti.stock_groovy.infrastructure.input.rest.product

import dev.pizzutti.stock_groovy.domain.product.Product

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
