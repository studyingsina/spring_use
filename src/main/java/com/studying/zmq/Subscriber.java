package com.studying.zmq;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class Subscriber {

	public static void main(String[] args) {
		Context context = ZMQ.context(1);
		Socket subscriber = context.socket(ZMQ.SUB);
		subscriber.connect("tcp://127.0.0.1:5557");
//		subscriber.connect("tcp://192.168.3.180:5557");
		subscriber.subscribe("".getBytes());
		int total = 0;
		while (true) {
			byte[] stringValue = subscriber.recv(0);
			String string = new String(stringValue);
			if (string.equals("send end......")) {
				break;
			}
			total++;
			System.out.println("Received " + total + " updates. :" + string);
		}

		subscriber.close();
		context.term();
	}
}