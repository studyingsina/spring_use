package com.studying.rpc.thrift.server;

import com.studying.rpc.thrift.stub.bean.ResultInt;
import com.studying.rpc.thrift.stub.bean.ResultStr;
import com.studying.rpc.thrift.stub.service.TestThriftService;
import org.apache.thrift.TException;

public class TestThriftServiceImpl implements TestThriftService.Iface {

    @Override
    public ResultStr getStr(String srcStr1, String srcStr2) throws TException {
        ResultStr ret = new ResultStr();
        long startTime = System.currentTimeMillis();
        String res = srcStr1 + srcStr2;
        long stopTime = System.currentTimeMillis();

        System.out.println("[getStr]time interval: " + (stopTime - startTime));
        return ret.setValue(res);
    }

    @Override
    public ResultInt getInt(int val) throws TException {
        ResultInt ret = new ResultInt();
        long startTime = System.currentTimeMillis();
        int res = val * 10;
        long stopTime = System.currentTimeMillis();

        System.out.println("[getInt]time interval: " + (stopTime - startTime));
        return ret.setValue(res);
    }

}
