package dev.pizzutti.sales_java.domain.entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Product {
    private UUID id;
    private String name;
    private String description;
    private String codBar;
    private BigDecimal value;
    private LocalDateTime createdAt;

    public Product(UUID id, String name, String description, String codBar, BigDecimal value, LocalDateTime createdAt) {
        this.id = id != null ? id : UUID.randomUUID();
        this.name = name.toUpperCase();
        this.description = description.toUpperCase();
        this.codBar = codBar;
        this.value = value;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }
}
