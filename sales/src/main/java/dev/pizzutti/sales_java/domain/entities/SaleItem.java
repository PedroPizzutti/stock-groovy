package dev.pizzutti.sales_java.domain.entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class SaleItem {
    private UUID id;
    private Product product;
    private BigDecimal value;
    private Integer quantity;

    public SaleItem(UUID id, Product product, BigDecimal value, Integer quantity) {
        this.id = id;
        this.product = product;
        this.value = value;
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return value.multiply(BigDecimal.valueOf(quantity));
    }

}
