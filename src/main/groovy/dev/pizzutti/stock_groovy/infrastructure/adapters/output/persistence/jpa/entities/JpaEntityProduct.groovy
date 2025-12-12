package dev.pizzutti.stock_groovy.infrastructure.adapters.output.persistence.jpa.entities

import dev.pizzutti.stock_groovy.domain.entities.Product
import groovy.transform.Canonical
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

import java.time.LocalDateTime

@Entity
@Table(name = "product")
@Canonical
class JpaEntityProduct {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    UUID id

    @Column(name = "name", nullable =  false, length = 150)
    String name

    @Column(name = "cod_bar", nullable = false, unique = true)
    String codBar

    @Column(name = "storage_area", nullable = false, length = 10)
    String storageArea

    @Column(name = "quantity", nullable = false)
    Long quantity

    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt

    JpaEntityProduct() {}

    static JpaEntityProduct fromDomain(Product product) {
        new JpaEntityProduct(
                id: product.id,
                name: product.name,
                codBar: product.codBar,
                storageArea: product.storageArea,
                quantity: product.quantity,
                createdAt: product.createdAt,
        )
    }

    Product toDomain() {
        new Product(this.id, this.name, this.codBar, this.storageArea, this.quantity, this.createdAt)
    }
}
