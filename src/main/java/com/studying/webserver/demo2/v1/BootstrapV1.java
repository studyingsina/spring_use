package com.studying.webserver.demo2.v1;

import com.studying.webserver.demo1.util.Logs;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by junweizhang on 17/11/20.
 * 第一版 WebServer,只实现基本功能,所有处理都是main线程中.
 */
public class BootstrapV1 {

    private ServerSocket ss;

    private File docRoot;

    private boolean isStop = false;

    public BootstrapV1(int port, File docRoot) throws Exception {
        // 1. 服务端启动8080端口，并一直监听；
        this.ss = new ServerSocket(port, 10);
        this.docRoot = docRoot;
    }

    public void serve() {
        Logs.SERVER.info("Http Server ready to receive requests...");
        while (!isStop) {
            try {
                process();
            } catch (Exception e) {
                Logs.SERVER.info("serve error", e);
                isStop = true;
                // System.exit(1);
            }
        }


    }

    /**
     * Demo版服务器处理逻辑:
     * 1. 启动服务器监听端口,如8080
     * 2. 开始监听客户端请求,如http请求
     * 3. 解析http请求参数
     * 4. 业务自己逻辑处理,如查询DB并返回数据
     * 5. 将返回数据按http协议格式返回
     */
    private void process() throws Exception {
        // 1. 服务端启动8080端口，并一直监听；
        ServerSocket ss = new ServerSocket(8080);

        // 2. 监听到有客户端（比如浏览器）要请求http://localhost:8080/，那么建立连接，TCP三次握手；
        Socket socket = ss.accept();
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        // 3. 建立连接后，读取此次连接客户端传来的内容（其实就是解析网络字节流并按HTTP协议去解析）；
        // GET /app/user HTTP/1.1
        String requestLine = reader.readLine();
        Logs.SERVER.info("requestLine is : {}", requestLine);
        if (requestLine == null || requestLine.length() < 1) {
            Logs.SERVER.error("could not read request");
            return;
        }
        String[] tokens = requestLine.split(" ");
        String method = tokens[0];
        String urlPath = tokens[1];

        // 4. 业务逻辑：组装给客户端的返回数据,如查DB
        String content = "服务端返回数据...";

        // 5. 找到资源后，再通过网络流将内容输出，当然，还是按照HTTP协议去输出，这样客户端（浏览器）就能正常渲染、显示网页内容；
        BufferedOutputStream bos = new BufferedOutputStream(os);
        int len = content.length();
        byte[] headerBytes = createHeaderBytes("HTTP/1.1 200 OK", len, "application/json;charset=utf-8");
        bos.write(headerBytes);
        byte[] buf = new byte[2000];
        bos.write(buf, 0, buf.length);
        bos.flush();
        socket.close();
    }

    /**
     * 生成HTTP Response头.
     *
     * @param content
     * @param length
     * @param contentType
     * @return
     */
    private byte[] createHeaderBytes(String content, long length, String contentType) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(baos));
        bw.write(content + "\r\n");
        if (length > 0) {
            bw.write("Content-Length: " + length + "\r\n");
        }
        if (contentType != null) {
            bw.write("Content-Type: " + contentType + "\r\n");
        }
        bw.write("\r\n");
        bw.flush();
        byte[] data = baos.toByteArray();
        bw.close();
        return data;
    }

    public static void main(String[] args) {
        try {
            int port = 8080;
            String docRootStr = "htmldir";
            URL url = BootstrapV1.class.getClassLoader().getResource(docRootStr);
            File docRoot = new File(url.toURI());
            BootstrapV1 bootstrap = new BootstrapV1(port, docRoot);
            bootstrap.serve();
        } catch (Exception e) {
            Logs.SERVER.error("main start error", e);
            System.exit(1);
        }
    }

}
