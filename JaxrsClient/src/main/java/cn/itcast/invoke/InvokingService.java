package cn.itcast.invoke;

import java.util.Collection;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import cn.itcast.domain.User;

public class InvokingService {
	public static void main(String[] args) {
		//新增用户
//		User user = new User(1, "rose");
		
//		Response response = WebClient.create("http://localhost:12345/userService/user")
//			.type(MediaType.APPLICATION_XML)//规定参数的数据格式:xml/json，默认值是xml
//			.post(user);
		
		
		//修改
		/*User user = new User(1, "lucy");
		
		Response response = WebClient.create("http://localhost:12345/userService/user")
				.type(MediaType.APPLICATION_JSON)//规定参数的数据格式:xml/json，默认值是xml
				.put(user);*/
		
		//查找所有
		/*Collection<? extends User> collection = WebClient.create("http://localhost:12345/userService/user")
				.accept(MediaType.APPLICATION_JSON)//规定参数的数据格式:xml/json，默认值是xml
				.getCollection(User.class);
		
		for(User u:collection){
			System.out.println(u);
		}*/
		
		
		//删除
		//Response response = WebClient.create("http://localhost:12345/userService/user/1").delete();
		
		//根据id查找
		User user = WebClient.create("http://localhost:12345/userService/user/1")
			.accept(MediaType.APPLICATION_JSON)
			.get(User.class);
		
		System.out.println(user);
		
		
//		System.out.println(response.getStatus());
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		System.out.println("客户端运行结束....");
	}
}
