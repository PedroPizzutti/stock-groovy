package dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.entities;

import dev.pizzutti.sales_java.domain.entities.Sale;
import dev.pizzutti.sales_java.domain.entities.SaleItem;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sale_product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleProductJpaEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sale_id")
    private SaleJpaEntity sale;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private ProductJpaEntity product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", precision = 19, scale = 2, nullable = false)
    private BigDecimal price;

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

    public static SaleProductJpaEntity fromDomain(SaleItem item, SaleJpaEntity sale) {
        return new SaleProductJpaEntity(
                item.getId(),
                sale,
                ProductJpaEntity.fromDomain(item.getProduct()),
                item.getQuantity(),
                item.getPrice(),
                null,
                null
        );
    }

    public SaleItem toDomain() {
        return new SaleItem(id, product.toDomain(), quantity);
    }

}
