package dev.pizzutti.stock_groovy.application.services

import dev.pizzutti.stock_groovy.domain.entities.Product
import dev.pizzutti.stock_groovy.domain.exception.ProductException
import dev.pizzutti.stock_groovy.domain.ports.input.ProductService
import dev.pizzutti.stock_groovy.domain.ports.output.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductServiceImpl implements ProductService {

    final ProductRepository productRepository;

    ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    Product create(Product product) {
        validateUniqueCodBar(product.codBar)
        productRepository.save(product)
    }

    @Override
    @Transactional
    Product update(Product product) {
        def persistedProduct = getById(product.id)
        def isChangingCodBar = persistedProduct.codBar != product.codBar;
        if (isChangingCodBar) {
            validateUniqueCodBar(product.codBar)
        }
        productRepository.save(product)
    }

    @Override
    Product getById(UUID id) {
         productRepository.findById(id).orElseThrow({ new ProductException(["Product with id '${id}' not found"]) })
    }

    @Override
    List<Product> listAll() {
        productRepository.findAll()
    }

    @Override
    @Transactional
    void delete(UUID id) {
        def persistedProduct = getById(id)
        productRepository.delete(persistedProduct.id)
    }

    @Override
    void reserve(String codBar, Long quantity) {
        int chance = new Random().nextInt(10);
        if (chance < 1) {
            throw new ProductException(["service unavailable".toString()]);
        }
//        var product = productRepository.findByCodBar(codBar)
//                .orElseThrow({ new ProductException(["Product with codBar '${codBar}' not found"]) });
//
//        if (product.getQuantity() < quantity) {
//           throw new ProductException(["Not enough product with codBar '${codBar}'"])
//        }
//
//        product.setQuantity(product.getQuantity() - quantity);
//        productRepository.save(product);
    }

    private void validateUniqueCodBar(String codBar) {
        if (productRepository.findByCodBar(codBar).isPresent()) {
            throw new ProductException(["Product with 'codBar' '${codBar}' already exists".toString()])
        }
    }
}
