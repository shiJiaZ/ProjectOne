package cn.itcast.webservice;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.domain.Car;

//webService的实现类
public class UserServiceImpl implements IUserService{

	@Override
	public void say(String userName) {
		System.out.println(userName+"大声说:我要上传智大学");
	}

	@Override
	public List<Car> findCarsByUsername(String userName) {
		List<Car> list = new ArrayList<Car>();
		if(userName.equals("itcast")){
			list.add(new Car(1, "奔驰", 9.9d));
			list.add(new Car(2, "宝马", 10.9d));
			list.add(new Car(3, "购物车", 99.9d));
		}
		return list;
	}
	
	
	
	
	
}
