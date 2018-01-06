package cn.itcast.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private Integer id;
	private String userName;
	private List<Car> cars = new ArrayList<Car>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<Car> getCars() {
		return cars;
	}
	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", cars=" + cars
				+ "]";
	}
	
	
	
}
