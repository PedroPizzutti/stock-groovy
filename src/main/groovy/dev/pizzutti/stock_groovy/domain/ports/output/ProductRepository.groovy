package dev.pizzutti.stock_groovy.domain.ports.output

import dev.pizzutti.stock_groovy.domain.entities.Product

interface ProductRepository {
    Product save(Product product)
    Optional<Product> findById(UUID id)
    Optional<Product> findByCodBar(String codBar)
    List<Product> findAll()
    void delete(UUID id)
}
