package dev.pizzutti.stock_groovy.domain.product

import dev.pizzutti.stock_groovy.domain.exception.DomainException

class ProductException extends DomainException {

    ProductException(List<String> errors) {
        super(errors, "Product validation failed")
    }

    static void throwIfAny(List<String> errors) {
        if (!errors.isEmpty()) {
            throw new ProductException(errors)
        }
    }
}
