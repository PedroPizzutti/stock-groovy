package dev.pizzutti.sales_java.infrastructure.adapters.output.grpc;

import dev.pizzutti.sales_java.domain.ports.output.StockCallable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StockCallableGrpcImp implements StockCallable {
    @Override
    public void reserve(String codBar, Integer quantity) {
        log.info("reserve: {}{}", codBar, quantity.toString());
    }
}
