package com.seasugar.grpc.server;

import com.seasugar.grpc.register.springcloud.ZooKeeperRegistry;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.InetAddress;

@Component
public class ServerRegistration {

    private final ZooKeeperRegistry zooKeeperRegistry;

    public ServerRegistration() throws IOException, InterruptedException {
        this.zooKeeperRegistry = new ZooKeeperRegistry();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void registerService() {
        try {
            String serviceName = "grpc-server";
            String serviceAddress = InetAddress.getLocalHost().getHostAddress() + ":8080";
            zooKeeperRegistry.registerService(serviceName, serviceAddress);
            System.out.println("Service registered to ZooKeeper: " + serviceName + " at " + serviceAddress);
        } catch (Exception e) {
            System.err.println("Failed to register service to ZooKeeper: " + e.getMessage());
        }
    }
}