package com.studying.rpc.swift.server;

import com.facebook.swift.service.ThriftServer;
import com.studying.util.LoggerUtil;

/**
 * Created by junweizhang on 17/7/19.
 */
public class ThriftServerV2Demo {

    public static int THRIFT_PORT = 12356;

    public static void main(String[] args) {

        createServer();
//        createMockServer();
        LoggerUtil.logger.info("server start ...");

        // serverCreator.stop();
        // serverCreator.checkExecutorsTerminated();
    }

    private static void createMockServer() {
        ServerCreator serverCreator = new ServerCreator().invokeMock();

        ThriftServer server = serverCreator.getServer();

        server.start();
    }

    private static void createServer() {
        ServerCreator serverCreator = new ServerCreator().invoke();

        ThriftServer server = serverCreator.getServer();

        server.start();
    }

}
