package cn.itcast.spring;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class MyProducer01 {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		//获取jmsTemplate
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-mq.xml");
		//获取
		JmsTemplate queueTemplate = (JmsTemplate) ctx.getBean("queueTemplate");
		//发送消息
		//第一个参数：目的地
		queueTemplate.send("18itheima", new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//创建消息
				TextMessage message = session.createTextMessage("使用Spring整合MQ，采用模板发送消息");
				return message;
			}
		});
		
		System.out.println("发送ok");
	}

}
