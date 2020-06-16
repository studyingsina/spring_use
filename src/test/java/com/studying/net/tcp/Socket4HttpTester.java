package com.studying.net.tcp;

import com.studying.net.http.HttpTester;
import com.studying.util.LoggerUtil;
import org.junit.Test;

import java.io.*;
import java.net.*;

/**
 * Created by junweizhang on 2019/5/14.
 */
public class Socket4HttpTester {

    @Test
    public void testGet() {
        try {
            String host = HttpTester.TEST_HOST;
            int port = HttpTester.TEST_PORT;
            String path = HttpTester.TEST_PATH;
            SocketAddress address = new InetSocketAddress(host, port);
            Socket socket = new Socket();
            socket.connect(address);
            OutputStreamWriter sw = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter writer = new BufferedWriter(sw);

            writer.write("GET " + path + " HTTP/1.1\r\n");
            writer.write("Host: " + host + "\r\n");
            writer.write("\r\n");
            writer.flush();

            BufferedInputStream sr = new BufferedInputStream(socket.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(sr, "utf-8"));
            StringBuffer response = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            LoggerUtil.logger.info("res : {}", response.toString());
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            LoggerUtil.logger.error("testGet error", e);
        }
    }

    @Test
    public void testPost() {
        try {
            String host = HttpTester.TEST_HOST;
            int port = HttpTester.TEST_PORT;
            String path = HttpTester.TEST_PATH;
            String data = URLEncoder.encode("name", "utf-8") + "=" + URLEncoder.encode("qq", "utf-8") + "&" +
                    URLEncoder.encode("age", "utf-8") + "=" + URLEncoder.encode("32", "utf-8");
            SocketAddress dest = new InetSocketAddress(host, port);
            Socket socket = new Socket();
            socket.connect(dest);
            OutputStreamWriter sw = new OutputStreamWriter(socket.getOutputStream(), "utf-8");
            BufferedWriter writer = new BufferedWriter(sw);

            writer.write("POST " + path + " HTTP/1.1\r\n");
            writer.write("Host: " + host + "\r\n");
            writer.write("Content-Length: " + data.length() + "\r\n");
            writer.write("Content-Type: application/json; charset=utf-8\r\n");
            writer.write("\r\n");
            writer.write(data);
            writer.flush();
            writer.write("\r\n");
            writer.flush();

            BufferedInputStream sr = new BufferedInputStream(socket.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(sr, "utf-8"));
            StringBuffer response = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            LoggerUtil.logger.info("res : {}", response.toString());
            reader.close();
            writer.close();
            socket.close();

        } catch (Exception e) {
            LoggerUtil.logger.error("testPost error", e);
        }
    }

}
