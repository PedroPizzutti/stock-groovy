package dev.pizzutti.sales_java.domain.ports.output;

public interface StockCallable {
    void reserve(String codBar, Integer quantity);
}
