package com.studying.net.http;

import com.studying.util.HttpUtils;
import com.studying.util.LoggerUtil;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;


/**
 * Created by junweizhang on 2019/5/13.
 */
public class HttpTester {

    public static final String TEST_HOST = "127.0.0.1";
    public static final int TEST_PORT = 8080;
    public static final String TEST_PATH = "/common/monitor/alive";
    public static final String TEST_URL = String.format("http://%s:%s%s", TEST_HOST, TEST_PORT, TEST_PATH);


    @Test
    public void testCycle() {
        for (int i = 0; i < 3; i++) {
//            postTest();
            testHttpConn();
            LoggerUtil.logger.info("testCycle num : {}", i);
        }
    }

    @Test
    public void postTest() {
        HttpPost post = new HttpPost(TEST_URL);
        post.addHeader("Content-Type", "application/json; charset=utf-8");
        String res = HttpUtils.post(post);
        LoggerUtil.logger.info("postTest res : {}", res);
    }

    @Test
    public void postGet() {
        HttpGet get = new HttpGet(TEST_URL);
        get.addHeader("Content-Type", "application/json; charset=utf-8");
        String res = HttpUtils.get(get);
        LoggerUtil.logger.info("postGet res : {}", res);
    }

    @Test
    public void testHttpConn() {
        try {
            InputStream is;
            OutputStream os;
            BufferedReader reader;

            String body = "test.body";
            URL url = new URL(TEST_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            // header
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Content-Length", "" + body.getBytes("utf-8").length);
            conn.setRequestProperty("Content-Language", "en-US");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");

            // auth
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(body.getBytes()));
            conn.setRequestProperty("Authorization", basicAuth);

            // time out
            conn.setReadTimeout(150000 * 1000);
            conn.setConnectTimeout(1000 * 1000);

            os = conn.getOutputStream();
            LoggerUtil.logger.info("conn : {} os : {}", conn, os);
            os.write(body.getBytes("utf-8"));
            os.flush();
            os.close();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // LoggerUtil.logger.info("res : {}", conn.getResponseMessage());
                // Map<String,List<String>> headers = conn.getHeaderFields();
                // LoggerUtil.logger.info("headers : {}", JSON.toJSONString(headers));
                LoggerUtil.logger.info("content : {}", conn.getContent().toString());
                is = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                LoggerUtil.logger.info("res : {}", content.toString());
                reader.close();
                is.close();
            } else {
                LoggerUtil.logger.info("res code : {}", conn.getResponseCode());
            }
            conn.disconnect();
        } catch (Exception e) {
            LoggerUtil.logger.error("testHttpConn error", e);
        } finally {
            // close
        }
    }

}
