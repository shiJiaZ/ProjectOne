package cn.itcast.jx.test;

import cn.itcast.jx.domain.Dept;

public class TestClass {

	public static void main(String[] args) {
		Dept dept = new Dept();
		//? extends Dept   
		Class<? extends Dept> class1 = dept.getClass();
		
		System.out.println(dept.getClass());

		System.out.println(Dept.class);
		
	}

}
