package dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.entities;

import dev.pizzutti.sales_java.domain.entities.Sale;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sale")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleJpaEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "buyer_email", length = 100, nullable = false)
    private String buyerEmail;

    @OneToMany(
        mappedBy = "sale",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private List<SaleProductJpaEntity> items = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public static SaleJpaEntity fromDomain(Sale sale) {
        SaleJpaEntity entity = new SaleJpaEntity();
        entity.setId(sale.getId());
        entity.setBuyerEmail(sale.getBuyerEmail());
        var listItems = sale.getItems().stream().map(item -> SaleProductJpaEntity.fromDomain(item, entity)).toList();
        entity.setItems(listItems);
        return entity;
    }

    public Sale toDomain() {
        var listItems = items.stream().map(SaleProductJpaEntity::toDomain).toList();
        return new Sale(id, buyerEmail, listItems);
    }

}

