package dev.pizzutti.stock_groovy.infrastructure.input.rest.product

import dev.pizzutti.stock_groovy.application.product.CreateProductUseCase
import dev.pizzutti.stock_groovy.application.product.DeleteProductUseCase
import dev.pizzutti.stock_groovy.application.product.RetrieveProductUseCase
import dev.pizzutti.stock_groovy.application.product.ListProductUseCase
import dev.pizzutti.stock_groovy.application.product.UpdateProductUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/product")
class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final RetrieveProductUseCase retrieveProductUseCase;
    private final ListProductUseCase listProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    ProductController(
            CreateProductUseCase createProductUseCase,
            UpdateProductUseCase updateProductUseCase,
            RetrieveProductUseCase retrieveProductUseCase,
            ListProductUseCase listProductUseCase,
            DeleteProductUseCase deleteProductUseCase
    ) {
        this.createProductUseCase = createProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.retrieveProductUseCase = retrieveProductUseCase;
        this.listProductUseCase = listProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
    }

    @PostMapping
    ResponseEntity<ProductResponse> create(@RequestBody ProductRequestCreate payload) {
        def product = createProductUseCase.execute(payload.toDomain())
        ResponseEntity.status(HttpStatus.CREATED).body(ProductResponse.fromDomain(product))
    }

    @PutMapping("/{productId}")
    ResponseEntity<ProductResponse> update(@RequestBody ProductRequestUpdate payload,
                                           @PathVariable("productId") UUID productId) {
        def product = updateProductUseCase.execute(payload.toDomain(productId))
        ResponseEntity.status(HttpStatus.ACCEPTED).body(ProductResponse.fromDomain(product))
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<ProductResponse> delete(@PathVariable("productId") UUID productId) {
        deleteProductUseCase.execute(productId)
        ResponseEntity.noContent().build()
    }

    @GetMapping
    ResponseEntity<List<ProductResponse>> list() {
        def products = listProductUseCase.execute().collect {ProductResponse.fromDomain(it)}
        ResponseEntity.status(200).body(products)
    }

    @GetMapping("/{productId}")
    ResponseEntity<ProductResponse> get(@PathVariable("productId") UUID productId) {
        def product = retrieveProductUseCase.execute(productId)
        ResponseEntity.status(200).body(ProductResponse.fromDomain(product))
    }

}
