package cn.itcast.jx.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

import com.alibaba.fastjson.annotation.JSONField;

public class User extends BaseEntity{
	//id
	private String id;
	//dept_id
	@JSONField(serialize=false)
	private Dept dept;
	//用户名
	private String userName;
	//密码
	private String password;
	//状态
	private Integer state;
	//用户扩展信息  一对一的关系
	@JSONField(serialize=false)
	private UserInfo userInfo;
	//用户与角色：多对多
	@JSONField(serialize=false)
	private Set<Role> roles = new HashSet<Role>();
	/**
	 * 此处可以使用set和list集合
	 * 如果改成list，namehbm中如何配置？
	 * Set集合----><set>标签
	 * List集合---><list>标签
	 *           <bag>标签，当list集合使用这个标签配置的时候，就跟<set>标签的配置一样    
	 * @return
	 */
	@JSON(serialize=false)
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	@JSON(serialize=false)
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password="
				+ password + ", state=" + state + "]";
	}

	
	
}
