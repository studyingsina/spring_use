package com.studying.rpc.swift.server;

import com.facebook.nifty.core.NettyServerConfig;
import com.facebook.nifty.core.ThriftServerDef;
import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.service.ThriftEventHandler;
import com.facebook.swift.service.ThriftServer;
import com.facebook.swift.service.ThriftServiceProcessor;
import com.google.common.collect.ImmutableList;
import com.studying.rpc.swift.TestThriftServiceV2Impl;
import com.studying.rpc.swift.mock.MockServiceImpl;
import com.studying.rpc.swift.mock.MockThriftServiceProcessor;
import org.junit.Assert;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 */
public class ServerCreator {
    private ExecutorService taskWorkerExecutor;
    private ThriftServer server;
    private ExecutorService bossExecutor;
    private ExecutorService ioWorkerExecutor;

    public ThriftServer getServer() {
        return server;
    }

    public ServerCreator invoke() {
        // boolean idDebug = true;
        // ThriftCodecFactory factory = new MockCompilerThriftCodecFactory(idDebug, ThriftCodecManager.class.getClassLoader());
        ThriftServiceProcessor processor = new ThriftServiceProcessor(
                new ThriftCodecManager(),
                ImmutableList.<ThriftEventHandler>of(),
                new TestThriftServiceV2Impl()
        );

        taskWorkerExecutor = newFixedThreadPool(1);

        ThriftServerDef serverDef = ThriftServerDef.newBuilder()
                .listen(ThriftServerV2Demo.THRIFT_PORT)
                .withProcessor(processor)
                .using(taskWorkerExecutor)
                .build();

        bossExecutor = newCachedThreadPool();
        ioWorkerExecutor = newCachedThreadPool();

        NettyServerConfig serverConfig = NettyServerConfig.newBuilder()
                .setBossThreadExecutor(bossExecutor)
                .setWorkerThreadExecutor(ioWorkerExecutor)
                .build();

        server = new ThriftServer(serverConfig, serverDef);
        return this;
    }

    public ServerCreator invokeMock() {
        MockThriftServiceProcessor processor = new MockThriftServiceProcessor(
                new ThriftCodecManager(),
                ImmutableList.<ThriftEventHandler>of(),
                new MockServiceImpl()
        );

        taskWorkerExecutor = newFixedThreadPool(1);

        ThriftServerDef serverDef = ThriftServerDef.newBuilder()
                .listen(ThriftServerV2Demo.THRIFT_PORT)
                .withProcessor(processor)
                .using(taskWorkerExecutor)
                .build();

        bossExecutor = newCachedThreadPool();
        ioWorkerExecutor = newCachedThreadPool();

        NettyServerConfig serverConfig = NettyServerConfig.newBuilder()
                .setBossThreadExecutor(bossExecutor)
                .setWorkerThreadExecutor(ioWorkerExecutor)
                .build();

        server = new ThriftServer(serverConfig, serverDef);
        return this;
    }

    public void checkExecutorsTerminated() {
        Assert.assertTrue(bossExecutor.isTerminated());
        Assert.assertTrue(ioWorkerExecutor.isTerminated());
        Assert.assertTrue(taskWorkerExecutor.isTerminated());
    }

    public void stop() {
        server.close();
    }
}
