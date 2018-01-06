package cn.itcast.jx.mail;

import cn.itcast.jx.util.MailUtil;

public class MailTest02 {

	public static void main(String[] args) {
		
		try {
			MailUtil.sendMail("约吗?", "到传智播客一起学java开发", "1638064027@qq.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
