package com.studying.rpc.thrift;

import com.studying.rpc.thrift.server.ThriftServerDemo;
import com.studying.rpc.thrift.stub.bean.ResultStr;
import com.studying.rpc.thrift.stub.service.TestThriftService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;

public class ThriftInvokeTester extends ThriftServerDemo {

    private static final String THRIFT_HOST = "127.0.0.1";
    private static final int THRIFT_PORT = 12356;
    private static final int SERVER_MODE_THREAD_POOL = 1;
    private static final int SERVER_MODE_NONBLOCK = 2;
    private static final int SERVER_MODE_THREADEDSELECTOR = 3;
    private static int serverMode = SERVER_MODE_NONBLOCK;

    TTransport m_transport = null;

    public TestThriftService.Client getServiceClient() {
        TProtocol protocol = null;
        if (serverMode == SERVER_MODE_THREAD_POOL) {
            m_transport = new TSocket(THRIFT_HOST, THRIFT_PORT, 2000);
            protocol = new TBinaryProtocol(m_transport);
        } else if (serverMode == SERVER_MODE_NONBLOCK || serverMode == SERVER_MODE_THREADEDSELECTOR) {
            m_transport = new TFramedTransport(new TSocket(THRIFT_HOST,
                    THRIFT_PORT, 2000));
            // 协议要和服务端一致
            protocol = new TBinaryProtocol(m_transport);
        }
        return new TestThriftService.Client(protocol);
    }

    @Test
    public void test() {
        // m_transport = new TSocket(THRIFT_HOST, THRIFT_PORT, 2000);
        m_transport = new TFramedTransport(new TSocket(THRIFT_HOST, THRIFT_PORT, 2000 * 1000));
        TProtocol protocol = new TBinaryProtocol(m_transport);
        TestThriftService.Client testClient = new TestThriftService.Client(protocol);

        try {
            m_transport.open();

            ResultStr res = testClient.getStr("test1", "test2");
            System.out.println("res = " + res);
            m_transport.close();
        } catch (TException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        m_transport = new TFramedTransport(new TSocket(THRIFT_HOST, THRIFT_PORT, 2000));
        // 协议要和服务端一致
        TProtocol protocol = new TBinaryProtocol(m_transport);
        TestThriftService.Client testClient = new TestThriftService.Client(protocol);

        try {
            m_transport.open();
            String param1 = "my String 1";
            String param2 = "my String 2";

            ResultStr res = testClient.getStr(param1, param2);

            System.out.println("res = " + res);

        } catch (TException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            m_transport.close();
        }
    }


}
