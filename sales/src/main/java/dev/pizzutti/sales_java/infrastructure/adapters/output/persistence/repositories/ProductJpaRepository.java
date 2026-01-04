package dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.repositories;

import dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.entities.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, UUID> {}
