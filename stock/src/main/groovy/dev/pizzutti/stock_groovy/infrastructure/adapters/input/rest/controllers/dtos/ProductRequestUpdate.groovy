package dev.pizzutti.stock_groovy.infrastructure.adapters.input.rest.controllers.dtos

import dev.pizzutti.stock_groovy.domain.entities.Product

import java.time.LocalDateTime

record ProductRequestUpdate(String name, String codBar, String storageArea, Long quantity, LocalDateTime createdAt) {
    Product toDomain(UUID id) {
        new Product(id, name, codBar, storageArea, quantity, createdAt)
    }
}

