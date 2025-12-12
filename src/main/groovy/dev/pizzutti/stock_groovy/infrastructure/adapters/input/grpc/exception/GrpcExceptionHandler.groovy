package dev.pizzutti.stock_groovy.infrastructure.adapters.input.grpc.exception

import dev.pizzutti.stock_groovy.domain.exception.DomainException
import io.grpc.ForwardingServerCallListener
import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor
import io.grpc.Status
import org.springframework.stereotype.Component

@Component
class GrpcExceptionHandler implements ServerInterceptor {

    @Override
    <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                          Metadata metadata,
                                                          ServerCallHandler<ReqT, RespT> serverCallHandler) {

        def originalListener = serverCallHandler.startCall(serverCall, metadata);

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(originalListener) {
            @Override
            void onHalfClose() {
                try {
                    super.onHalfClose()
                } catch (DomainException e) {
                    def status = Status.INTERNAL.withDescription(e.getMessage())
                    def newMetadata = new Metadata();
                    def key = Metadata.Key.of("errors", Metadata.ASCII_STRING_MARSHALLER)
                    newMetadata.put(key, e.getErrors().join(";"))
                    serverCall.close(status, newMetadata)
                } catch (Exception e) {
                    def status = Status.INTERNAL.withDescription(e.getMessage())
                    serverCall.close(status, new Metadata())
                }
            }
        }
    }
}
