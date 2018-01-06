package cn.itcast.jx.action.stat;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.dao.common.SqlDao;
import cn.itcast.jx.util.file.FileUtil;

public class StatChartAction extends BaseAction{
	
	private SqlDao sqlDao;
	
	
	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}


	/**
	 * 查询数据库
	 * 如何查数据库？？
	 * 1 hql--- 
	 * 2 sql---好,使用jdbcTemplate来查找数据
	 * 在当前的项目中，既有hibernate，又有jdbcTemplate
	 * 可以用hibernate做增删改，jdbcTemplate只进行查询操作
	 * 
	 * 手动拼接xml数据
  <slice title="Twice a day" pull_out="true">358</slice>
  <slice title="Once a day">258</slice>
  <slice title="Once a week">154</slice>
  <slice title="Never" url="http://www.interactivemaps.org" description="Click on the slice to find more information" alpha="50">114</slice>

	 * 
	 * 
	 */
	@SuppressWarnings({ "deprecation", "resource" })
	public String factorysale() throws Exception {
		String sql = "select factory_name,sum(amount) from contract_product_c group by factory_name order by sum(amount) desc";
		
		List<String> list = sqlDao.executeSQL(sql);
		StringBuffer sb = new StringBuffer();
		
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<pie>");
		for(int i=0;i<list.size();i++){
			sb.append("<slice title='"+list.get(i)+"'>"+list.get(++i)+"</slice>");
		}
		sb.append("</pie>");
		
		// 将数据写出去--写到tomcat中
//		/项目运行的时候，访问的都是tomcat中的内容
//		/如果现在写成功了，
		//获取tomcat的路径
		String path = ServletActionContext.getRequest().getRealPath("/");
		//path+="stat/chart/factorysale/data.xml";
		
		//写出去
		//new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
		/*BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path))));
		writer.write(sb.toString());
		writer.flush();
		writer.close();*/
		
		FileUtil fileUtil = new FileUtil();
		//第一个参数：写入的路径
		//第二个参数：文件名
		//第三个参数：内容
		//第四个参数：编码
		fileUtil.createTxt(path+"stat/chart/factorysale", "data.xml", sb.toString(), "UTF-8");
		
		
		return "factorysale";
	}
	
	public String productsale() throws Exception {
		String sql = "select * from (select product_no,sum(cnumber) mycount from contract_product_c group by product_no order by sum(cnumber) desc) where rownum<15";
		
		List<String> list = sqlDao.executeSQL(sql);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<chart>");
		
		/*********x轴********/
		sb.append("<series>");
		for(int i=0,j=0;i<list.size();i+=2,j++){
			sb.append("<value xid='"+j+"'>"+list.get(i)+"</value>");
		}
		sb.append("</series>");
		
		/**********y轴***********/
		sb.append("<graphs>");
		sb.append("<graph gid='30' color='#FFCC00' gradient_fill_colors='#111111, #1A897C'>");
		for(int i=1;i<list.size();i+=2){
			sb.append("<value xid='"+i/2+"' description='' url=''>"+list.get(i)+"</value>");
		}
		sb.append("</graph>");
		sb.append("</graphs>");
		/**********图标的设置*********/
		sb.append("<labels>");
		sb.append("<label lid='0'>");
		sb.append("<x>0</x>");
		sb.append("<y>20</y>");
		sb.append("<rotate></rotate> ");
		sb.append("<width></width> ");
		sb.append("<align>center</align>");
		sb.append("<text_color></text_color>");
		sb.append("<text_size></text_size>");
		sb.append("<text> ");
		sb.append("<![CDATA[<b>产品销量排行</b>]]>");
		sb.append(" </text> ");
		sb.append("</label>");
		sb.append("</labels>");
		sb.append("</chart>");
		
		
		//写出到硬盘的位置
		String sPath = ServletActionContext.getRequest().getRealPath("/") + "stat/chart/productsale";
		
		FileUtil fileUtil = new FileUtil();
		fileUtil.createTxt(sPath, "data.xml", sb.toString(), "utf-8");
		
		
		
		return "productsale";
	}
	
	/**
	 * 
	 * [
                {
                    "country": "USA",        
                    "visits": 4025,
                    "color": "#FF0F00"
                },
                {
                    "product_no": "China",
                    "mycount": 1882,
                    "color": "#FF6600"
                }
        ]        
	 * 组装json格式数据的方法：
	 * 1 fastjson
	 * 2 Gson
	 * 3   手动拼接
	 * 4 jackson..
	 * 
	 * @return
	 * @throws Exception
	 */
	public String productsaleJson() throws Exception {
		String sql = "select * from (select product_no,sum(cnumber) mycount from contract_product_c group by product_no order by sum(cnumber) desc) where rownum<15";
		
		List<String> list = sqlDao.executeSQL(sql);
		
		String[] colors = {"#FF0F00","#FF6600","#FF9E01","#FCD202","#FF0F00","#FF6600","#FF9E01","#FCD202",
				"#FF0F00","#FF6600","#FF9E01","#FCD202","#FF0F00","#FF6600","#FF9E01","#FCD202"		
		};
		
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(int i=0;i<list.size();i++){
			sb.append("{");
			sb.append("'product_no':'"+list.get(i)+"',");
			sb.append("'mycount':"+list.get(++i)+",");
			sb.append("'color':'"+colors[i/2]+"'");
			sb.append("},");
		}
		//截取最后一个逗号
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		
		//放入值栈
		this.put("chartData", sb.toString());
		
		
		return "productsaleJson";
	}
	
	
	
	
	
	
	 /*[
      {
          "date": "2009-10-02",
          "value": 5
      },
      {
          "hour": "2009-10-03",
          "value": 15
      }*/
	  public String onlineInfoJson() throws Exception{
		  
		  String sql = "select a.a1,nvl(b.mycount,0) from online_info_t a "
		  		+ "left join (select to_char(login_time,'hh24') a1,count(*) mycount"
		  		+ " from login_log_p group by to_char(login_time,'hh24'))b "
		  		+ "on a.a1 = b.a1 order by a.a1 asc";
		  
		  List<String> list = sqlDao.executeSQL(sql);
		  
		  StringBuffer sb = new StringBuffer();
		  sb.append("[");
		  for(int i=0;i<list.size();i++){
			  sb.append("{");
			  sb.append("'hour':'"+list.get(i)+"',");
			  sb.append("'value':"+list.get(++i));
			  sb.append("},");
		  }
		//截取最后一个
		  sb.deleteCharAt(sb.length()-1);
		  sb.append("]");
		  
		  //放入值栈
		  this.put("chartData", sb.toString());
		  
		  return "onlineInfoJson";
		  
	  }
	
	
	
	
	/**
	 * [{
            name: 'Tokyo',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }]
	 * @return
	 * @throws Exception
	 */
	public String onlineInfoHcharts() throws Exception {
		
		String sql = "select a.a1,nvl(b.mycount,0) from online_info_t a "
		  		+ "left join (select to_char(login_time,'hh24') a1,count(*) mycount"
		  		+ " from login_log_p group by to_char(login_time,'hh24'))b "
		  		+ "on a.a1 = b.a1 order by a.a1 asc";
		  
		List<String> list = sqlDao.executeSQL(sql);
		StringBuffer sb = new StringBuffer();
		sb.append("[{");
		sb.append("'name':'人数',");
		sb.append("'data':");
		sb.append("[");
		for(int i=1;i<list.size();i+=2){
			sb.append(list.get(i)+",");
		}
		//去掉最后一个
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		sb.append("}]");
		//将数据放入值栈
		this.put("chartData", sb.toString());
		
		return "onlineInfoHcharts";
	}
	
	
	public String echarts() throws Exception {
		// TODO Auto-generated method stub
		return "echarts";
	}
	
	
	
	
	
	
	
}
