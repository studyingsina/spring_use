package com.studying.rpc.thrift.client;

import com.studying.rpc.thrift.stub.bean.ResultStr;
import com.studying.rpc.thrift.stub.service.TestThriftService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftClientDemo {
	private static final String THRIFT_HOST = "127.0.0.1";
	private static final int THRIFT_PORT = 5800;
	private static final int SERVER_MODE_THREAD_POOL = 1;
	private static final int SERVER_MODE_NONBLOCK = 2;
	private static final int SERVER_MODE_THREADEDSELECTOR = 3;
	private static int serverMode = SERVER_MODE_THREADEDSELECTOR;

	 static TTransport m_transport = null;
	 
	 public static TestThriftService.Client getServiceClient()
	 {
		TProtocol protocol = null;
		if(serverMode == SERVER_MODE_THREAD_POOL)
		{
			m_transport = new TSocket(THRIFT_HOST, THRIFT_PORT,2000);
			protocol = new TBinaryProtocol(m_transport);
		}
		else if(serverMode == SERVER_MODE_NONBLOCK || serverMode == SERVER_MODE_THREADEDSELECTOR)
		{
			m_transport = new TFramedTransport(new TSocket(THRIFT_HOST,
					THRIFT_PORT, 2000));
			// 协议要和服务端一致
			protocol = new TBinaryProtocol(m_transport);
		}
		return new TestThriftService.Client(protocol);
	 }
	 
	 
	 public static void main()
	 {
		 TestThriftService.Client testClient1 = getServiceClient();
		 TestThriftService.Client testClient = getServiceClient();
		 if(testClient1.getClass() == testClient.getClass())
			 System.out.println("testClient1.getClass() == testClient.getClass()");
		 else
			 System.out.println("testClient1.getClass() != testClient.getClass()");
		 try {
			 m_transport.open();

			 ResultStr res = testClient.getStr("test1", "test2");
			 
			 m_transport.close();;
		} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
}
