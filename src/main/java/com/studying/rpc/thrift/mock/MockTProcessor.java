package com.studying.rpc.thrift.mock;

import com.google.common.collect.Maps;
import com.studying.util.ByteUtil;
import org.apache.thrift.*;
import org.apache.thrift.protocol.*;
import org.apache.thrift.transport.TFramedTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by junweizhang on 17/7/2.
 */
public class MockTProcessor<I extends MockServiceIface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift
        .TProcessor {

    private Logger logger = LoggerFactory.getLogger(MockServiceImpl.class);

    private MockServiceIface mockService;
    private MockProcessFunction processFunction;

    public MockTProcessor(I iface) {
        super(iface, getProcessMap());
        this.mockService = iface;
        this.processFunction = new MockProcessFunction("MockProcessFunction");
    }

    private static <I> Map<String, ProcessFunction<I, ? extends TBase>> getProcessMap() {
        Map<String, ProcessFunction<I, ? extends TBase>> processMap = Maps.newHashMap();
        processMap.put("", null);
        return processMap;
    }

    protected MockTProcessor(I iface, Map<String, ProcessFunction<I, ? extends TBase>> processFunctionMap) {
        super(iface, processFunctionMap);
    }

    public boolean process(TProtocol in, TProtocol out) throws TException {
        TMessage msg = in.readMessageBegin();
//        ProcessFunction fn = processMap.get(msg.name);
//        if (fn == null) {
//            TProtocolUtil.skip(in, TType.STRUCT);
//            in.readMessageEnd();
//            TApplicationException x = new TApplicationException(TApplicationException.UNKNOWN_METHOD, "Invalid method name: '"+msg
// .name+"'");
//            out.writeMessageBegin(new TMessage(msg.name, TMessageType.EXCEPTION, msg.seqid));
//            x.write(out);
//            out.writeMessageEnd();
//            out.getTransport().flush();
//            return true;
//        }
//        fn.process(msg.seqid, in, out, iface);
        processFunction.process2(msg.seqid, in, out, mockService);

        return true;
    }


    class MockProcessFunction<I, T extends TBase> extends org.apache.thrift.ProcessFunction<I, MockResult> {

        public MockProcessFunction(String methodName) {
            super("MockProcessFunction");
        }

        public void process2(int seqid, TProtocol iprot, TProtocol oprot, I iface) throws TException {
            MockResult args = getEmptyArgsInstance();
            try {
                // args.read(iprot);
                iprot.readStructBegin();
            } catch (TProtocolException e) {
                iprot.readMessageEnd();
                TApplicationException x = new TApplicationException(TApplicationException.PROTOCOL_ERROR, e.getMessage());
                oprot.writeMessageBegin(new TMessage(getMethodName(), TMessageType.EXCEPTION, seqid));
                x.write(oprot);
                oprot.writeMessageEnd();
                oprot.getTransport().flush();
                return;
            }
            iprot.readMessageEnd();
            TBase result = getResult(iface, args);
//            oprot.writeMessageBegin(new TMessage(getMethodName(), TMessageType.REPLY, seqid));
            result.write(oprot);
//            oprot.writeMessageEnd();
            oprot.getTransport().flush();
            /**
             * mock byte[]
             * new byte[]{-128,1,0,2,0,0,0,6,103,101,116,83,116,114,0,0,0,1,12,0,0,11,0,2,0,0,0,10,116,101,115,116,49,116,101,115,116,50,
             * 0,0,0}
             */
        }

        @Override
        protected TBase getResult(I i, MockResult mockResult) throws TException {
            return new MockResult();
        }

        @Override
        protected MockResult getEmptyArgsInstance() {
            return null;
        }


    }

    class MockResult implements org.apache.thrift.TBase<MockResult, MockFields>, java.io.Serializable, Cloneable {

        @Override
        public void read(TProtocol tProtocol) throws TException {
            logger.info("read...");
        }

        @Override
        public void write(TProtocol oprot) throws TException {
            try {
                logger.info("write...");
//                oprot.writeStructBegin(new TStruct("MockResult"));
//                oprot.writeFieldBegin(new TField("value", org.apache.thrift.protocol.TType.STRING, (short) 2));
//                oprot.writeString("write mock test...");
//                oprot.writeFieldEnd();
//                oprot.writeFieldStop();
//                oprot.writeStructEnd();
                // ResultStr resultStr = new ResultStr();
                TFramedTransport transport = (TFramedTransport) oprot.getTransport();
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
                logger.error("write error", e);
            }
        }

        @Override
        public MockFields fieldForId(int i) {
            logger.info("fieldForId...");
            return null;
        }

        @Override
        public boolean isSet(MockFields mockFields) {
            logger.info("isSet...");
            return false;
        }

        @Override
        public Object getFieldValue(MockFields mockFields) {
            logger.info("getFieldValue...");
            return null;
        }

        @Override
        public void setFieldValue(MockFields mockFields, Object o) {
            logger.info("setFieldValue...");
        }

        @Override
        public TBase<MockResult, MockFields> deepCopy() {
            logger.info("deepCopy...");
            return null;
        }

        @Override
        public void clear() {
            logger.info("clear...");
        }

        @Override
        public int compareTo(MockResult o) {
            logger.info("compareTo...");
            return 0;
        }
    }

    enum MockFields implements org.apache.thrift.TFieldIdEnum {
        MockField01((short) 1, "MockField01");

        private final short _thriftId;
        private final String _fieldName;

        MockFields(short thriftId, String fieldName) {
            _thriftId = thriftId;
            _fieldName = fieldName;
        }

        public short getThriftFieldId() {
            return _thriftId;
        }

        public String getFieldName() {
            return _fieldName;
        }

    }

}