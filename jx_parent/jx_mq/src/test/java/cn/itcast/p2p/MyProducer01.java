package cn.itcast.p2p;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MyProducer01 {
	//queue:队列
	public static void main(String[] args) throws Exception {
		//1 创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		//2 创建连接
		Connection connection = connectionFactory.createConnection();
		//3 开启连接
		connection.start();
		//4 创建会话
		//第一个参数：是否开启事务 
		//第二个参数：消息消费之后，自动确认
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		//5 创建队列:Queue
		Queue queue = session.createQueue("itheima18");
		//6 创建生产者
		MessageProducer producer = session.createProducer(queue);
		for(int i=100;i<200;i++){
			//7 创建消息
			TextMessage textMessage = session.createTextMessage(i+"鲁兄,你到现在没有女朋友，是不是喜欢男的？");
			//8 发送消息
			producer.send(textMessage);
		}
		//9 提交事务
		session.commit();
		//10 关闭连接
		connection.close();
		
		
		System.out.println("ok");
	}

}
