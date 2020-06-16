package com.studying.el;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Options;
import com.studying.util.LoggerUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by junweizhang on 2019/3/1.
 */
public class AviatorTester {

    @Test
    public void testCal() {
        Long result = (Long) AviatorEvaluator.execute("1+4+3");
        LoggerUtil.logger.info("result : {}", result);
        AviatorEvaluator.setOption(Options.TRACE_EVAL, true);
        result = (Long) AviatorEvaluator.execute("println('hello world'); 1+2+3 ; 100-1");
        LoggerUtil.logger.info("result : {}", result);
    }

    @Test
    public void testCalMap() {
        String yourName = "Michael";
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("yourName", yourName);
        AviatorEvaluator.setOption(Options.TRACE_EVAL, true);
        String result = (String) AviatorEvaluator.execute(" 'hello ' + yourName ", env);
        LoggerUtil.logger.info("result : {}", result);
    }

}
