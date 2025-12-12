package dev.pizzutti.stock_groovy.infrastructure.config

import dev.pizzutti.stock_groovy.infrastructure.adapters.input.grpc.exception.GrpcExceptionHandler
import dev.pizzutti.stock_groovy.infrastructure.adapters.input.grpc.services.ProductGrpcImpl
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import org.springframework.context.annotation.Configuration

import javax.annotation.PostConstruct
import java.time.LocalDateTime

@Configuration
class GrpcServer {

    private Server server
    private GrpcExceptionHandler exceptionHandler
    private ProductGrpcImpl productGrpcImpl

    GrpcServer(GrpcExceptionHandler exceptionHandler,
               ProductGrpcImpl productGrpcImpl) {
        this.exceptionHandler = exceptionHandler
        this.productGrpcImpl = productGrpcImpl
    }

    @PostConstruct
    void start() {
        server = ServerBuilder
                .forPort(6565)
                .intercept(exceptionHandler)
                .addService(productGrpcImpl)
                .addService(ProtoReflectionService.newInstance())
                .build()
        server.start()
        println LocalDateTime.now().toString() + "  INFO --- gRPC Server on port 6565"

        Runtime.runtime.addShutdownHook(new Thread({
            server?.shutdown()
            println LocalDateTime.now().toString() + "  INFO --- gRPC Server down"
        }))
    }

}
