package dev.pizzutti.stock_groovy.domain.product

import groovy.transform.Canonical

import java.time.LocalDateTime

@Canonical
class Product {
    UUID id
    String name
    String codBar
    String storageArea
    Long quantity
    LocalDateTime createdAt

    Product(UUID id = null, String name, String codBar, String storageArea, Long quantity,
            LocalDateTime createdAt = null) {

        List<String> errors = []

        if (name.length() <= 3) {
            errors.add("'name' must have at least 3 characters")
        }
        if (codBar.length() != 8 && codBar.length() != 13) {
            errors.add("'codBar' must follow EAN-8 or EAN-13")
        }
        if (storageArea.length() != 10) {
            errors.add("'storageArea' must have exactly 10 characters")
        }
        if (quantity < 0) {
            errors.add("'quantity' must not be negative")
        }

        ProductException.throwIfAny(errors)

        this.id = id ?: UUID.randomUUID()
        this.name = name
        this.codBar = codBar
        this.storageArea = storageArea
        this.quantity = quantity
        this.createdAt = createdAt ?: LocalDateTime.now()
    }
}
