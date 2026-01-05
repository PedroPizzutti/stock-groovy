package dev.pizzutti.sales_java.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.retry.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class Resilience4JLogger {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public Resilience4JLogger(CircuitBreakerRegistry circuitBreakerRegistry) {
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @PostConstruct
    public void registerEvents() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("grpc");

        circuitBreaker.getEventPublisher()
                .onStateTransition(event -> log.info("CircuitBreaker {} mudou: {} -> {}",
                        circuitBreaker.getName(),
                        event.getStateTransition().getFromState(),
                        event.getStateTransition().getToState()));
    }
}
