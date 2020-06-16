package com.studying.net.http;

import com.studying.util.LoggerUtil;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by junweizhang on 2019/5/16.
 */
public class NioHttpClientTester {

    @Test
    public void testGet() {
        try {
            String host = HttpTester.TEST_HOST;
            int port = HttpTester.TEST_PORT;
            String path = HttpTester.TEST_PATH;

            InetSocketAddress remoteAddress = new InetSocketAddress(host, port);
            // 调用open的静态方法创建连接指定的主机的SocketChannel
            SocketChannel socketChangel = SocketChannel.open(remoteAddress);
            // 设置该sc已非阻塞的方式工作
            socketChangel.configureBlocking(false);

            // 将SocketChannel对象注册到指定的Selector
            Selector selector = Selector.open();
            socketChangel.register(selector, SelectionKey.OP_READ);
            sendMessage(socketChangel, createHttpMsg(host, path));

            boolean remoteConnClosed = false;
            while (!remoteConnClosed) {
                //设置超时为1分钟
                int n = selector.select(1000 * 60);
                if (n <= 0) {
                    break;
                }
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isReadable()) {
                        //如果读取的数据大于1024, 则下次调用 selector.select 的数值还会大于0
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        SocketChannel client = (SocketChannel) key.channel();
                        int num = client.read(buffer);
                        while (num > 0) {
                            //如果num = 0, 表示读取不到远端数据，可能是远端网速慢或者其他网络原因，下次selector.select返回时可再读; 如果num=-1,表示远端关闭了连接。
                            client.read(buffer);
                            String res = new String(buffer.array(), "utf-8");
                            LoggerUtil.logger.info("testGet : {}", res);
                            num = client.read(buffer);
                        }

                        if (num == -1) {
                            //远程连接主动关闭
                            LoggerUtil.logger.info("remote server close connection...");
                            client.close();
                            key.cancel();
                            remoteConnClosed = true;
                        }

                    }
                    //这里必须移除掉该selectionKey, 否则下次select调用时还会存在
                    iter.remove();
                }
            }
        } catch (Exception e) {
            LoggerUtil.logger.error("testGet error", e);
        }
    }

    private String createHttpMsg(String host, String path) {
        StringBuffer req = new StringBuffer();
        req.append("GET ").append(path).append(" HTTP/1.1").append("\r\n");
        req.append("Host: ").append(host).append("\r\n");
        // req.append("Connection: Closed");
        req.append("Connection: keep-alive");
        req.append("\r\n").append("\r\n");
        return req.toString();
    }

    private void sendMessage(SocketChannel client, String msg) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        client.write(buffer);
    }

    /**
     * https://segmentfault.com/a/1190000017040893
     * @throws Exception
     */
    public void testDemo() throws Exception {
        // 创建一个selector
        Selector selector = Selector.open();

        // 初始化TCP连接监听通道
        ServerSocketChannel listenChannel = ServerSocketChannel.open();
        listenChannel.bind(new InetSocketAddress(9999));
        listenChannel.configureBlocking(false);
        // 注册到selector（监听其ACCEPT事件）
        listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 创建一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(100);

        while (true) {
            selector.select(); //阻塞，直到有监听的事件发生
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();

            // 通过迭代器依次访问select出来的Channel事件
            while (keyIter.hasNext()) {
                SelectionKey key = keyIter.next();

                if (key.isAcceptable()) { // 有连接可以接受
                    SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                    LoggerUtil.logger.info("connect address : {}", channel.getRemoteAddress());
                } else if (key.isReadable()) { // 有数据可以读取

                    buffer.clear();
                    // 读取到流末尾说明TCP连接已断开，
                    // 因此需要关闭通道或者取消监听READ事件
                    // 否则会无限循环
                    if (((SocketChannel) key.channel()).read(buffer) == -1) {
                        key.channel().close();
                        continue;
                    }

                    // 按字节遍历数据
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        byte b = buffer.get();
                        if (b == 0) { // 客户端消息末尾的\0
                            // 响应客户端
                            buffer.clear();
                            buffer.put("Hello, Client!\0".getBytes());
                            buffer.flip();
                            while (buffer.hasRemaining()) {
                                ((SocketChannel) key.channel()).write(buffer);
                            }
                        } else {
                            LoggerUtil.logger.info("print char : {}", b);
                        }
                    }
                }

                // 已经处理的事件一定要手动移除
                keyIter.remove();
            }
        }
    }
}
