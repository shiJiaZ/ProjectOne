package cn.itcast.jx.mail;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailTest03 {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		//发送复杂邮件：带图片/带附件
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		//获取邮件发送的工具类
		JavaMailSenderImpl mailSenderImpl = (JavaMailSenderImpl) ctx.getBean("mailSender");
		//创建邮件
		MimeMessage mimeMessage = mailSenderImpl.createMimeMessage();
		//创建邮件助手
		/**
		 * 第一个参数:邮件信息
		 * 第二个参数：multipart，复杂邮件 true，是  false，否
		 */
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		/**
		 * 第一个参数：文本内容
		 * 第二个参数：表明第一个参数是否是html格式的内容
		 */
		helper.setText("<html><head><title>带图片的邮件</title></head><body><img src=cid:img></body></html>", true);
		/*helper.setText("<html><head><title>带图片的邮件</title></head><body><img src='d:\\dog.jpg'></body></html>", html);*/
		//获取硬盘上的资源，使其在发送邮件的时候，在网络上传输
		FileSystemResource fsr = new FileSystemResource(new File("d:\\dog.jpg"));
		//第一个参数：cid
		//第二个参数：文件资源
		helper.addInline("img", fsr);
		//设置标题
		helper.setSubject("带图片的邮件");
		
		//设置一个发送者
		helper.setFrom("shitcast@163.com");
		//设置接收者
		helper.setTo("1638064027@qq.com");
		
		//带附件
		helper.addAttachment("你好.jpg", fsr);
		
		
		//发送
		mailSenderImpl.send(mimeMessage);
		
		System.out.println("ok");
	}

}
