package dev.pizzutti.sales_java.domain.entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Sale {
    private UUID id;
    private String buyerEmail;
    private List<SaleItem> items;

    public Sale(UUID id, String buyerEmail, List<SaleItem> items) {
        this.id = id != null ? id : UUID.randomUUID();
        this.buyerEmail = buyerEmail;
        this.items = items != null ? items : new ArrayList<>();
    }

    public BigDecimal getTotal() {
        return items.stream().map(SaleItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
