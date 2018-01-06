package cn.itcast.jx.action.sysadmin;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.Dept;
import cn.itcast.jx.service.DeptService;
import cn.itcast.jx.util.Page;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class DeptAction extends BaseAction implements ModelDriven<Dept>{
	//模型驱动，一定要手动实例化
	private Dept model = new Dept();
	
	@Override
	public Dept getModel() {
		return model;
	}
	
	private DeptService deptService;
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	private String symbol = "dept";

	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * 增加一个list方法，在这个方法中，需要分页查找所有的部门信息
	 */
	public String list() throws Exception {
		//分页查找所有的部门信息
		deptService.findPage("from Dept", page, Dept.class, null);
		//设置分页请求的URL
		page.setUrl("deptAction_list");
		
//		HttpServletRequest servletRequest = ServletActionContext.getRequest();
		
		
		//将page放入值栈--root栈
		//ActionContext.getContext().getValueStack().push(page);
		this.push(page);
		
		return "list";
	}
	
	/**
	 * 实现什么业务？
	 * 如何获取页面勾选的id
	 * <input type="checkbox" name="id" value="100">
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toview() throws Exception {
		
		//根据id查找部门信息
		Dept dept = deptService.get(Dept.class, model.getId());
		//Dept dept1 = deptService.get(model.getClass(), model.getId());
		//dept应该放入哪个栈
//		ActionContext.getContext().getValueStack().push(dept);
		this.push(dept);
		
		return "toview";
	}
	
	/**
	 * 进入新增部门的页面
	 * 准备的数据：
	 * 1 所有的父部门--哪些部门可以作为父部门？    答：所有启用的部门
	 * 
	 * @return
	 * @throws Exception
	 */
	public String tocreate() throws Exception {
		//查找所有的启用的父部门
		List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);
		//放入值栈
		this.put("deptList", deptList);
		
		return "tocreate";
	}
	
	/**
	 * 保存：
	 * 1 获取页面的父部门和部门名称
	 * 2 执行保存操作
	 * @return
	 * @throws Exception
	 */
	public String insert() throws Exception {
		
		//直接调用Service层执行保存操作
		deptService.saveOrUpdate(model);
		
		return SUCCESS;
	}
	
	/**
	 * 进入修改页面，需要准备的数据：
	 * 1 所有的启用的部门信息
	 * 2 当前修改部门的信息
	 * @return
	 * @throws Exception
	 */
	public String toupdate() throws Exception {
		//1 所有启用的部门信息
		List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);
		//2 查找修改部门的信息
		Dept dept = deptService.get(Dept.class, model.getId());
		//放入值栈
		//解决自己不能是自己的父部门
		//deptList.remove(dept);
		//调用查找儿孙部门的方法
		findChildList(dept);
		//移除儿孙部门
		deptList.removeAll(childList);
		
		//方式一：命名的方式压入root栈
		ActionContext.getContext().getValueStack().set("deptList", deptList);
		//方式二：放入map栈
		//ActionContext.getContext().put("deptList", deptList);
		
		/**
		 * this代表当前类的实例，当子类继承了父类之后，子类没有重写父类的方法，this.方法  和  super.方法   访问的都是父类的方法
		 * 但是当子类重写了父类的方法之后，this.方法  访问的是子类的方法  ；super.方法访问的是父类的方法
		 */
		this.push(dept);
//		super.push(dept);
		
		return "toupdate";
	}
	
	
//	/查找出某个部门的儿孙部门
	private List<Dept> childList = new ArrayList<Dept>();
	
	public void findChildList(Dept parent){
		//查找当前父部门拥有的子部门
		List<Dept> list = deptService.find("from Dept where parent.id = ? and state = 1", 
				Dept.class, new String[]{parent.getId()});
		if(list.size()>0){
			//遍历,查找出每个子部门的下属部门
			for(Dept d:list){
				findChildList(d);
			}
		}
		//进入位置，表明已经没有子部门了
		childList.add(parent);
	}
	
	
	
	
	
	
	public String update() throws Exception {
		deptService.saveOrUpdate(model);
		return SUCCESS;
	}
	
	
	@SuppressWarnings("deprecation")
	public String deptNameExist() throws Exception {
		//对客户端的内容进行解码
		//String deptName = URLDecoder.decode(model.getDeptName());
		
		//根据部门名查找部门信息
		List<Dept> list = deptService.find("from Dept where deptName = ?", Dept.class, new String[]{model.getDeptName()});
		//默认数据库中不存在，可以添加
		String result = "false";
		if(list.size()>0){
			result="true";//数据库中存在，不能添加
		}
		//写到流中，输出到前台
		HttpServletResponse response = ServletActionContext.getResponse();
		response.getWriter().write(result);
		
		
		return NONE;
	}
	
	/**
	 * 只要考虑单条删除的情况
	 * <input type="checkbox" name="id" value="100">
	 * <input type="checkbox" name="id" value="97f88a8c-90fc-4d52-aed7-2046f62fb498">
	 * <input type="checkbox" name="id" value="97f88a8c-90fc-4d52-aed7-2046f62fb497">
	 * <input type="checkbox" name="id" value="97f88a8c-90fc-4d52-aed7-2046f62fb496">
	 * 页面多个同名input文本框，后台可以怎么接收值？
	 * String  页面文本框的名字--->97f88a8c-90fc-4d52-aed7-2046f62fb498, 97f88a8c-90fc-4d52-aed7-2046f62fb497, 97f88a8c-90fc-4d52-aed7-2046f62fb496
	 * String[] 页面文本框的名字-->{'97f88a8c-90fc-4d52-aed7-2046f62fb498','97f88a8c-90fc-4d52-aed7-2046f62fb497','97f88a8c-90fc-4d52-aed7-2046f62fb496'}
	 * List<String> ...   -->.................
	 */
	public String delete() throws Exception {
		//单条删除
		//deptService.deleteById(Dept.class, model.getId());
		//多条删除
		String[] ids = model.getId().split(", ");
		//调用service
		deptService.delete(Dept.class, ids);
		
		return SUCCESS;
	}
	
	
	
	

}
