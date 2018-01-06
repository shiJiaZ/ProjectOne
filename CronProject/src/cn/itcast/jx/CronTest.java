package cn.itcast.jx;

import java.util.Date;

import wjw.cron.ex.CronExpressionEx;
import wjw.cron.ex.DateFormatUtil;

public class CronTest {

	public static void main(String[] args) throws Exception {
		 //创建表达式对象
		 CronExpressionEx exp = new CronExpressionEx("0 0-5 14 * * ?");
		 //生成系统时间
	      Date dd = new Date();
	      for (int i = 1; i <= 100; i++) {
	        //获取下一次的执行时间
	    	dd = exp.getNextValidTimeAfter(dd);
	        System.out.println(i + ": " + DateFormatUtil.format("yyyy-MM-dd HH:mm:ss", dd) + "\n");
	        dd = new Date(dd.getTime() + 1000L);
	     
	      
	      
	     }

	}

}
