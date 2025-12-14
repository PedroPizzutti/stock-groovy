package dev.pizzutti.stock_groovy.infrastructure.config

import dev.pizzutti.stock_groovy.infrastructure.adapters.input.grpc.exception.GrpcExceptionHandler
import dev.pizzutti.stock_groovy.infrastructure.adapters.input.grpc.services.ProductGrpcImpl
import groovy.util.logging.Slf4j
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import org.springframework.context.annotation.Configuration

import javax.annotation.PostConstruct

@Configuration
@Slf4j
class GrpcServer {

    final static PORT = 6565
    Server server
    GrpcExceptionHandler exceptionHandler
    ProductGrpcImpl productGrpcImpl

    GrpcServer(GrpcExceptionHandler exceptionHandler,
               ProductGrpcImpl productGrpcImpl) {
        this.exceptionHandler = exceptionHandler
        this.productGrpcImpl = productGrpcImpl
    }

    @PostConstruct
    void start() {
        server = ServerBuilder
                .forPort(PORT)
                .intercept(exceptionHandler)
                .addService(productGrpcImpl)
                .addService(ProtoReflectionService.newInstance())
                .build()

        server.start()
        log.info("gRPC server started on port (${PORT})")

        final Server serverRef = server

        Runtime.runtime.addShutdownHook(new Thread({
            serverRef?.shutdown()
            log.info("gRPC Server down")
        }, "grpc-shutdown-hook"))
    }

}
