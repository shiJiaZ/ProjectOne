package cn.itcast.rs.webservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import cn.itcast.domain.User;
//让次接口称为restful风格的接口，提供restful风格的服务
//@Path("/userService")//访问这个接口的路径
@Consumes("*/*")//接口接收参数的格式:xml/json
@Produces("*/*")//接收返回参数的格式:xml/json
public interface IUserWebService {
	
	@POST
	@Path("/user")
	@Consumes({"application/xml","application/json"})//定义输入参数
	public void saveUser(User user);
	
	@DELETE
	@Path("/user/{id}")
	@Consumes({"application/xml","application/json"})
	public void deleteUser(@PathParam("id")Integer id);
	
	@PUT
	@Path("/user")
	@Consumes({"application/xml","application/json"})
	public void updateUser(User user);
	
	@GET
	@Path("/user/{id}")
	@Consumes({"application/xml","application/json"})
	@Produces({"application/xml","application/json"})//返回值
	public User findUserById(@PathParam("id")Integer id);
	
	@GET
	@Path("/user")
	@Produces({"application/xml","application/json"})
	public List<User> findAllUser();
	
	@PUT
	@Path("/user")
	@Consumes({"application/xml","application/json"})
	@Produces({"application/xml","application/json"})//返回值
	public User saveAndGetUser(User user);
	
}
