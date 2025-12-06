package dev.pizzutti.stock_groovy.application.product

import dev.pizzutti.stock_groovy.domain.product.Product
import dev.pizzutti.stock_groovy.domain.product.ProductRepository

class RetrieveProductUseCase {

    private final ProductRepository repository

    RetrieveProductUseCase(ProductRepository repository) {
        this.repository = repository
    }

    Product execute(UUID id) {
        repository.findById(id).orElseThrow { new RuntimeException("Product with id '${id}' not found") }
    }

}
