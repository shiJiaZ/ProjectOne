package cn.itcast.pubsub;

import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MyConsumer02 {
	//广播模式的消费者
	public static void main(String[] args) throws Exception {
		//1 创建连接工厂  
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		//2 创建连接
		Connection connection = connectionFactory.createConnection();
		//3 开启连接
		connection.start();
		//4 创建会话
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		//5 创建队列
		Topic topic = session.createTopic("itheima18");
		//6 创建消费者
		MessageConsumer consumer = session.createConsumer(topic);
		//7 消费消息
		while(true){
			//消费者一秒钟去服务器抓取消息一次
			//参数是间隔时间
			TextMessage message = (TextMessage) consumer.receive(10000);
			if(message!=null){
				System.out.println("广播模式02："+message.getText());
			}else{
				break;
			}
			
		}
		
		//8 提交事务
		session.commit();
		//9 关闭连接
		session.close();
		connection.close();
		
		System.out.println("广播模式/主题模式消费结束");
	}

}
