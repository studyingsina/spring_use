package com.studying.util;

import com.studying.rpc.thrift.stub.bean.ResultStr;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by junweizhang on 17/7/2.
 */
public class ByteUtil {

    private static Logger logger = LoggerFactory.getLogger(ByteUtil.class);

    public static byte[] getInitBytes() {
        byte[] ret = new byte[]{-128, 1, 0, 2, 0, 0, 0, 6, 103, 101, 116, 83, 116, 114, 0, 0, 0, 1, 12, 0, 0, 11, 0, 2, 0, 0, 0, 10, 116,
                101, 115, 116, 49, 116, 101, 115, 116, 50, 0, 0};
        return ret;
    }

    public static String bToS() throws Exception {
        byte[] ret = getInitBytes();
        String str = new String(ret, "ISO-8859-1");
        logger.info("byteToString:[{}]", str);
        return str;
    }

    public static byte[] sToB(String param) throws Exception {
        String str = null;
        if(param == null){
            str = bToS();
        }
        byte[] ret = str.getBytes("ISO-8859-1");
        StringBuilder output = new StringBuilder(50);
        for (byte b : ret) {
            output.append(b).append(", ");
        }
        logger.info("stringToByte:[{}]", output.toString());
        return ret;
    }

    @Test
    public void byteToString() throws Exception {
        bToS();
    }

    @Test
    public void stringToByte() throws Exception {
        ResultStr resultStr = new ResultStr();
        resultStr.setValue("mock data test...");
        sToB(null);
    }

}
