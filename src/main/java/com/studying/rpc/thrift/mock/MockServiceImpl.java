package com.studying.rpc.thrift.mock;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by junweizhang on 17/7/2.
 */
public class MockServiceImpl implements MockServiceIface{

    Logger logger = LoggerFactory.getLogger(MockServiceImpl.class);

    @Override
    public void mock() throws TException {
        logger.info("mock impl");
    }
}
