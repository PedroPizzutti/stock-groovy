package dev.pizzutti.stock_groovy.application.product

import dev.pizzutti.stock_groovy.domain.product.Product
import dev.pizzutti.stock_groovy.domain.product.ProductRepository

class ListProductUseCase {

    private final ProductRepository repository

    ListProductUseCase(ProductRepository repository) {
        this.repository = repository
    }

    List<Product> execute() {
        repository.findAll()
    }

}
