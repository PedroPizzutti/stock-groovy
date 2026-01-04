package dev.pizzutti.sales_java.domain.ports.output;

public interface StockExternalCall {
    void reserve(String codBar, Integer quantity);
}
