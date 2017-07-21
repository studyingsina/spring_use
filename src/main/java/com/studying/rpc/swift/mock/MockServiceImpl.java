package com.studying.rpc.swift.mock;

import com.studying.rpc.swift.MockService;
import com.studying.util.LoggerUtil;
import org.apache.thrift.TException;

/**
 * Created by junweizhang on 17/7/21.
 */
public class MockServiceImpl implements MockService {

    @Override
    public void mockService() throws TException {
        LoggerUtil.logger.info("春风十里不如曾鋆吹牛");
    }

}
