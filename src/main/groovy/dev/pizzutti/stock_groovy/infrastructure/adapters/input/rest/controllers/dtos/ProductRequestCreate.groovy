package dev.pizzutti.stock_groovy.infrastructure.adapters.input.rest.controllers.dtos

import dev.pizzutti.stock_groovy.domain.entities.Product

record ProductRequestCreate(String name, String codBar, String storageArea, Long quantity) {
    Product toDomain() {
        new Product(name, codBar, storageArea, quantity)
    }
}
