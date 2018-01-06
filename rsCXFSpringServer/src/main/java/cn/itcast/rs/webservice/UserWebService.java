package cn.itcast.rs.webservice;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.domain.User;

public class UserWebService implements IUserWebService {
	
	@Override
	public void saveUser(User user) {
		System.out.println("保存了用户:"+user);
	}

	@Override
	public void deleteUser(Integer id) {
		System.out.println("删除的用户id："+id);
	}

	@Override
	public void updateUser(User user) {
		System.out.println("更新了用户:"+user);
	}

	@Override
	public User findUserById(Integer id) {
		return new User(1, "lucy");
	}

	@Override
	public List<User> findAllUser() {
		List<User> list = new ArrayList<User>();
		list.add(new User(1, "lucy"));
		list.add(new User(2, "tom"));
		list.add(new User(3, "jack"));
		list.add(new User(4, "rose"));
		
		return list;
	}

	@Override
	public User saveAndGetUser(User user) {
		System.out.println("传过来的参数信息:"+user);
		return new User(123, "12365");
	}

}
