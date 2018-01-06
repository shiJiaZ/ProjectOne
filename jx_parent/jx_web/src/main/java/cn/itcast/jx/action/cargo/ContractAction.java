package cn.itcast.jx.action.cargo;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.common.SysConstant;
import cn.itcast.jx.domain.Contract;
import cn.itcast.jx.domain.Dept;
import cn.itcast.jx.domain.User;
import cn.itcast.jx.service.ContractService;
import cn.itcast.jx.service.DeptService;
import cn.itcast.jx.util.Page;

import com.opensymphony.xwork2.ModelDriven;

public class ContractAction extends BaseAction implements ModelDriven<Contract>{
	//模型驱动，一定要手动实例化
	private Contract model = new Contract();
	
	@Override
	public Contract getModel() {
		return model;
	}
	
	private ContractService contractService;
	
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	
	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}



	/**
	 * 增加一个list方法，在这个方法中，需要分页查找所有的部门信息
	 * 
	 * <input type="radio" name="userInfo.degree" value="0" class="input"/>超级管理员,总裁
	   <input type="radio" name="userInfo.degree" value="1" class="input"/>跨部门跨人员
	   <input type="radio" name="userInfo.degree" value="2" class="input"/>管理所有下属部门和人员
	   <input type="radio" name="userInfo.degree" value="3" class="input"/>管理本部门
	   <input type="radio" name="userInfo.degree" value="4" class="input"/>普通员工
	 */
	public String list() throws Exception {
		String hql = "from Contract where 1=1 ";
		//判断权限
		//此处获取当前登录用户 
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		//判断
		if(user.getUserInfo().getDegree()==4){
			//普通员工
			hql+="and createBy = '"+user.getId()+"'";
		}else if(user.getUserInfo().getDegree()==3){
			//部门经理
			hql+="and createDept = '"+user.getDept().getId()+"'";
		}else if(user.getUserInfo().getDegree()==2){
			//副总：大部门经理，管理本部门和下属部门
			findChildDepts(user.getDept().getId());//此处管理的是自己所在的部门和下属部门
			//deptIdList中就有值了      in （'x','y','z','a'） 
			String str = "";
			for(String id:deptIdList){
				str+="'"+id+"',";
			}
			//截取最后一个逗号
			str = str.substring(0, str.length()-1);
			hql+="and createDept in("+str+")";
			
			
		}else if(user.getUserInfo().getDegree()==1){
			//副总：跨部门跨人员
			
			
		}else{
			//总裁
			
		}
		
		
		
		
		//分页查找所有的部门信息
		contractService.findPage(hql, page, Contract.class, null);
		//设置分页请求的URL
		page.setUrl("contractAction_list");
		
//		HttpServletRequest servletRequest = ServletActionContext.getRequest();
		
		
		//将page放入值栈--root栈
		//ActionContext.getContext().getValueStack().push(page);
		this.push(page);
		
		return "list";
	}
	
	private DeptService deptService;
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	/**
	 * 查找该部门的所有下属部门:此处必用递归
	 */
	private List<String> deptIdList = new ArrayList<String>();
	
	public void findChildDepts(String parentId){
		
		List<Dept> childList = deptService.find("from Dept where parent.id = ?", Dept.class, new String[]{parentId});
		if(childList.size()>0){
			for(Dept d:childList){
				//递归
				findChildDepts(d.getId());
			}
		}
		//进入这一行，表明没有子部门了
		deptIdList.add(parentId);
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
		Contract contract = contractService.get(Contract.class, model.getId());
		//contract应该放入哪个栈
//		ActionContext.getContext().getValueStack().push(contract);
		//在放入值栈之前将\r\n替换成<br>
//		contract.setCrequest(contract.getCrequest().replaceAll("\r\n", "<br>"));//错误
//		contract.setCrequest(contract.getCrequest().replaceAll("\\r\\n", "<br>"));//错误
//		contract.setCrequest(contract.getCrequest().replaceAll("\\\\r\\\\n", "<br>"));//ok
//		contract.setCrequest(contract.getCrequest().replace("\\r\\n", "<br>"));//ok
		
		this.push(contract);
		
		return "toview";
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String tocreate() throws Exception {
		
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
		contractService.saveOrUpdate(model);
		
		return SUCCESS;
	}
	
	/**
	 * 进入修改页面，需要准备的数据：
	 * @throws Exception
	 */
	public String toupdate() throws Exception {
		//查找要修改的角色信息
		Contract contract = contractService.get(Contract.class, model.getId());
		//放入值栈
		this.push(contract);
		
		return "toupdate";
	}

	
	
	
	
	
	public String update() throws Exception {
		contractService.saveOrUpdate(model);
		return SUCCESS;
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
		//contractService.deleteById(Contract.class, model.getId());
		//多条删除
		String[] ids = model.getId().split(", ");
		//调用service
		contractService.delete(Contract.class, ids);
		
		return SUCCESS;
	}
	
	
	public String submit() throws Exception {
		
		String[] ids = model.getId().split(", ");
		
		for(String id:ids){
			Contract contract = contractService.get(Contract.class, id);
			contract.setState(SysConstant.YI_SHANG_BAO);
			contractService.saveOrUpdate(contract);
		}
		
		return SUCCESS;
	}
	
	
	public String cancel() throws Exception {
		
		String[] ids = model.getId().split(", ");
		
		for(String id:ids){
			Contract contract = contractService.get(Contract.class, id);
			contract.setState(SysConstant.CAO_GAO);
			contractService.saveOrUpdate(contract);
		}
		
		return SUCCESS;
	}

}
