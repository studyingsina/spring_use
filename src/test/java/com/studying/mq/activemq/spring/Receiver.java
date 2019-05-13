package com.studying.mq.activemq.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import com.alibaba.fastjson.JSON;

public class Receiver {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:amq-test.xml");

		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
		while (true) {
			Object map = jmsTemplate.receiveAndConvert();

			System.out.println("收到消息：" + JSON.toJSONString(map));
		}
	}
}