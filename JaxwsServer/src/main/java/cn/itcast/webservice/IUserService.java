package cn.itcast.webservice;

import java.util.List;

import javax.jws.WebService;

import cn.itcast.domain.Car;


///webService的接口
@WebService
public interface IUserService {
	
	public void say(String userName);
	
	public List<Car> findCarsByUsername(String userName);
	
	
}
