package cn.itcast.jx.quartz;

import java.util.Date;

/**
 * 定时任务的类名自定义，方法自定义
 * 
 * @Description:
 * @author:     传智播客 java学院    传智.袁老师
 * @version:    1.0
 * @Company:    http://java.itcast.cn 
 * @date:       2017年12月21日
 */
public class JobTest {
	
	//随便写的方法名
	public void execute(){
		
		System.out.println("执行了调度:"+new Date());
		
	}
	
	
}
