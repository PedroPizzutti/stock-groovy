package dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.mappers;

import dev.pizzutti.sales_java.domain.entities.Product;
import dev.pizzutti.sales_java.infrastructure.adapters.output.persistence.entities.ProductJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductJpaMapper {
    public Product toDomain(ProductJpaEntity entity);
    ProductJpaEntity toEntity(Product domain);
}
