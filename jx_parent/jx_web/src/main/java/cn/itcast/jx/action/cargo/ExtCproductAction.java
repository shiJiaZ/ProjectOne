package cn.itcast.jx.action.cargo;

import java.util.List;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.ExtCproduct;
import cn.itcast.jx.domain.Factory;
import cn.itcast.jx.service.ExtCproductService;
import cn.itcast.jx.service.FactoryService;
import cn.itcast.jx.util.Page;

import com.opensymphony.xwork2.ModelDriven;

public class ExtCproductAction extends BaseAction implements ModelDriven<ExtCproduct>{
	//模型驱动，一定要手动实例化
	private ExtCproduct model = new ExtCproduct();
	
	@Override
	public ExtCproduct getModel() {
		return model;
	}
	
	private ExtCproductService extCproductService;
	
	public void setExtCproductService(ExtCproductService extCproductService) {
		this.extCproductService = extCproductService;
	}
	
	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	private FactoryService factoryService;

	public void setFactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}

	/**
	 *  进入附件列表页面+新增页面
	 *  1 准备附件工厂
	 *  2 准备附件列表
	 *  
	 */
	public String tocreate() throws Exception {
		//1 查找附件工厂 
		List<Factory> factoryList = factoryService.find("from Factory where ctype='附件' and state = 1", Factory.class, null);
		//2 准备附件列表--所属货物
		extCproductService.findPage("from ExtCproduct where contractProduct.id = ?", page, 
				ExtCproduct.class, new String[]{model.getContractProduct().getId()});
		//3 设置URL
		page.setUrl("extCproductAction_tocreate");
		//4 放入值栈
		this.put("factoryList", factoryList);
		this.push(page);
		
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
		extCproductService.saveOrUpdate(model);
		return SUCCESS;
	}
	
	
	
	
	
	/**
	 * 进入修改页面，需要准备的数据：
	 * 1 所有启用附件工厂信息
	 * 2 附件本身的信息
	 * @throws Exception
	 */
	public String toupdate() throws Exception {
		//1 查找要修改的附件信息
		ExtCproduct extCproduct = extCproductService.get(ExtCproduct.class, model.getId());
		//放入值栈
		this.push(extCproduct);
		//2 查找附件工厂信息
		List<Factory> factoryList = factoryService.find("from Factory where ctype='附件' and state = 1", Factory.class, null);
		//3 放入值栈
		this.put("factoryList", factoryList);
		
		
		return "toupdate";
	}

	
	/**
	 * 1 修改属性
	 * 2 修改价格
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		extCproductService.saveOrUpdate(model);
		return SUCCESS;
	}
	
	
	
	
	
	public String delete() throws Exception {
		//单条删除
		extCproductService.deleteById(ExtCproduct.class, model.getId());
		
		
		return SUCCESS;
	}
	
	
}
