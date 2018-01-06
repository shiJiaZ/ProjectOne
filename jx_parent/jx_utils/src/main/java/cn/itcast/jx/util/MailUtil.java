package cn.itcast.jx.util;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	
	public static void  sendMail(String subject,String text,String address) throws Exception{
		//1 准备邮件发送的参数
		Properties props = new Properties();
		//设置主机地址  smtp.qq.com   smtp.126.com   smtp.163.com  smtp.itcast.cn
		props.put("mail.smtp.host", "smtp.163.com");
		//是否开启验证,必须开启用户名密码校验
		props.put("mail.smtp.auth", true);
		
		//2 获取与邮件服务器的连接
		Session session = Session.getDefaultInstance(props);
		//3 创建一封简单邮件
		MimeMessage message = new MimeMessage(session);
		//4 设置标题
		message.setSubject(subject);
		//5  设置正文
		message.setText(text);
		//6 设置发送者
		InternetAddress fromAddress = new InternetAddress("shitcast@163.com");
		message.setFrom(fromAddress);
		//7 设置接收者
		InternetAddress toAddress = new InternetAddress(address);
		//第一个参数：to  发送者  cc 抄送    bcc  暗送密送
		//第二个参数：接收者地址
		message.setRecipient(RecipientType.TO, toAddress);
		//8 发送 
		Transport transport = session.getTransport("smtp");
		transport.connect("shitcast@163.com", "q7w8e9a4s5d6");
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		
		System.out.println("ok");
		
	}
	
}
