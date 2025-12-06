package dev.pizzutti.stock_groovy.domain.product

import java.time.LocalDateTime

class Product {
    UUID id
    String name
    String codBar
    String storageArea
    Long quantity
    LocalDateTime createdAt

    Product(UUID id = null, String name, String codBar, String storageArea, Long quantity,
            LocalDateTime createdAt = null) {
        if (name.length() <= 3) {
            throw new RuntimeException("'name' must have at least 3 characters")
        }
        if (codBar.length() != 8 && codBar.length() != 13) {
            throw new RuntimeException("'codBar' must follow EAN-8 or EAN-13")
        }
        if (storageArea.length() != 10) {
            throw new RuntimeException("'storageArea' must have exactly 10 characters")
        }
        if (quantity < 0) {
            throw new RuntimeException("'quantity' must not be negative")
        }
        this.id = id ?: UUID.randomUUID()
        this.name = name
        this.codBar = codBar
        this.storageArea = storageArea
        this.quantity = quantity
        this.createdAt = createdAt ?: LocalDateTime.now()
    }
}
