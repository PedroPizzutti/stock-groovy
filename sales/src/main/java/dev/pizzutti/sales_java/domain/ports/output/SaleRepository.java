package dev.pizzutti.sales_java.domain.ports.output;

import dev.pizzutti.sales_java.domain.entities.Sale;

public interface SaleRepository {
    Sale save(Sale sale);
}
