package com.seasugar.grpcclient.controller;

import com.seasugar.api.ProductProto;
import com.seasugar.api.ProductServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("good")
public class GoodController {
    @GrpcClient("grpc-server")
    private ProductServiceGrpc.ProductServiceBlockingStub productService;

    @GetMapping("/getById/{id}")
    public void good(@PathVariable("id") Long id) {
        System.out.println(id);
        ProductProto.ProductResponse product = productService.getProduct(ProductProto.ProductRequest.newBuilder().setProductId(id).build());
        System.out.println(product);
    }
}
