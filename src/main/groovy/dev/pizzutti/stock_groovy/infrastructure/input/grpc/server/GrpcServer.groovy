package dev.pizzutti.stock_groovy.infrastructure.input.grpc.server

import dev.pizzutti.stock_groovy.infrastructure.input.grpc.product.ProductGrpc
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import java.time.LocalDateTime

@Component
class GrpcServer {

    private Server server
    private ProductGrpc productGrpc

    GrpcServer(ProductGrpc productGrpc) {
        this.productGrpc = productGrpc
    }

    @PostConstruct
    void start() {
        server = ServerBuilder
                .forPort(6565)
                .addService(productGrpc)
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
