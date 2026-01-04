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
    private BigDecimal price;
    private Integer quantity;

    public SaleItem(UUID id, Product product, Integer quantity) {
        this.id = id != null ? id : UUID.randomUUID();
        this.product = product;
        this.price = product.getPrice();
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

}
