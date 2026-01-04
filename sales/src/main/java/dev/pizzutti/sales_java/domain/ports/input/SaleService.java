package dev.pizzutti.sales_java.domain.ports.input;

import dev.pizzutti.sales_java.domain.entities.Product;
import dev.pizzutti.sales_java.domain.entities.Sale;

import java.util.List;

public interface SaleService {
    List<Product> listProducts();
    Sale makeSale(Sale sale);
}
