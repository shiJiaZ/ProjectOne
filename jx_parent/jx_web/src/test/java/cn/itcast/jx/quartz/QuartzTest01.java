package cn.itcast.jx.quartz;

import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QuartzTest01 {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		
		CronTriggerImpl trigger= (CronTriggerImpl) ctx.getBean("jobTrigger");
		
		//动态获取xml文件中的内容
		String expression = trigger.getCronExpression();
		
		
		System.out.println("itheima18.....:"+expression);
		
	}

}
