package dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.imp;

import dev.pizzutti.sales_java.domain.entities.Product;
import dev.pizzutti.sales_java.domain.ports.output.ProductRepository;
import dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.entities.ProductJpaEntity;
import dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.repositories.ProductJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductRepositoryImp implements ProductRepository {

    private final ProductJpaRepository repository;

    public ProductRepositoryImp(ProductJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream().map(ProductJpaEntity::toDomain).toList();
    }
}
