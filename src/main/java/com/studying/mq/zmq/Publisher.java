package com.studying.mq.zmq;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class Publisher {

	public static void main(String[] args) throws InterruptedException {
		Context context = ZMQ.context(1);
		Socket publisher = context.socket(ZMQ.PUB);
//		publisher.bind("tcp://192.168.3.180:5557");
		publisher.bind("tcp://127.0.0.1:5557");
		try {
			// zmq发送速度太快，在订阅者尚未与发布者建立联系时，已经开始了数据发布
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		publisher.send("send start......".getBytes(), 0);
		for (int i = 0; i < 10; i++) {
			publisher.send(("Hello world " + i).getBytes(), ZMQ.NOBLOCK);
		}
		publisher.send("send end......".getBytes(), 0);

		publisher.close();
		context.term();
	}
}