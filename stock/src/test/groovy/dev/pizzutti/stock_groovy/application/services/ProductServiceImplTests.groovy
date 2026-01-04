package dev.pizzutti.stock_groovy.application.services

import dev.pizzutti.stock_groovy.domain.entities.Product
import dev.pizzutti.stock_groovy.domain.exception.ProductException
import dev.pizzutti.stock_groovy.domain.ports.output.ProductRepository
import spock.lang.Specification

class ProductServiceImplTests extends Specification {

    def "should create product when codBar is unique"() {
        given:
            ProductRepository repository = Stub()
            def service = new ProductServiceImpl(repository)

            def product = validProduct()

            repository.findByCodBar(_ as String) >> Optional.empty()
            repository.save(_ as Product) >> { Product p -> p }

        when:
            def result = service.create(product)

        then:
            result == product
    }

    def "should throw exception when creating product with duplicated codBar"() {
        given:
            ProductRepository repository = Stub()
            def service = new ProductServiceImpl(repository)

            def product = validProduct()

            repository.findByCodBar("12345678") >> Optional.of(product)

        when:
            service.create(product)

        then:
            def ex = thrown(ProductException)
            ex.errors.contains("Product with 'codBar' '12345678' already exists")
    }

    def "should update product without validating codBar when it does not change"() {
        given:
            ProductRepository repository = Stub()
            def service = new ProductServiceImpl(repository)

            def product = validProduct()

            repository.findById(product.id) >> Optional.of(product)
            repository.save(_ as Product) >> { Product p -> p }

        when:
            def result = service.update(product)

        then:
            result == product
    }

    def "should validate codBar when updating product and codBar changes"() {
        given:
            ProductRepository repository = Stub()
            def service = new ProductServiceImpl(repository)

            def persisted = validProduct()
            def updated = new Product(
                    persisted.id,
                    persisted.name,
                    "87654321",
                    persisted.storageArea,
                    persisted.quantity,
                    persisted.createdAt
            )

            repository.findById(persisted.id) >> Optional.of(persisted)
            repository.findByCodBar("87654321") >> Optional.empty()
            repository.save(_ as Product) >> { Product p -> p }

        when:
            def result = service.update(updated)

        then:
            result == updated
    }

    def "should throw exception when updating product with duplicated codBar"() {
        given:
            ProductRepository repository = Stub()
            def service = new ProductServiceImpl(repository)

            def persisted = validProduct()
            def other = validProduct()

            def updated = new Product(
                    persisted.id,
                    persisted.name,
                    "87654321",
                    persisted.storageArea,
                    persisted.quantity,
                    persisted.createdAt
            )

            repository.findById(persisted.id) >> Optional.of(persisted)
            repository.findByCodBar("87654321") >> Optional.of(other)

        when:
            service.update(updated)

        then:
            def ex = thrown(ProductException)
            ex.errors.contains("Product with 'codBar' '87654321' already exists")
    }

    def "should throw exception when product is not found by id"() {
        given:
            ProductRepository repository = Stub()
            def service = new ProductServiceImpl(repository)

            def id = UUID.randomUUID()

            repository.findById(id) >> Optional.empty()

        when:
            service.getById(id)

        then:
            def ex = thrown(ProductException)
            ex.errors.contains("Product with id '${id}' not found")
    }

    def "should delete product when it exists"() {
        given:
            ProductRepository repository = Stub()
            def service = new ProductServiceImpl(repository)

            def product = validProduct()

            repository.findById(product.id) >> Optional.of(product)

        when:
            service.delete(product.id)

        then:
            noExceptionThrown()
    }

    private static Product validProduct() {
        new Product(
                UUID.randomUUID(),
                "Produto Valido",
                "12345678",
                "AREA-00001",
                10L,
                null
        )
    }
}
