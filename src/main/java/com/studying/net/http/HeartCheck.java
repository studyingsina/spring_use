package com.studying.net.http;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class HeartCheck {

    private static Logger logger = LoggerFactory.getLogger(HeartCheck.class);

    @Test
    public void httpDetect() {
        String checkUrl = "";
        int res = -1;
        HttpURLConnection connection = null;
        try {
//            Socket sc = null;
//            sc.setSoLinger();
            URL url = new URL(checkUrl);
            connection = (HttpURLConnection) url.openConnection();
            int timeout = 200;
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.connect();
            res = connection.getResponseCode();
            if (res >= 200 && res < 400) {
                logger.info("{} success, res={}", checkUrl, res);
                // ALIVE;
            } else {
                logger.error("{} failed, res={}", checkUrl, res);
                // DEAD;
            }
        } catch (Exception e) {
            logger.error("{} failed, {}", checkUrl, e.getMessage());
            // DEAD;
        } finally {
            logger.info("{}, res : {}", checkUrl, res);
            try {
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                // ignore.
            }
        }

    }

}