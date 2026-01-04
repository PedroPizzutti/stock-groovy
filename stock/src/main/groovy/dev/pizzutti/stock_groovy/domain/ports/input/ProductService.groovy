package dev.pizzutti.stock_groovy.domain.ports.input

import dev.pizzutti.stock_groovy.domain.entities.Product

interface ProductService {
    Product create(Product product);
    Product update(Product product);
    Product getById(UUID id);
    List<Product> listAll();
    void delete(UUID id);
}