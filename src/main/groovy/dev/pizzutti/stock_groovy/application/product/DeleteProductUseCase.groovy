package dev.pizzutti.stock_groovy.application.product

import dev.pizzutti.stock_groovy.domain.product.ProductRepository

class DeleteProductUseCase {

    private final ProductRepository repository

    DeleteProductUseCase(ProductRepository repository) {
        this.repository = repository
    }

    void execute(UUID id) {
        repository.findById(id).orElseThrow { new RuntimeException("Product with id '${id}' not found") }
        repository.delete(id)
    }

}
