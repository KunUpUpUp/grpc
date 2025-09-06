package com.seasugar.grpcserver.service;

import com.seasugar.api.ProductServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;
import com.seasugar.api.ProductProto.ProductRequest;
import com.seasugar.api.ProductProto.ProductResponse;
import io.grpc.stub.StreamObserver;

@GrpcService
public class ProductServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {
    @Override
    public void getProduct(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        // Business logic here
        System.out.println("函数被调用，传入的参数为" + request.getProductId());
        ProductResponse response = ProductResponse.newBuilder()
                .setName("Example Product")
                .setPrice(99.99f)
                .setDescription("This is an example product.")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}