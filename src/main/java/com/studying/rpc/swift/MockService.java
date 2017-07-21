package com.studying.rpc.swift;

import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;
import com.google.common.util.concurrent.ListenableFuture;

@ThriftService("MockService")
public interface MockService {

    @ThriftService("MockService")
    public interface Async {
        @ThriftMethod(value = "mockService")
        ListenableFuture<Void> mockService();
    }

    @ThriftMethod(value = "mockService")
    void mockService() throws org.apache.thrift.TException;
}