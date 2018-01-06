package cn.itcast.jx.util;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

public class ResponseUtil {
	
	public static void writeContentToClient(String content) throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(content);
	}
	
	
	
}
