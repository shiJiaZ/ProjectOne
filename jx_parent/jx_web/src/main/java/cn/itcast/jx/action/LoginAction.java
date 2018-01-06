package cn.itcast.jx.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import cn.itcast.jx.common.SysConstant;
import cn.itcast.jx.domain.User;


/**
 * 
 * @Description:
 * @author:     传智播客 java学院    传智.袁新奇
 * @version:    1.0
 * @Company:    http://java.itcast.cn 
 * @date:       2016年9月17日
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;



	//SSH传统登录方式
	public String login() throws Exception {
		//重复登录的问题解决
		/*****
		 * 方案一：从session中获取登录信息，如果能取到，直接返回success
		 */
		//User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		/**
		 * 方式二：从Shiro中取
		 */
		//2 获取Subject
		Subject subject = SecurityUtils.getSubject();
		//从shiro中取出user信息
		/*User user = (User) subject.getPrincipal();
		
		if(user!=null){
			return SUCCESS;
		}
		*/
		
		/**
		 * 方式三：在shiro中提供了 是否已经认证的方法
		 * 
		 */
		if(subject.isAuthenticated()){
			return SUCCESS;
		}
		
		
		if(StringUtils.isBlank(username)){
			return LOGIN;
		}
		
		try {
			//1 接收页面参数，这个工作已经完成了
			
			//3 启动shiro安全管理器
			//将信息封装到UsernamePasswordToken中
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			
			subject.login(token);
			
			
			//9 如果运行到此行代码，表明用户已经登录成功
			//获取当前登录的用户信息
			User loginUser = (User) subject.getPrincipal();
			//放入session中
			session.put(SysConstant.CURRENT_USER_INFO, loginUser);
		} catch (Exception e) {
			//e.printStackTrace();
			request.put("errorInfo", "用户名或者密码错误");
			return LOGIN;
		}
		
		
		return SUCCESS;
	}
	
	public String logout() throws Exception {
		//注销：shiro注销；session清空
		session.remove(SysConstant.CURRENT_USER_INFO);
		// shiro注销的时候，也会清空session
		SecurityUtils.getSubject().logout();
		
		return "logout";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

