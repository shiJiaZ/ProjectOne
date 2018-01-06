package cn.itcast.invoke;

import java.net.MalformedURLException;

import cn.itcast.jx.IWeatherService;

import com.caucho.hessian.client.HessianProxyFactory;

public class InvokingHessian {

	public static void main(String[] args) throws Exception {
		//创建客户端代理对象
		HessianProxyFactory proxy = new HessianProxyFactory();
		//设置访问地址，并且返回数据
		IWeatherService service = (IWeatherService) proxy.create("http://localhost:8090/HessianServer/hessian");
		//输出
		String result = service.findWeatherByCityName("上海");
		
		System.out.println(result);
		
	}

}
