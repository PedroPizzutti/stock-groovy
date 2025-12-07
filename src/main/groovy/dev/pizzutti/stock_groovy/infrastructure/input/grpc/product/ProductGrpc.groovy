package dev.pizzutti.stock_groovy.infrastructure.input.grpc.product

import com.google.protobuf.Empty
import dev.pizzutti.stock_groovy.application.product.CreateProductUseCase
import dev.pizzutti.stock_groovy.application.product.DeleteProductUseCase
import dev.pizzutti.stock_groovy.application.product.ListProductUseCase
import dev.pizzutti.stock_groovy.application.product.RetrieveProductUseCase
import dev.pizzutti.stock_groovy.application.product.UpdateProductUseCase
import dev.pizzutti.stock_groovy.domain.product.Product
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.CreateProductProtoRequest
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.DeleteProductProtoRequest
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.GetProductProtoRequest
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.ListProductsProtoResponse
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.ProductProtoResponse
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.ProductServiceGrpc
import dev.pizzutti.stock_groovy.infrastructure.input.grpc.proto.UpdateProductProtoRequest
import io.grpc.stub.StreamObserver

//import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Service

import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class ProductGrpc extends ProductServiceGrpc.ProductServiceImplBase {

    CreateProductUseCase createProductUseCase
    UpdateProductUseCase updateProductUseCase
    RetrieveProductUseCase retrieveProductUseCase
    ListProductUseCase listProductUseCase
    DeleteProductUseCase deleteProductUseCase

    ProductGrpc(
            CreateProductUseCase createProductUseCase,
            UpdateProductUseCase updateProductUseCase,
            RetrieveProductUseCase retrieveProductUseCase,
            ListProductUseCase listProductUseCase,
            DeleteProductUseCase deleteProductUseCase
    ) {
        this.createProductUseCase = createProductUseCase
        this.updateProductUseCase = updateProductUseCase
        this.retrieveProductUseCase = retrieveProductUseCase
        this.listProductUseCase = listProductUseCase
        this.deleteProductUseCase = deleteProductUseCase
    }

    @Override
    void create(CreateProductProtoRequest request, StreamObserver<ProductProtoResponse> responseObserver) {
        def product = new Product(request.name, request.codBar, request.storageArea, request.quantity,)
        def domain = createProductUseCase.execute(product)
        responseObserver.onNext(toProductProtoResponse(domain))
        responseObserver.onCompleted()
    }

    @Override
    void update(UpdateProductProtoRequest request, StreamObserver<ProductProtoResponse> responseObserver) {
        def id =  UUID.fromString(request.id)
        def createdAt = toLocalDateTime(request.createdAt)
        def product = new Product(id, request.name, request.codBar, request.storageArea, request.quantity, createdAt)
        def domain = updateProductUseCase.execute(product)
        responseObserver.onNext(toProductProtoResponse(domain))
        responseObserver.onCompleted()
    }

    @Override
    void delete(DeleteProductProtoRequest request, StreamObserver<Empty> responseObserver) {
        deleteProductUseCase.execute(UUID.fromString(request.id))
        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    @Override
    void get(GetProductProtoRequest request, StreamObserver<ProductProtoResponse> responseObserver) {
        def product = retrieveProductUseCase.execute(UUID.fromString(request.id))
        responseObserver.onNext(toProductProtoResponse(product))
        responseObserver.onCompleted()
    }

    @Override
    void list(Empty request, StreamObserver<ListProductsProtoResponse> responseObserver) {
        def products = listProductUseCase.execute()
        def protoList = ListProductsProtoResponse.newBuilder()
        products.collect { toProductProtoResponse(it) }

        responseObserver.onNext(protoList.build())
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
