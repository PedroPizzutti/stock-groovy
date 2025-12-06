package dev.pizzutti.stock_groovy.infrastructure.config

import dev.pizzutti.stock_groovy.application.product.CreateProductUseCase
import dev.pizzutti.stock_groovy.application.product.DeleteProductUseCase
import dev.pizzutti.stock_groovy.application.product.RetrieveProductUseCase
import dev.pizzutti.stock_groovy.application.product.ListProductUseCase
import dev.pizzutti.stock_groovy.application.product.UpdateProductUseCase
import dev.pizzutti.stock_groovy.domain.product.ProductRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProductUseCaseConfig {

    @Bean
    CreateProductUseCase createProductUseCase(ProductRepository repository) {
        new CreateProductUseCase(repository)
    }

    @Bean
    UpdateProductUseCase updateProductUseCase(ProductRepository repository) {
        new UpdateProductUseCase(repository)
    }

    @Bean
    RetrieveProductUseCase retrieveProductUseCase(ProductRepository repository) {
        new RetrieveProductUseCase(repository)
    }

    @Bean
    ListProductUseCase listProductUseCase(ProductRepository repository) {
        new ListProductUseCase(repository)
    }

    @Bean
    DeleteProductUseCase deleteProductUseCase(ProductRepository repository) {
        new DeleteProductUseCase(repository)
    }
}
