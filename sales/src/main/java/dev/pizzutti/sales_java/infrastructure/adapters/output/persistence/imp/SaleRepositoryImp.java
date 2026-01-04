package dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.imp;

import dev.pizzutti.sales_java.domain.entities.Sale;
import dev.pizzutti.sales_java.domain.ports.output.SaleRepository;
import dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.mappers.SaleJpaMapper;
import dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.repositories.SaleJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class SaleRepositoryImp implements SaleRepository {

    private final SaleJpaRepository repository;
    private final SaleJpaMapper mapper;

    public SaleRepositoryImp(SaleJpaRepository repository,
                             SaleJpaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Sale save(Sale sale) {
        return mapper.toDomain(repository.save(mapper.toEntity(sale)));
    }
}
