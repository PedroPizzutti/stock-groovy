package dev.pizzutti.stock_groovy.infrastructure.output.persistence.product

import org.springframework.data.jpa.repository.JpaRepository

interface JpaRepositoryProduct extends JpaRepository<JpaEntityProduct, UUID> {
    Optional<JpaEntityProduct> findByCodBar(String codBar)
}
