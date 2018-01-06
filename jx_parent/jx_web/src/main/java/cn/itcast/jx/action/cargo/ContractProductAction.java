package cn.itcast.jx.action.cargo;

import java.util.List;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.ContractProduct;
import cn.itcast.jx.domain.Factory;
import cn.itcast.jx.service.ContractProductService;
import cn.itcast.jx.service.FactoryService;
import cn.itcast.jx.util.Page;

import com.opensymphony.xwork2.ModelDriven;

public class ContractProductAction extends BaseAction implements ModelDriven<ContractProduct>{
	//模型驱动，一定要手动实例化
	private ContractProduct model = new ContractProduct();
	
	@Override
	public ContractProduct getModel() {
		return model;
	}
	
	private ContractProductService contractProductService;
	
	public void setContractProductService(ContractProductService contractProductService) {
		this.contractProductService = contractProductService;
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
	 * tocreate方法：进入货物列表页面的方法，在这个页面有两个功能，新增页面+列表页面，所以需要准备的数据有：
	 * 1 所有的工厂信息：货物工厂 
	 * 2 分页查看当前购销合同对应的货物信息 
	 */
	public String tocreate() throws Exception {
		//1 查找所有的货物的工厂
		List<Factory> factoryList = factoryService.find("from Factory where ctype='货物' and state = 1", Factory.class, null);
		//2 分页查找购销合同对应的货物信息
		contractProductService.findPage("from ContractProduct where contract.id = ?", page,
					ContractProduct.class, new String[]{model.getContract().getId()});
		//设置分页的URL
		page.setUrl("contractProductAction_tocreate");
		
		//放入值栈
		this.put("factoryList", factoryList);
		this.push(page);
		
		return "tocreate";
	}
	
	/**
	 * 保存：注意点
	 * 冗余字段？增加货物
	 * 分散计算：一次集中计算的工作量分散到多次的计算中
	 * 1 ：更新购销合同的总金额
	 * @return
	 * @throws Exception
	 */
	public String insert() throws Exception {
		
		//直接调用Service层执行保存操作
		contractProductService.saveOrUpdate(model);
		
//		return SUCCESS;
		return tocreate();
	}
	
	/**
	 * 进入修改页面，需要准备的数据：
	 * 1 所有的货物工厂信息
	 * 2 当前货物的信息
	 * @throws Exception
	 */
	public String toupdate() throws Exception {
		//1 查找要修改的角色信息
		ContractProduct contractProduct = contractProductService.get(ContractProduct.class, model.getId());
		//放入值栈
		this.push(contractProduct);
		//2 准备货物工厂的信息
		List<Factory> factoryList = factoryService.find("from Factory where ctype='货物' and state = 1", Factory.class, null);
		//3 放入值栈
		this.put("factoryList", factoryList);
		
		return "toupdate";
	}

	
	
	
	
	/**
	 * 注意点:
	 * 1修改货物的时候，可能修改了货物的数量/单价，此时总价要重新计算
	 * 2 属性要更新 
	 */
	public String update() throws Exception {
		contractProductService.saveOrUpdate(model);
		return SUCCESS;
	}
	
	
	/**
	 * 根据页面特点，此处不支持多条删除，只允许单条删除
	 * 删除的注意点：
	 * 1 修改购销合同总金额 = 购销合同总金额  - 货物的总金额  -  附件金额
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		//单条删除
		contractProductService.deleteById(ContractProduct.class, model.getId());
			
		return SUCCESS;
	}
	
	
	

}
