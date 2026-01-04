package dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.imp;

import dev.pizzutti.sales_java.domain.entities.Product;
import dev.pizzutti.sales_java.domain.ports.output.ProductRepository;
import dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.mappers.ProductJpaMapper;
import dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.repositories.ProductJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductRepositoryImp implements ProductRepository {

    private final ProductJpaRepository repository;
    private final ProductJpaMapper mapper;

    public ProductRepositoryImp(ProductJpaRepository repository,
                                ProductJpaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }
}
