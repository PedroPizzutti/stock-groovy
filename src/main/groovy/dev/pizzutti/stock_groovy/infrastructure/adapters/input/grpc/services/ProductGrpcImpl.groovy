package dev.pizzutti.stock_groovy.infrastructure.adapters.input.grpc.services

import com.google.protobuf.Empty
import dev.pizzutti.stock_groovy.domain.entities.Product
import dev.pizzutti.stock_groovy.domain.ports.input.ProductService
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.CreateProductProtoRequest
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.DeleteProductProtoRequest
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.GetProductProtoRequest
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.ListProductsProtoResponse
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.ProductProtoResponse
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.ProductServiceGrpc
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.UpdateProductProtoRequest
import io.grpc.Status
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Service

import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class ProductGrpcImpl extends ProductServiceGrpc.ProductServiceImplBase {

    ProductService productService

    ProductGrpcImpl(ProductService productService) {
        this.productService = productService
    }

    @Override
    void create(CreateProductProtoRequest request, StreamObserver<ProductProtoResponse> responseObserver) {
        def product = new Product(request.name, request.codBar, request.storageArea, request.quantity)
        def domain = productService.create(product)
        responseObserver.onNext(toProductProtoResponse(domain))
        responseObserver.onCompleted()
    }

    @Override
    void update(UpdateProductProtoRequest request, StreamObserver<ProductProtoResponse> responseObserver) {
        def id =  UUID.fromString(request.id)
        def createdAt = toLocalDateTime(request.createdAt)
        def product = new Product(id, request.name, request.codBar, request.storageArea, request.quantity, createdAt)
        def domain = productService.update(product)
        responseObserver.onNext(toProductProtoResponse(domain))
        responseObserver.onCompleted()
    }

    @Override
    void delete(DeleteProductProtoRequest request, StreamObserver<Empty> responseObserver) {
        productService.delete(UUID.fromString(request.id))
        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    @Override
    void get(GetProductProtoRequest request, StreamObserver<ProductProtoResponse> responseObserver) {
        def product = productService.getById(UUID.fromString(request.id))
        responseObserver.onNext(toProductProtoResponse(product))
        responseObserver.onCompleted()
    }

    @Override
    void list(Empty request, StreamObserver<ListProductsProtoResponse> responseObserver) {
        def products = productService.listAll()
        def protoList = ListProductsProtoResponse.newBuilder()
                .addAllProducts {products.collect { toProductProtoResponse(it) }}
                .build()
        responseObserver.onNext(protoList)
        responseObserver.onCompleted()
    }

    private static ProductProtoResponse toProductProtoResponse(Product product) {
        return ProductProtoResponse.newBuilder()
                .setId(product.id.toString())
                .setName(product.name)
                .setCodBar(product.codBar)
                .setStorageArea(product.storageArea)
                .setQuantity(product.quantity)
                .setCreatedAt(toTimestamp(product.createdAt))
                .build()
    }

    private static com.google.protobuf.Timestamp toTimestamp(LocalDateTime dt) {
        if (dt == null) {
            return com.google.protobuf.Timestamp.getDefaultInstance()
        }
        return com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(dt.toEpochSecond(ZoneOffset.UTC))
                .setNanos(dt.getNano())
                .build()
    }

    private static LocalDateTime toLocalDateTime(com.google.protobuf.Timestamp t) {
        return LocalDateTime.ofEpochSecond(t.getSeconds(), t.getNanos(), ZoneOffset.UTC)
    }
}
