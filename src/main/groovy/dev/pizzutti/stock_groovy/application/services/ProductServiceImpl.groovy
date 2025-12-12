package dev.pizzutti.stock_groovy.application.services

import dev.pizzutti.stock_groovy.domain.entities.Product
import dev.pizzutti.stock_groovy.domain.exception.ProductException
import dev.pizzutti.stock_groovy.domain.ports.input.ProductService
import dev.pizzutti.stock_groovy.domain.ports.output.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl implements ProductService {

    final ProductRepository productRepository;

    ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    Product create(Product product) {
        if (productRepository.findByCodBar(product.codBar).isPresent()) {
            throw new ProductException(["Product with 'codBar' '${product.codBar}' already exists"])
        }
        return productRepository.save(product)
    }

    @Override
    Product update(Product product) {
        def persistedProduct = productRepository.findById(product.id)
                .orElseThrow { new ProductException(["Product with id '${product.id}' not found"]) }

        if ((persistedProduct.codBar != product.codBar) && (productRepository.findByCodBar(product.codBar).isPresent())) {
            throw new ProductException(["Product with 'codBar' '${product.codBar}' already exists"])
        }
        return productRepository.save(product)
    }

    @Override
    Product getById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow({ new ProductException(["Product with id '${id}' not found"]) })
    }

    @Override
    List<Product> listAll() {
        return productRepository.findAll()
    }

    @Override
    void delete(UUID id) {
        productRepository.delete(id)
    }
}
