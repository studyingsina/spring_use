package com.studying.rpc.thrift.server;

import com.studying.rpc.thrift.mock.MockServiceImpl;
import com.studying.rpc.thrift.mock.MockTProcessor;
import com.studying.rpc.thrift.stub.service.TestThriftService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

public class ThriftServerDemo {

    private static int m_thriftPort = 12356;

    private static TestThriftServiceImpl m_myService = new TestThriftServiceImpl();

    private static TServer m_server = null;

    private static void createNonblockingServer() throws TTransportException {
        TProcessor tProcessor = new TestThriftService.Processor<TestThriftService.Iface>(m_myService);
        TNonblockingServerSocket nioSocket = new TNonblockingServerSocket(m_thriftPort);
        TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(nioSocket);
        tnbArgs.processor(tProcessor);
        tnbArgs.transportFactory(new TFramedTransport.Factory());
        tnbArgs.protocolFactory(new TBinaryProtocol.Factory());
        // 使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式
        m_server = new TNonblockingServer(tnbArgs);
    }

    public static void createMockServer() throws Exception{
        MockServiceImpl mockService = new MockServiceImpl();
        TProcessor tProcessor = new MockTProcessor(mockService);
        TNonblockingServerSocket nioSocket = new TNonblockingServerSocket(m_thriftPort);
        TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(nioSocket);
        tnbArgs.processor(tProcessor);
        tnbArgs.transportFactory(new TFramedTransport.Factory());
        tnbArgs.protocolFactory(new TBinaryProtocol.Factory());
        // 使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式
        m_server = new TNonblockingServer(tnbArgs);
    }

    public static boolean start() {
        try {
             createNonblockingServer();
//            createMockServer();
        } catch (Exception e) {
            System.out.println("start server error!" + e);
            return false;
        }
        System.out.println("service at port: " + m_thriftPort);
        m_server.serve();
        return true;
    }

    public static void main(String[] args) {
        if (!start()) {
            System.exit(0);
        }
    }

}
