package com.seasugar.grpc.register;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZooKeeperRegistry {
    private static final String ZK_ADDRESS = "localhost:2181";
    private static final int SESSION_TIMEOUT = 5000;
    private static final String REGISTRY_PATH = "/grpc_services";

    private ZooKeeper zooKeeper;

    public ZooKeeperRegistry() throws IOException, InterruptedException {
        connect();
    }

    private void connect() throws IOException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        zooKeeper = new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                latch.countDown();
            }
        });
        latch.await();
    }

    public void registerService(String serviceName, String serviceAddress) throws KeeperException, InterruptedException {
        String servicePath = REGISTRY_PATH + "/" + serviceName;
        if (zooKeeper.exists(servicePath, false) == null) {
            zooKeeper.create(servicePath, serviceAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            zooKeeper.setData(servicePath, serviceAddress.getBytes(), -1);
        }
    }

    public void close() throws InterruptedException {
        if (zooKeeper != null) {
            zooKeeper.close();
        }
    }
}