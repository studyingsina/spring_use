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
