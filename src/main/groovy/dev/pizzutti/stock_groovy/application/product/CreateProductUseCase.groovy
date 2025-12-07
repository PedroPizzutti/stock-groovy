package dev.pizzutti.stock_groovy.application.product

import dev.pizzutti.stock_groovy.domain.product.Product
import dev.pizzutti.stock_groovy.domain.product.ProductException
import dev.pizzutti.stock_groovy.domain.product.ProductRepository

class CreateProductUseCase {

    private final ProductRepository repository

    CreateProductUseCase(ProductRepository repository) {
        this.repository = repository
    }

    Product execute(Product product) {
        if (repository.findByCodBar(product.codBar).isPresent()) {
            throw new ProductException(["Product with 'codBar' '${product.codBar}' already exists"])
        }
        repository.save(product)
    }
}
