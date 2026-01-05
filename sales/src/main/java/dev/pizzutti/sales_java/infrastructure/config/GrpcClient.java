package dev.pizzutti.sales_java.infrastructure.config;

import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.ProductServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClient {

    private static final String GRPC_SERVER_HOST = "localhost";
    private static final int GRPC_SERVER_PORT = 6565;


    @Bean
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder
                .forAddress(GRPC_SERVER_HOST, GRPC_SERVER_PORT)
                .usePlaintext()
                .build();
    }

    @Bean
    public ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub(ManagedChannel channel) {
        return ProductServiceGrpc.newBlockingStub(channel);
    }

}
