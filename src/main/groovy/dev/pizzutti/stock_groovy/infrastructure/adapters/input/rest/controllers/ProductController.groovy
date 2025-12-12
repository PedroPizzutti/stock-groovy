package dev.pizzutti.stock_groovy.infrastructure.adapters.input.rest.controllers


import dev.pizzutti.stock_groovy.domain.ports.input.ProductService
import dev.pizzutti.stock_groovy.infrastructure.adapters.input.rest.controllers.dtos.ProductRequestCreate
import dev.pizzutti.stock_groovy.infrastructure.adapters.input.rest.controllers.dtos.ProductRequestUpdate
import dev.pizzutti.stock_groovy.infrastructure.adapters.input.rest.controllers.dtos.ProductResponse
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

    final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    ResponseEntity<ProductResponse> create(@RequestBody ProductRequestCreate payload) {
        def product = productService.create(payload.toDomain())
        ResponseEntity.status(HttpStatus.CREATED).body(ProductResponse.fromDomain(product))
    }

    @PutMapping("/{productId}")
    ResponseEntity<ProductResponse> update(@RequestBody ProductRequestUpdate payload,
                                           @PathVariable("productId") UUID productId) {
        def product = productService.update(payload.toDomain(productId))
        ResponseEntity.status(HttpStatus.ACCEPTED).body(ProductResponse.fromDomain(product))
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<ProductResponse> delete(@PathVariable("productId") UUID productId) {
        productService.delete(productId)
        ResponseEntity.noContent().build()
    }

    @GetMapping
    ResponseEntity<List<ProductResponse>> list() {
        def products = productService.listAll()
        def productsResponse = new ArrayList<ProductResponse>()
        productsResponse.addAll(products.collect({ProductResponse.fromDomain(it)}))
        ResponseEntity.ok(productsResponse)
    }

    @GetMapping("/{productId}")
    ResponseEntity<ProductResponse> get(@PathVariable("productId") UUID productId) {
        def product = productService.getById(productId)
        ResponseEntity.status(200).body(ProductResponse.fromDomain(product))
    }
}
