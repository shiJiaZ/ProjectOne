package cn.itcast.jx.action.sysadmin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.Dept;
import cn.itcast.jx.domain.Role;
import cn.itcast.jx.domain.User;
import cn.itcast.jx.service.DeptService;
import cn.itcast.jx.service.RoleService;
import cn.itcast.jx.service.UserService;
import cn.itcast.jx.util.Page;
import cn.itcast.jx.util.ResponseUtil;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends BaseAction implements ModelDriven<User>{
	//模型
	private User model = new User();

	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	private Page page = new Page();

	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	private String symbol = "user";

	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String list() throws Exception {
		//int i=1/0;
		//查找所有的用户信息
		userService.findPage("from User", page, User.class, null);
		//设置分页的URL
		page.setUrl("userAction_list");
		//放入值栈
		this.push(page);
		
		return "list";
	}
	
	public String toview() throws Exception {
		// 根据id查找用户信息
		User user = userService.get(User.class, model.getId());
		//放入值栈  root栈
		this.push(user);
		
		return "toview";
	}
	private DeptService deptService;
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public String tocreate() throws Exception {
		//1 查看所有启用的部门
		List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);
		//2 查看领导-所有的在职的人
		List<User> userList = userService.find("from User where state = 1", User.class, null);
		//3 放入值栈-map站
		this.put("deptList", deptList);
		this.put("userList", userList);
		
		return "tocreate";
	}
	
	
	public String insert() throws Exception {
		
		userService.saveOrUpdate(model);
		
		return SUCCESS;
	}
	
	/**
	 * 进入修改用户信息页面
	 * 
	 */
	public String toupdate() throws Exception {
		//1 所有的启用的部门
		List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);
		//2 当前的用户信息
		User user = userService.get(User.class, model.getId());
		
		//将信息放入值栈
		this.put("deptList", deptList);
		this.push(user);
		
		return "toupdate";
	}
	
	public String update() throws Exception {
		userService.saveOrUpdate(model);
		return SUCCESS;
	}
	
	
	public String delete() throws Exception {
		String[] ids = model.getId().split(", ");
		
		userService.delete(User.class, ids);
		return SUCCESS;
	}
	
	private String deptId;
	
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	/**
	 * 后台返回数据有几种方式
	 * 1 json
	 * 2 html
	 * 组装json的技术：
	 * 1 struts2-json-plugin
	 * 2 fastjson
	 * 3 Gson
	 * 4   手动拼接
	 * @return
	 * @throws Exception
	 */
	/**
	 * 方式一：采用struts2-json-plugin解决:默认会将关联属性全部转成json格式的数据
	 */
	public String findManagers1() throws Exception {
		//根据部门id查找部门的领导信息
		//List<User> userList = userService.find("from User where dept.id = ? and userInfo.degree != 4", User.class, new String[]{deptId});
		List<User> userList = userService.find("from User where dept.id = ?", User.class, new String[]{deptId});
		//放入值栈    struts2-json-plugin会自动从root栈顶取值
		this.push(userList);
		
		//返回值
		return "json";
	}
	/**
	 * 方式二：采用fastjson处理json格式串问题
	 * 
	 */
	public String findManagers2() throws Exception {
		//根据部门id查找部门的领导信息
		//List<User> userList = userService.find("from User where dept.id = ? and userInfo.degree != 4", User.class, new String[]{deptId});
		List<User> userList = userService.find("from User where dept.id = ?", User.class, new String[]{deptId});
		//采用fastjson转
		String result = JSON.toJSONString(userList);
		
		//手动返回
		ResponseUtil.writeContentToClient(result);
		//返回值
		return NONE;
	}
	/**
	 * 方式三：返回html
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findManagers() throws Exception {
		//根据部门id查找部门的领导信息
		//List<User> userList = userService.find("from User where dept.id = ? and userInfo.degree != 4", User.class, new String[]{deptId});
		List<User> userList = userService.find("from User where dept.id = ?", User.class, new String[]{deptId});
		//拼接返回的串信息
		String result = "";
		for(User u:userList){
			result+="<option value='"+u.getId()+"'>"+u.getUserName()+"</option>";
		}
		
		//手动返回
		ResponseUtil.writeContentToClient(result);
		//返回值
		return NONE;
	}
	
	private RoleService roleService;
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	/**
	 *  1 所有的角色
		2 用户的信息
		3 当前用户已经拥有的角色
	 */
	public String torole() throws Exception {
		//1 所有的角色信息
		List<Role> roleList = roleService.find("from Role", Role.class, null);
		//2 准备用户信息
		User user = userService.get(User.class, model.getId());
		//3 准备当前用户所具有的角色
		Set<Role> roles = user.getRoles();
		
		//放入值栈
		this.put("roleList", roleList);//1
		this.put("roles", roles);//2
		this.push(user);//3
		
		//方案一 ：1,2,3
		//方案二：1,3,4
		String userRoleStr = "";
		for(Role r:roles){//用户已经拥有角色
			userRoleStr+=r.getName()+",";
		}
		
		this.put("userRoleStr", userRoleStr);//4
		
		return "torole";
	}
	private String[] roleIds;
	
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	/**
	 * 页面多个同名的文本框，后台如何接收值？
	 * <input type="checkbox" value="4028a1c34ec2e5c8014ec2ebf8430001"  name="roleIds">
	   <input type="checkbox" value="4028a1c34ec2e5c8014ec2ebf8430002"  name="roleIds">
	   <input type="checkbox" value="4028a1c34ec2e5c8014ec2ebf8430003"  name="roleIds">
	   <input type="checkbox" value="4028a1c34ec2e5c8014ec2ebf8430004"  name="roleIds">	
	 * 
	 * 后台可以采用如下方式接收值
	 * String-->4028a1c34ec2e5c8014ec2ebf8430001, 4028a1c34ec2e5c8014ec2ebf8430002, 4028a1c34ec2e5c8014ec2ebf8430003
	 * String[]-->['4028a1c34ec2e5c8014ec2ebf8430001','4028a1c34ec2e5c8014ec2ebf8430001',4028a1c34ec2e5c8014ec2ebf8430001]
	 * List<String>--->
	 * @throws Exception
	 */
	public String role() throws Exception {
		//1 查找用户信息--修改的是用户对应的角色
		User user = userService.get(User.class, model.getId());
		//2 查找角色信息
		Set<Role> roles = new HashSet<Role>();
		if(roleIds!=null&&roleIds.length>0){
			for(String id:roleIds){
				Role role = roleService.get(Role.class, id);
				roles.add(role);
			}
		}
		//3  整体替换:多对多自动维护中间表，无需级联
		user.setRoles(roles);
		
		//4 调用Service层
		userService.saveOrUpdate(user);
		return SUCCESS;
	}
	
	
	
	
	
	
	
	
	
}
    