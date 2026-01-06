package dev.pizzutti.sales_java.infrastructure.adapters.output.grpc;

import dev.pizzutti.sales_java.domain.ports.output.StockCallable;
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.ProductServiceGrpc;
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.ReserveProductProtoRequest;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class StockCallableGrpcImp implements StockCallable {

    private final ProductServiceGrpc.ProductServiceBlockingStub productGrpc;

    StockCallableGrpcImp(ProductServiceGrpc.ProductServiceBlockingStub productGrpc) {
        this.productGrpc = productGrpc;
    }

    @Override
    @CircuitBreaker(name = "grpc", fallbackMethod = "fallbackCb")
    public void reserveCircuitBreaker(String codBar, Integer quantity) {
        reserve(codBar, quantity);
    }

    @Override
    @Retry(name = "grpc", fallbackMethod = "fallbackRetry")
    public void reserveRetry(String codBar, Integer quantity) {
        reserve(codBar, quantity);
    }

    @Override
    @TimeLimiter(name = "grpc", fallbackMethod = "fallbackTl")
    public CompletableFuture<Void> reserveTimeLimiter(String codBar, Integer quantity) {
        return CompletableFuture.runAsync(() -> {
            reserve(codBar, quantity);
        });
    }

    @Override
    @Bulkhead(name = "grpc", fallbackMethod = "fallbackBh")
    public void reserveBulkhead(String codBar, Integer quantity) {
        reserve(codBar, quantity);
    }

    @Override
    @Bulkhead(name = "grpc", type = Bulkhead.Type.THREADPOOL, fallbackMethod = "fallbackBhPool")
    public CompletableFuture<Void> reserveBulkheadThreadPool(String codBar, Integer quantity) {
        return CompletableFuture.runAsync(() -> {
            reserve(codBar, quantity);
        });
    }

    @Override
    @RateLimiter(name = "grpc", fallbackMethod = "fallbackRt")
    public void reserveRateLimiter(String codBar, Integer quantity) {
        reserve(codBar, quantity);
    }

    private void reserve(String codBar, Integer quantity) {
        var request = ReserveProductProtoRequest.newBuilder()
                .setCodBar(codBar)
                .setQuantity(quantity)
                .build();
        productGrpc.reserve(request);
    }

    public void fallbackCb(String codBar, Integer quantity, Throwable t) {
        log.warn("CircuitBreak Fallback disparado para codBar={}", codBar);
        throw new RuntimeException(t);
    }

    public void fallbackRetry(String codBar, Integer quantity, Throwable t) {
        log.warn("Retry Fallback disparado para codBar={}", codBar);
        throw new RuntimeException(t);
    }

    public void fallbackBh(String codBar, Integer quantity, Throwable t) {
        log.warn("Bulkhead Fallback disparado para codBar={}", codBar);
        throw new RuntimeException(t);
    }

    public void fallbackRt(String codBar, Integer quantity, Throwable t) {
        log.warn("RateLimiter Fallback disparado para codBar={}", codBar);
        throw new RuntimeException(t);
    }

    public CompletableFuture<Void> fallbackBhPool(String codBar, Integer quantity, Throwable t) {
        log.warn("Bulkhead ThreadPool Fallback disparado para codBar={}", codBar);
        throw new RuntimeException(t);
    }

    public CompletableFuture<Void> fallbackTl(String codBar, Integer quantity, Throwable t) {
        log.warn("TimeLimiter Fallback disparado para codBar={}", codBar);
        throw new RuntimeException(t);
    }
}
