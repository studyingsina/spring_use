package com.studying.rpc.thrift.stub.service;

import com.studying.rpc.thrift.mock.TMockProtocol;
import com.studying.rpc.thrift.stub.bean.ResultStr;
import com.studying.util.ByteUtil;
import org.apache.thrift.TByteArrayOutputStream;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Created by junweizhang on 17/7/3.
 */
public class TestThriftServiceSub {

    Logger logger = LoggerFactory.getLogger(TestThriftServiceSub.class);

    @Test
    public void test() throws TException {
        // ReflectionUtils.findField(TestThriftService.getStr_result.class, "");
        // getStr_resultStandardScheme resultStandardScheme = null;
        TestThriftService.getStr_result getStr_result = new TestThriftService.getStr_result();
        ResultStr resultStr = new ResultStr();
        resultStr.setValue("test1test2");
        getStr_result.setSuccess(resultStr);
        TProtocol oprot = TMockProtocol.getProtocol(null);
        getStr_result.write((TProtocol) oprot);

    }

    @Test
    public void testWriteBuffer() {
        try {
            TFramedTransport transport = new TFramedTransport(null);
            Field writeBuffer = ReflectionUtils.findField(TFramedTransport.class, "writeBuffer_");
            logger.info("writeBuffer : {}, accessable : {}", writeBuffer, writeBuffer.isAccessible());
            ReflectionUtils.makeAccessible(writeBuffer);
            logger.info("accessable : {}", writeBuffer.isAccessible());
            TByteArrayOutputStream outputStream = (TByteArrayOutputStream) writeBuffer.get(transport);
            logger.info("outputStream : {}", outputStream);
            outputStream.reset();
            byte[] bytes = ByteUtil.getInitBytes();
            outputStream.write(bytes);
        } catch (Exception e) {
            logger.error("testWriteBuffer error", e);
        }
    }


}
