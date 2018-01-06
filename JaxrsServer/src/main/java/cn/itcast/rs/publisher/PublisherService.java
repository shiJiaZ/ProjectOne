package cn.itcast.rs.publisher;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import cn.itcast.rs.webservice.UserWebService;

public class PublisherService {

	public static void main(String[] args) {
		//1 创建服务工厂类
		JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
		//2 设置访问地址
		factory.setAddress("http://localhost:12345/");
		//3 设置服务对象
		factory.setServiceBean(new UserWebService());
		
		/********添加日志记录*****/
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		
		
		//4 发布服务
		factory.create();
		
	}

}
