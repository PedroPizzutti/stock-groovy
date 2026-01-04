package dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.imp;

import dev.pizzutti.sales_java.domain.entities.Sale;
import dev.pizzutti.sales_java.domain.ports.output.SaleRepository;
import dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.entities.SaleJpaEntity;
import dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.repositories.SaleJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class SaleRepositoryImp implements SaleRepository {

    private final SaleJpaRepository repository;

    public SaleRepositoryImp(SaleJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Sale save(Sale sale) {
        var saved = repository.save(SaleJpaEntity.fromDomain(sale));
        return saved.toDomain();
    }
}
