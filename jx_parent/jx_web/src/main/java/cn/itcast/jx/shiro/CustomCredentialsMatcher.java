package cn.itcast.jx.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import cn.itcast.jx.util.Encrypt;

/**
 * 
 * @Description:
 * @author:     传智播客 java学院    传智.袁老师
 * @version:    1.0
 * @Company:    http://java.itcast.cn 
 * @date:       2017年12月10日
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher{

	/**
	 * Credentials:身份，凭证，
	 * 身份匹配/凭证匹配，密码比较
	 * 第一个参数：token ：用户页面输入的信息
	 * 第二个参数：info ： 认证之后的信息
	 * 返回值：boolean ：密码比较是否成功
	 */
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		//获取用户页面的密码
		//向下转型
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		//获取密码
		char[] pwd = upToken.getPassword();
		
		//页面的密码加密
		String newPwd = Encrypt.md5(new String(pwd), upToken.getUsername());
		//数据库中的密码
		Object dbPwd = info.getCredentials();
		
		
		return equals(newPwd, dbPwd);
	}
	
	
	
	
	
}
