package dev.pizzutti.stock_groovy.infrastructure.adapters.persistence.jpa.repositories

import dev.pizzutti.stock_groovy.infrastructure.adapters.persistence.jpa.entities.JpaEntityProduct
import org.springframework.data.jpa.repository.JpaRepository

interface JpaRepositoryProduct extends JpaRepository<JpaEntityProduct, UUID> {
    Optional<JpaEntityProduct> findByCodBar(String codBar)
}
