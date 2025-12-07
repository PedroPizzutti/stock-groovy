package dev.pizzutti.stock_groovy.infrastructure.output.persistence.product

import dev.pizzutti.stock_groovy.domain.product.Product
import dev.pizzutti.stock_groovy.domain.product.ProductRepository
import org.springframework.stereotype.Service

@Service
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
