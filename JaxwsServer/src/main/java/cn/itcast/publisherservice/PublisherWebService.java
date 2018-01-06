package cn.itcast.publisherservice;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import cn.itcast.webservice.UserServiceImpl;

public class PublisherWebService {
	
	public static void main(String[] args) {
		
		// 1 创建服务工厂类
		JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
		// 2 设置服务访问地址
		factory.setAddress("http://127.0.0.1:12345/userService");
		// 3 设置服务对象
		factory.setServiceBean(new UserServiceImpl());
		
		/*************添加日志记录信息*************/
		//控制台输出 --请求的信息
		factory.getInInterceptors().add(new LoggingInInterceptor());
		//控制台输出--响应的信息
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		
		// 4 发布服务
		factory.create();
		
		System.out.println("服务端");
	}
	
}
