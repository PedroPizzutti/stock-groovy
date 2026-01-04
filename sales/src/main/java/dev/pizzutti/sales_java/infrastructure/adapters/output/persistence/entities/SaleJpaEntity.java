package dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.entities;

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

    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

