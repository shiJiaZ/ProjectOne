package cn.itcast.invoke;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import cn.itcast.domain.User;

public class InvokingService {

	public static void main(String[] args) {
		User user = new User(1, "jack");
		
		User user2 = WebClient.create("http://localhost:8090/rsCXFSpringServer/ws/userService/user")
				.type(MediaType.APPLICATION_JSON)
				.put(user, User.class);//返回单个对象
//				.postAndGetCollection(body, memberClass)//返回集合数据
		
		System.out.println(user2);
		
		
		
		
		
		
		
	}

}
