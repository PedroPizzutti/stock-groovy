package dev.pizzutti.sales_java.domain.ports.output;

import dev.pizzutti.sales_java.domain.entities.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
}
