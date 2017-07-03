package com.studying.log;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by junweizhang on 17/4/6.
 */
public class LoggerTester {

    @Test
    public void testGetLogger(){
        Logger logger = LoggerFactory.getLogger(LoggerTester.class);
        Logger logger1 = LoggerFactory.getLogger("com.studying.log");
        // Logger logger2 = LoggerFactory.getLogger("com.studying");
        Logger logger3 = LoggerFactory.getLogger("com");
    }

}
