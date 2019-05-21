package com.studying.net.http;

import com.studying.util.LoggerUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.junit.Test;

import java.net.URI;
import java.nio.charset.Charset;

/**
 * Created by junweizhang on 2019/5/15.
 */
public class NettyHttpClientTester {

    @Test
    public void testGet() {
        try {
//            HttpPost post = new HttpPost(HttpTester.TEST_URL);
//            post.addHeader("Content-Type", "application/json; charset=utf-8");
//            String res = HttpUtils.post(post);

            URI uri = new URI(HttpTester.TEST_URL);
            String host = uri.getHost();
            int port = uri.getPort();


            // Configure the client.
            EventLoopGroup group = new NioEventLoopGroup();

            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).handler(new HttpClientInitializer());

            // Make the connection attempt.
            Channel ch = b.connect(host, port).sync().channel();

            // Prepare the HTTP request.
            FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath());
            request.headers().set(HttpHeaderNames.HOST, host);
            request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
            request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);

            // Send the HTTP request.
            ch.writeAndFlush(request);

            // Wait for the server to close the connection.
            ch.closeFuture().sync();

            // Shut down executor threads to exit.
            group.shutdownGracefully();

//            LoggerUtil.logger.info("testGet res : {}", res);
        } catch (Exception e) {
            LoggerUtil.logger.error("testGet error", e);
        }
    }

    class HttpClientMsgHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse response) throws Exception {
            if (!response.headers().isEmpty()) {
                for (CharSequence name : response.headers().names()) {
                    for (CharSequence value : response.headers().getAll(name)) {
                        LoggerUtil.logger.error("HEADER : {} = {}", name, value);
                    }
                }
            }
            LoggerUtil.logger.info("HttpClientMsgHandler res : {}", response.content().toString(Charset.forName("utf-8")));
        }
    }

    class HttpClientInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        public void initChannel(SocketChannel ch) {
            ChannelPipeline p = ch.pipeline();
            p.addLast(new HttpClientCodec());

            // Remove the following line if you don't want automatic content
            // decompression.
            p.addLast(new HttpContentDecompressor());//这里要添加解压，不然打印时会乱码

            // Uncomment the following line if you don't want to handle
            // HttpContents.
            // p.addLast(new HttpObjectAggregator(1048576));
            p.addLast(new HttpObjectAggregator(123433));//添加HttpObjectAggregator， HttpClientMsgHandler才会收到FullHttpResponse
            p.addLast(new HttpClientMsgHandler());
        }

    }
}