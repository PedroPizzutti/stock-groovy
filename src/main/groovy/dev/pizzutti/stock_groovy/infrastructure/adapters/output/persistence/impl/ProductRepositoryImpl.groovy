package dev.pizzutti.stock_groovy.infrastructure.adapters.output.persistence.impl

import dev.pizzutti.stock_groovy.domain.entities.Product
import dev.pizzutti.stock_groovy.domain.ports.output.ProductRepository
import dev.pizzutti.stock_groovy.infrastructure.adapters.output.persistence.jpa.entities.JpaEntityProduct
import dev.pizzutti.stock_groovy.infrastructure.adapters.persistence.jpa.repositories.JpaRepositoryProduct
import org.springframework.stereotype.Component

@Component
class ProductRepositoryImpl implements ProductRepository {

    private final JpaRepositoryProduct jpaRepository;

    ProductRepositoryImpl(JpaRepositoryProduct jpaRepository) {
        this.jpaRepository = jpaRepository
    }

    @Override
    Product save(Product product) {
        def jpaEntity = JpaEntityProduct.fromDomain(product)
        jpaRepository.save(jpaEntity).toDomain()
    }

    @Override
    Optional<Product> findById(UUID id) {
        jpaRepository.findById(id).map {it.toDomain()}
    }

    @Override
    Optional<Product> findByCodBar(String codBar) {
        jpaRepository.findByCodBar(codBar).map {it.toDomain()}
    }

    @Override
    List<Product> findAll() {
        jpaRepository.findAll().collect({it.toDomain()})
    }

    @Override
    void delete(UUID id) {
        jpaRepository.deleteById(id)
    }
}
