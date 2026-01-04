package dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.entities;

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

    @Column(name = "value", precision = 19, scale = 2, nullable = false)
    private BigDecimal value;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
