package cn.itcast.jx.invoke;

import java.util.List;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import cn.itcast.webservice.Car;
import cn.itcast.webservice.IUserService;

public class InvokingService {
	
	public static void main(String[] args) {
		//1 创建客户端代理对象
		JaxWsProxyFactoryBean proxy = new JaxWsProxyFactoryBean();
		//2 设置wsdl使用说明书的地址
		proxy.setAddress("http://127.0.0.1:12345/userService?wsdl");
		//3 设置接收的接口
		proxy.setServiceClass(IUserService.class);
		//4 接收实例
		IUserService userService = (IUserService) proxy.create();
		//5 调用远程方法
		userService.say("新新");
		/*System.out.println("客户端..");
		
		List<Car> list = userService.findCarsByUsername("itcast");
		for(Car car:list){
			System.out.println(car.getId()+":"+car.getCarName()+":"+car.getPrice());
		}*/
		
		
		
		
	}
	
	
	
}
