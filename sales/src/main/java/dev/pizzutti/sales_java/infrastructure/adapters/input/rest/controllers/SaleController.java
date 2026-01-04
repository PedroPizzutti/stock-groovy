package dev.pizzutti.sales_java.infrastructure.adapters.input.rest.controllers;

import dev.pizzutti.sales_java.domain.entities.Product;
import dev.pizzutti.sales_java.domain.entities.Sale;
import dev.pizzutti.sales_java.domain.ports.input.SaleService;
import dev.pizzutti.sales_java.infrastructure.adapters.input.rest.dtos.SaleDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sale")
public class SaleController {

    private final SaleService service;

    SaleController(SaleService service) {
        this.service = service;
    }

    @GetMapping("/products")
    ResponseEntity<List<Product>> getProducts() {
        var products = service.listProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @PostMapping
    ResponseEntity<Sale> create(@RequestBody SaleDto payload) {
        var sale = service.makeSale(payload.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body(sale);
    }

}
