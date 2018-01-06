package cn.itcast.webservice;

import java.util.List;

import javax.jws.WebService;

import cn.itcast.domain.Car;


///webService的接口
@WebService
//什么是restful
//就是在rest思想指导下开发的接口，就是restful的接口，这个接口提供的服务就是restful的服务
public interface IUserService {
	
	public void say(String userName);
	
	public List<Car> findCarsByUsername(String userName);
	
	
}
