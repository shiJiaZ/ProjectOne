package cn.itcast.jx.shiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.itcast.jx.domain.Module;
import cn.itcast.jx.domain.Role;
import cn.itcast.jx.domain.User;
import cn.itcast.jx.service.UserService;
/**
 * 为什么你写的这个类能够称之为Realm域？
 * 答：要么集成父类 要么实现接口
 * 
 */
public class AuthRealm extends AuthorizingRealm{
	//配置UserService
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	

	/**
	 * 此方法由shiro安全管理器负责调用
	 * 参数：AuthenticationToken:LoginAction的login方法中将这个数据传过来
	 * 返回值：AuthenticationInfo：认证信息
	 * 思路：调用Service层，查找用户信息
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// 认证
		//向下转型
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		//根据用户名查找用户信息
		User user = userService.findUserByUsername(upToken.getUsername());
		
		if(user==null){
			return null;
		}
		//进入到此处，表明用户名是存在的
		/**
		 * 1 principal ：当前数据库中查找的用户信息
		 * 2 credentials：数据库中的用户的密码信息
		 * 3 realmName:当前的Realm域的名字哦,后面会用
		 */
		AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		
		return info;
	}
	
	/**
	 * 返回用户所能够访问的模块
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		//将信息装入AuthorizationInfo
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 授权
		//获取当前登录用户
		//方式一
		//User user = (User) ServletActionContext.getRequest().getSession().getAttribute(SysConstant.CURRENT_USER_INFO);
		//方式二
		//User user = (User) SecurityUtils.getSubject().getPrincipal();
		//方式三
		User user = (User) principals.fromRealm(this.getName()).iterator().next();
		//获取当前用户所能够访问的角色
		Set<Role> roles = user.getRoles();
		
		//定义一个集合，用来装数据
		List<String> list = new ArrayList<String>();
		
		//遍历角色
		for(Role r:roles){
			Set<Module> modules = r.getModules();
			//遍历模块
			for(Module m:modules){
				if(m.getCtype()==0){//0 主菜单  1 左侧菜单  2 三级菜单
					//['系统首页','货运管理','统计分析','系统管理']
					list.add(m.getCpermission());
				}
				
			}
		}
		
		info.addStringPermissions(list);
		
		return info;
	}
	
	
	
	
}
