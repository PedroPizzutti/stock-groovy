package dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.mappers;

import dev.pizzutti.sales_java.domain.entities.Sale;
import dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.entities.SaleJpaEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = SaleProductJpaMapper.class)
public interface SaleJpaMapper {
    Sale toDomain(SaleJpaEntity entity);
    SaleJpaEntity toEntity(Sale domain);

    @AfterMapping
    default void linkSale(@MappingTarget SaleJpaEntity saleJpaEntity) {
        if (saleJpaEntity.getItems() != null) {
            saleJpaEntity.getItems().forEach(item -> item.setSale(saleJpaEntity));
        }
    }
}
