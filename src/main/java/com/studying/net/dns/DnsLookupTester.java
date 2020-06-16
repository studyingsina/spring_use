package com.studying.net.dns;

import com.alibaba.fastjson.JSON;
import com.studying.util.LoggerUtil;
import org.junit.Test;

import java.net.InetAddress;

public class DnsLookupTester {

    @Test
    public void test3W(){
        String baidu = "baidu.com";
        InetAddress[] ia = resolve(baidu);
        LoggerUtil.logger.info("baidu dns ip ia : {}", JSON.toJSONString(ia));
        String qq = "qq.com";
        ia = resolve(qq);
        LoggerUtil.logger.info("qq dns ip ia : {}", JSON.toJSONString(ia));
    }

    public static InetAddress[] resolve(final String host){
        InetAddress[] ia = null;
        try {
            ia = InetAddress.getAllByName(host);
        } catch (Exception e) {
            LoggerUtil.logger.error("resolve error", e);
        }
        return ia;
    }

}
