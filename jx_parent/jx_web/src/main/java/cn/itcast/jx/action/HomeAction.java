package cn.itcast.jx.action;

/**
 * 
 * @Description:
 * @author:     传智播客 java学院    传智.袁新奇
 * @version:    1.0
 * @Company:    http://java.itcast.cn 
 * @date:       2016年9月17日
 */
public class HomeAction extends BaseAction{
	private String moduleName;		//动态指定跳转的模块，在struts.xml中配置动态的result
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String fmain(){
		return "fmain";
	}
	
	public String title(){
		return "title";
	}

	//转向moduleName指向的模块
	public String tomain(){
		return "tomain";
	}
	public String toleft(){
		return "toleft";
	}

}
