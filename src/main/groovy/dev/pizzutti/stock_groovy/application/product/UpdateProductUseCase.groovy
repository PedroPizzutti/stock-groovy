package dev.pizzutti.stock_groovy.application.product

import dev.pizzutti.stock_groovy.domain.product.Product
import dev.pizzutti.stock_groovy.domain.product.ProductRepository

class UpdateProductUseCase {

    private final ProductRepository repository

    UpdateProductUseCase(ProductRepository repository) {
        this.repository = repository
    }

    Product execute(Product product) {
        def persistedProduct = repository.findById(product.id)
                .orElseThrow { new RuntimeException("Product with id '${product.id}' not found") }

        if ((persistedProduct.codBar != product.codBar) && (repository.findByCodBar(product.codBar).isPresent())) {
            throw new RuntimeException("Product with 'codBar' '${product.codBar}' already exists")
        }

        repository.save(product)
    }
}
