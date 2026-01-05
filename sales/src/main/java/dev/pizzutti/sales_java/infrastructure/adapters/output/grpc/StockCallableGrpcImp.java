package dev.pizzutti.sales_java.infrastructure.adapters.output.grpc;

import dev.pizzutti.sales_java.domain.ports.output.StockCallable;
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.ProductServiceGrpc;
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.ReserveProductProtoRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.grpc.Metadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StockCallableGrpcImp implements StockCallable {

    private final ProductServiceGrpc.ProductServiceBlockingStub productGrpc;

    StockCallableGrpcImp(ProductServiceGrpc.ProductServiceBlockingStub productGrpc) {
        this.productGrpc = productGrpc;
    }

    @Override
    @Retry(name = "grpc", fallbackMethod = "fallbackRetry")
    @RateLimiter(name = "grpc", fallbackMethod = "fallbackRt")
    @CircuitBreaker(name = "grpc", fallbackMethod = "fallbackCb")
    public void reserve(String codBar, Integer quantity) {
        var request = ReserveProductProtoRequest.newBuilder()
                .setCodBar(codBar)
                .setQuantity(quantity)
                .build();

        productGrpc.reserve(request);
    }

    public void fallbackRetry(String codBar, Integer quantity, Throwable t) {
        log.warn("Retry disparado para codBar={}", codBar);
        throw new RuntimeException(t);
    }

    public void fallbackCb(String codBar, Integer quantity, Throwable t) {
        log.warn("Callback disparado para codBar={}", codBar);
        Metadata metadata = null;
        if (t instanceof io.grpc.StatusRuntimeException e) {
            metadata = e.getTrailers();
            if (metadata != null) {
                Metadata.Key<String> errorsKey = Metadata.Key.of("errors", Metadata.ASCII_STRING_MARSHALLER);
                String errors = metadata.get(errorsKey);
                throw new RuntimeException("Erro ao criar venda: " + errors);
            }
        } else {
            throw new RuntimeException(t);
        }
    }

    public void fallbackRt(String codBar, Integer quantity, Throwable t) {
        log.warn("RateLimiter disparado para codBar={}", codBar);
        throw new RuntimeException(t);
    }

}
