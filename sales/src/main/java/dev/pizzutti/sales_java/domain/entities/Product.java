package dev.pizzutti.sales_java.domain.entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class Product {
    private UUID id;
    private String name;
    private String description;
    private String codBar;
    private BigDecimal price;

    public Product(UUID id, String name, String description, String codBar, BigDecimal price) {
        this.id = id != null ? id : UUID.randomUUID();
        this.name = name.toUpperCase();
        this.description = description.toUpperCase();
        this.codBar = codBar;
        this.price = price;
    }
}
