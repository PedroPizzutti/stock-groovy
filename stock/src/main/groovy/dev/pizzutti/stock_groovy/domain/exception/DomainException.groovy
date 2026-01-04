package dev.pizzutti.stock_groovy.domain.exception

abstract class DomainException extends RuntimeException {

    final List<String> errors

    protected DomainException(List<String> errors, String message) {
        super(message)
        this.errors = List.copyOf(errors)
    }
}
