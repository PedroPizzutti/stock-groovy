package dev.pizzutti.stock_groovy.domain.entities

import dev.pizzutti.stock_groovy.domain.exception.ProductException
import spock.lang.Specification
import spock.lang.Unroll

class ProductTests extends Specification {

    def "should create product with valid data"() {
        when:
            def product = new Product(
                    null,
                    "Arroz Branco",
                    "0060876497670",
                    "AREA-00001",
                    10,
                    null
            )
        then:
            product.id != null
            product.name == "ARROZ BRANCO"
            product.codBar == "0060876497670"
            product.storageArea == "AREA-00001"
            product.quantity == 10
            product.createdAt != null
    }


    @Unroll
    def "should throw exception when name is invalid: '#name'"() {
        when:
        new Product(
                null,
                name,
                "0060876497670",
                "AREA-00001",
                10,
                null
        )

        then:
            def ex = thrown(ProductException)
            ex.errors.contains("'name' must have at least 3 characters")

        where:
            name << ["A", "Ab", ""]
    }

    @Unroll
    def "should throw error when codBar length is invalid: '#codBar'"() {
        when:
            new Product(
                    null,
                    "Produto Valido",
                    codBar,
                    "AREA-00001",
                    1L,
                    null
            )

        then:
            def ex = thrown(ProductException)
            ex.errors.contains("'codBar' must follow EAN-8 or EAN-13")

        where:
            codBar << [
                    "1",
                    "1234567",
                    "123456789",
                    "123456789012",
                    "12345678901234"
            ]
    }

    @Unroll
    def "should throw error when storageArea length is invalid: '#storageArea'"() {
        when:
            new Product(
                    null,
                    "Produto Valido",
                    "12345678",
                    storageArea,
                    1,
                    null
            )

        then:
            def ex = thrown(ProductException)
            ex.errors.contains("'storageArea' must have exactly 10 characters")

        where:
            storageArea << [
                    "",
                    "A",
                    "AREA-1",
                    "AREA-0000",
                    "AREA-000001",
                    "123456789"
            ]
    }

    @Unroll
    def "should throw error when quantity is negative: #quantity"() {
        when:
            new Product(
                    null,
                    "Produto Valido",
                    "12345678",
                    "AREA-00001",
                    quantity,
                    null
            )

        then:
            def ex = thrown(ProductException)
            ex.errors.contains("'quantity' must not be negative")

        where:
            quantity << [-1, -10, -999]
    }

}
