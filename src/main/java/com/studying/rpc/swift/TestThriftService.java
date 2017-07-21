package com.studying.rpc.swift;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftField.Requiredness;
import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;
import com.google.common.util.concurrent.ListenableFuture;
import com.studying.rpc.swift.gen.ResultInt;
import com.studying.rpc.swift.gen.ResultStr;

@ThriftService("TestThriftService")
public interface TestThriftService {

    @ThriftService("TestThriftService")
    public interface Async {
        @ThriftMethod(value = "getStr")
        ListenableFuture<ResultStr> getStr(
                @ThriftField(value = 1, name = "srcStr1", requiredness = Requiredness.NONE) final String srcStr1,
                @ThriftField(value = 2, name = "srcStr2", requiredness = Requiredness.NONE) final String srcStr2
        );

        @ThriftMethod(value = "getInt")
        ListenableFuture<ResultInt> getInt(
                @ThriftField(value = 1, name = "val", requiredness = Requiredness.NONE) final int val
        );
    }

    @ThriftMethod(value = "getStr")
    ResultStr getStr(
            @ThriftField(value = 1, name = "srcStr1", requiredness = Requiredness.NONE) final String srcStr1,
            @ThriftField(value = 2, name = "srcStr2", requiredness = Requiredness.NONE) final String srcStr2
    ) throws org.apache.thrift.TException;

    @ThriftMethod(value = "getInt")
    ResultInt getInt(
            @ThriftField(value = 1, name = "val", requiredness = Requiredness.NONE) final int val
    ) throws org.apache.thrift.TException;
}