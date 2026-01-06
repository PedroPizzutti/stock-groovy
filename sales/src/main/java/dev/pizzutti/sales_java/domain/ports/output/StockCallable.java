package dev.pizzutti.sales_java.domain.ports.output;

import java.util.concurrent.CompletableFuture;

public interface StockCallable {
    void reserveCircuitBreaker(String codBar, Integer quantity);
    void reserveRetry(String codBar, Integer quantity);
    CompletableFuture<Void> reserveTimeLimiter(String codBar, Integer quantity);
    void reserveBulkhead(String codBar, Integer quantity);
    CompletableFuture<Void> reserveBulkheadThreadPool(String codBar, Integer quantity);
    void reserveRateLimiter(String codBar, Integer quantity);
}
