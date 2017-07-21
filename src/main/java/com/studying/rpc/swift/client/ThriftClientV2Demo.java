package com.studying.rpc.swift.client;

import com.facebook.nifty.client.FramedClientConnector;
import com.facebook.nifty.core.TNiftyTransport;
import com.facebook.nifty.core.ThriftTransportType;
import com.facebook.swift.codec.ThriftCodec;
import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.codec.internal.TProtocolWriter;
import com.facebook.swift.service.ThriftClientManager;
import com.facebook.swift.service.ThriftMethodHandler;
import com.google.common.net.HostAndPort;
import com.studying.rpc.swift.TestThriftService;
import com.studying.rpc.swift.gen.ResultStr;
import com.studying.rpc.swift.gen.ThriftResult;
import com.studying.rpc.swift.server.ThriftServerV2Demo;
import com.studying.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TTransport;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by junweizhang on 17/7/19.
 */
public class ThriftClientV2Demo {

    public static byte[] mockResultBytes() throws Exception {
        ThriftCodecManager codecManager = new ThriftCodecManager();
        try (ThriftClientManager clientManager = new ThriftClientManager(codecManager)) {
            Class<?> clientType = TestThriftService.class;
            String clientName = ThriftClientManager.DEFAULT_NAME;

            // 获取 codecManager
//            Field codecManagerField = ReflectionUtils.findField(ThriftClientManager.class, "codecManager");
//            ReflectionUtils.makeAccessible(codecManagerField);
//            ThriftCodecManager codecManager = (ThriftCodecManager) codecManagerField.get(clientManager);

            // 构造 ThriftClientMetadata 实例.
            Constructor constructor = ThriftClientManager.ThriftClientMetadata.class.getDeclaredConstructor(Class.class, String.class,
                    ThriftCodecManager.class);
            LoggerUtil.logger.info("constructor : {} isAccessible : {}", constructor, constructor.isAccessible());
            ReflectionUtils.makeAccessible(constructor);
            LoggerUtil.logger.info("constructor : {} isAccessible : {}", constructor, constructor.isAccessible());
            final ThriftClientManager.ThriftClientMetadata clientMetadata = (ThriftClientManager.ThriftClientMetadata) constructor
                    .newInstance(clientType, clientName, codecManager);

            // 2. 获取到 ThriftMethodHandler 中的 codec
            Method getStrMethod = null; // ReflectionUtils.findMethod(TestThriftService.class, "getStr");

            Method[] methods = ReflectionUtils.getAllDeclaredMethods(TestThriftService.class);
            for (Method method : methods) {
                if (StringUtils.equalsIgnoreCase(method.getName(), "getStr")) {
                    getStrMethod = method;
                    break;
                }
            }


            ThriftMethodHandler methodHandler = clientMetadata.getMethodHandlers().get(getStrMethod);
            Field successCodec = ReflectionUtils.findField(ThriftMethodHandler.class, "successCodec");
            LoggerUtil.logger.info("successCodec : {}, accessable : {}", successCodec, successCodec.isAccessible());
            ReflectionUtils.makeAccessible(successCodec);
            LoggerUtil.logger.info("successCodec :{}, accessable : {}", successCodec, successCodec.isAccessible());
            ThriftCodec codec = (ThriftCodec) successCodec.get(methodHandler);

            // Mock Data
            ResultStr mockResult = new ResultStr();
            mockResult.setResult(ThriftResult.SUCCESS);
            mockResult.setValue("洛阳亲友如相问, 就说我在写代码");
//            mockResult.setValue("白居易-刘禹锡");

            // TChannelBufferInputTransport inputTransport = new TChannelBufferInputTransport();
            // TChannelBufferOutputTransport outputTransport = new TChannelBufferOutputTransport();
            // TTransportPair transportPair = TTransportPair.fromSeparateTransports(inputTransport, outputTransport);

            Channel channel = null;
            ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
            ThriftTransportType thriftTransportType = null;
            TTransport outputTransport = new TNiftyTransport(channel, channelBuffer, thriftTransportType);
            TBinaryProtocol.Factory factory = new TBinaryProtocol.Factory();
            TBinaryProtocol protocol = (TBinaryProtocol) factory.getProtocol(outputTransport);

            TProtocolWriter writer = new TProtocolWriter(protocol);
            writer.writeStructBegin(getStrMethod.getName() + "_result");
            writer.writeField("success", (short) 0, codec, mockResult);
            writer.writeStructEnd();

            ChannelBuffer realBuffer = ((TNiftyTransport) protocol.getTransport()).getOutputBuffer();
            int count = realBuffer.writerIndex();
            byte[] fakeBytes = realBuffer.array();
            byte[] bytes = new byte[count];
            System.arraycopy(fakeBytes, 0, bytes, 0, count);
            return bytes;
        }

    }

    public static void main(String[] args) throws Exception {
        try (ThriftClientManager clientManager = new ThriftClientManager()) {

            TestThriftService thriftService = clientManager.createClient(
                    new FramedClientConnector(HostAndPort.fromParts("localhost", ThriftServerV2Demo.THRIFT_PORT)),
                    TestThriftService.class).get();

            ResultStr resultStr = thriftService.getStr("白居易", "刘禹锡");

            LoggerUtil.logger.info("thriftService str : {}", resultStr);

        }
    }

}
