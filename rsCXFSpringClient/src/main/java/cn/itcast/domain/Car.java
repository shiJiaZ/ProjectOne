package cn.itcast.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="car")//名字随便写，无所谓，大小写不区分
public class Car {
	public Car() {
		super();
	}
	public Car(Integer id, String carName, Double price) {
		super();
		this.id = id;
		this.carName = carName;
		this.price = price;
	}
	private Integer id;
	private String carName;
	private Double price;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Car [id=" + id + ", carName=" + carName + ", price=" + price
				+ "]";
	}
	
	
}
