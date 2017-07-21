package com.studying.rpc.swift;

import com.alibaba.fastjson.JSON;
import com.facebook.swift.codec.ThriftField;
import com.studying.rpc.swift.gen.ResultInt;
import com.studying.rpc.swift.gen.ResultStr;
import com.studying.rpc.swift.gen.ThriftResult;
import com.studying.util.LoggerUtil;
import org.apache.thrift.TException;

/**
 * Created by junweizhang on 17/7/19.
 */
public class TestThriftServiceV2Impl implements TestThriftService {

    @Override
    public ResultStr getStr(@ThriftField(value = 1, name = "srcStr1", requiredness = ThriftField.Requiredness.NONE) String srcStr1,
                            @ThriftField(value = 2, name = "srcStr2", requiredness = ThriftField.Requiredness.NONE) String srcStr2)
            throws TException {
        LoggerUtil.logger.info("getStr start, srcStr1 : {}, srcStr2 : {}", srcStr1, srcStr2);
        ResultStr resultStr = new ResultStr();
        String val = new StringBuilder(50).append(srcStr1).append("-").append(srcStr2).toString();
        resultStr.setResult(ThriftResult.SUCCESS);
        resultStr.setValue(val);
        LoggerUtil.logger.info("getStr end, resultStr : {}", JSON.toJSONString(resultStr));
        return resultStr;
    }

    @Override
    public ResultInt getInt(@ThriftField(value = 1, name = "val", requiredness = ThriftField.Requiredness.NONE) int val) throws TException {
        LoggerUtil.logger.info("getInt start, val : {}", val);
        ResultInt resultInt = new ResultInt();
        resultInt.setResult(ThriftResult.SUCCESS);
        resultInt.setValue(val + 10);
        LoggerUtil.logger.info("getInt end, resultInt : {}", JSON.toJSONString(resultInt));
        return resultInt;
    }
}
