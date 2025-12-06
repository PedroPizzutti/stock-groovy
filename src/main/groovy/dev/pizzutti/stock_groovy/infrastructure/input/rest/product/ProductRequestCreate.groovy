package dev.pizzutti.stock_groovy.infrastructure.input.rest.product

import dev.pizzutti.stock_groovy.domain.product.Product

record ProductRequestCreate(String name, String codBar, String storageArea, Long quantity) {
    Product toDomain() {
        new Product(name, codBar, storageArea, quantity)
    }
}
