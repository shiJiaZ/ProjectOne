package cn.itcast.jx.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Dept implements Serializable{
	
	//表中的主键,在实体类中就叫id
	private String id;
	//其余属性按照驼峰命名法来
	private String deptName;
	//parent_id，外键,父部门，指向的是自己表的主键
	private Dept parent;
	//状态
	private Integer state;
	//配置部门与用户，一对多的关系
	private Set<User> users = new HashSet<User>();

	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Dept getParent() {
		return parent;
	}
	public void setParent(Dept parent) {
		this.parent = parent;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Dept [id=" + id + ", deptName=" + deptName + ", parent="
				+ parent + ", state=" + state + "]";
	}
	
}
