package cn.itcast.p2p;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MyConsumer02 {

	public static void main(String[] args) throws Exception {
		//1 创建连接工厂  
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		//2 创建连接
		Connection connection = connectionFactory.createConnection();
		//3 开启连接
		connection.start();
		//4 创建会话
		final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		//5 创建队列
		Queue queue = session.createQueue("itheima18");
		//6 创建消费者
		MessageConsumer consumer = session.createConsumer(queue);
		//7 消费消息
		/*while(true){
			//消费者一秒钟去服务器抓取消息一次
			//参数是间隔时间
			TextMessage message = (TextMessage) consumer.receive(10000);
			if(message!=null){
				System.out.println("p2p:02:"+message.getText());
			}else{
				break;
			}
			
		}*/
		
		//监听器消费消息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				try {
					TextMessage tm = (TextMessage) message;
					String result = tm.getText();
					System.out.println(result);
					//8 提交事务
					session.commit();
					
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		/*while(true){
			
		}*/
		
		
		//9 关闭连接
		//session.close();
		//connection.close();
		
		System.out.println("消费成功");
	}

}
