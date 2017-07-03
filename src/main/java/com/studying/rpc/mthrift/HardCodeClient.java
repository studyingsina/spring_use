package com.studying.rpc.mthrift;

/**
 * Created by  on 15/3/27.
 */
public class HardCodeClient {
//    private ThriftClientProxy thriftClientProxy;
//    private TestService.Iface iface;
//
//    public HardCodeClient() {
//
//        MTThriftPoolConfig mtThriftPoolConfig = new MTThriftPoolConfig();
//        mtThriftPoolConfig.setMaxActive(5);
//        mtThriftPoolConfig.setMaxIdle(2);
//        mtThriftPoolConfig.setMinIdle(1);
//        mtThriftPoolConfig.setMaxWait(3000);
//        mtThriftPoolConfig.setTestOnBorrow(false);
//
//        thriftClientProxy = new ThriftClientProxy();
//        thriftClientProxy.setMtThriftPoolConfig(mtThriftPoolConfig);
//        thriftClientProxy.setServiceInterface(TestService.class);
//        thriftClientProxy.setTimeout(5000);
//        thriftClientProxy.setClusterManager("OCTO");
//        try {
//            thriftClientProxy.afterPropertiesSet();
//            iface = (TestService.Iface) thriftClientProxy.getObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public TestService.Iface getIface() {
//        return iface;
//    }
//
//    public static void main(String[] args) {
//        HardCodeClient hardCodeClient = new HardCodeClient();
//        while (true) {
//            TestRequest testRequest = new TestRequest();
//            testRequest.setUserid(123);
//            testRequest.setName("土豆");
//            testRequest.setMessage("你是谁");
//            testRequest.setSeqid(1);
//            TestResponse testResponse = null;
//            long start = System.currentTimeMillis();
//            try {
//                testResponse = hardCodeClient.getIface().method1(testRequest);
//                System.out.println(System.currentTimeMillis() - start + " ms");
//            } catch (TException e) {
//                e.printStackTrace();
//            }
//            System.out.println(testResponse);
//        }
//    }
}
