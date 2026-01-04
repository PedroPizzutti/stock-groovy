package dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.mappers;

import dev.pizzutti.sales_java.domain.entities.SaleItem;
import dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.entities.SaleProductJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleProductJpaMapper {
    SaleItem toDomain(SaleProductJpaEntity entity);
    SaleProductJpaEntity toEntity(SaleItem domain);
}
