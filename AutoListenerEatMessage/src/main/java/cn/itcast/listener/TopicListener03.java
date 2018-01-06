package cn.itcast.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import cn.itcast.util.SmsUtil;

import com.aliyuncs.exceptions.ClientException;
/**
 * queue队列的消息消费
 */
public class TopicListener03 implements MessageListener{

	@Override//组装消息1212124785&&&&&&&&&&消息内容
	public void onMessage(Message message) {
		 try {
			TextMessage tm = (TextMessage) message;
			 String text = tm.getText();
			 String[] values = text.split("&&&&&&&&&&");
			 if(values.length==2){
				//发送短信
				 SmsUtil.sendSms(values[0],values[1] );
			 }
			 
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	
}
