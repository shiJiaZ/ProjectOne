package cn.itcast.invoke;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.webservice.Car;
import cn.itcast.webservice.IUserService;

public class InvokingWebService {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		//加载配置文件
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-cxf.xml");
		//获取bean对象
		IUserService userService = (IUserService) ctx.getBean("client");
		//访问方法
//		userService.say("马化腾");

		List<Car> list = userService.findCarsByUsername("itcast");
		
		for(Car car:list){
			System.out.println(car.getCarName());
		}
		
	}

}
