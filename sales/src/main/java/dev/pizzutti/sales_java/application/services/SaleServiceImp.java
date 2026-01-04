package dev.pizzutti.sales_java.application.services;

import dev.pizzutti.sales_java.domain.entities.Product;
import dev.pizzutti.sales_java.domain.entities.Sale;
import dev.pizzutti.sales_java.domain.ports.input.SaleService;
import dev.pizzutti.sales_java.domain.ports.output.ProductRepository;
import dev.pizzutti.sales_java.domain.ports.output.SaleRepository;
import dev.pizzutti.sales_java.domain.ports.output.StockCallable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleServiceImp implements SaleService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final StockCallable stockCallable;

    public SaleServiceImp(ProductRepository productRepository,
                          SaleRepository saleRepository,
                          StockCallable stockCallable) {
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
        this.stockCallable = stockCallable;
    }

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public Sale makeSale(Sale sale) {
        sale.getItems().forEach(item -> stockCallable.reserve(item.getProduct().getCodBar(), item.getQuantity()));
        return saleRepository.save(sale);
    }
}
