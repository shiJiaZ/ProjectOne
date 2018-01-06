package cn.itcast.jx.action.cargo;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.Contract;
import cn.itcast.jx.domain.Export;
import cn.itcast.jx.domain.ExportProduct;
import cn.itcast.jx.service.ContractService;
import cn.itcast.jx.service.ExportProductService;
import cn.itcast.jx.service.ExportService;
import cn.itcast.jx.util.Page;
import cn.itcast.jx.util.UtilFuns;
import cn.itcast.jx.vo.ExportProductResult;
import cn.itcast.jx.vo.ExportProductVo;
import cn.itcast.jx.vo.ExportResult;
import cn.itcast.jx.vo.ExportVo;

import com.opensymphony.xwork2.ModelDriven;

public class ExportAction extends BaseAction implements ModelDriven<Export>{
	//模型驱动，一定要手动实例化
	private Export model = new Export();
	
//	private String contractIds;
	
	
	@Override
	public Export getModel() {
		return model;
	}
	
	private ExportService exportService;
	
	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}
	
	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	private ContractService contractService;
	
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}

	/**
	 * 查找所有已上报待报运的购销合同信息：分页查找
	 * @return
	 * @throws Exception
	 */
	public String contractList() throws Exception {
		// 查找以上报的购销合同信息
		contractService.findPage("from Contract where state = 1", page, Contract.class, null);
		// 设置分页URL
		page.setUrl("exportAction_contractList");
		//放入值栈
		this.push(page);
		
		return "contractList";
	}
	
	
	
	
	
	
	



	/**
	 * 增加一个list方法，在这个方法中，需要分页查找所有的部门信息
	 */
	public String list() throws Exception {
		//分页查找所有的部门信息
		exportService.findPage("from Export", page, Export.class, null);
		//设置分页请求的URL
		page.setUrl("exportAction_list");
		
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
		Export export = exportService.get(Export.class, model.getId());
		//export应该放入哪个栈
//		ActionContext.getContext().getValueStack().push(export);
		this.push(export);
		
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
		exportService.saveOrUpdate(model);
		
		return SUCCESS;
	}
	
	/**
	 * 进入修改页面，需要准备的数据：
	 * @throws Exception
	 */
	public String toupdate() throws Exception {
		//查找要修改的角色信息
		Export export = exportService.get(Export.class, model.getId());
		//放入值栈
		this.push(export);
		
		//准备报运货物的信息
		//addTRRecord("mRecordTable","id","productNo","cnumber","grossWeight","netWeight",
		//"sizeLength","sizeWidth","sizeHeight","exPrice","tax");
		//报运货物信息
		Set<ExportProduct> exportProducts = export.getExportProducts();
		StringBuffer sb = new StringBuffer();
		for(ExportProduct ep:exportProducts){
			sb.append("addTRRecord('mRecordTable',");
			sb.append("'"+ep.getId()+"',");
			sb.append("'"+ep.getProductNo()+"',");
			sb.append("'"+ep.getCnumber()+"',");
			sb.append("'"+(ep.getGrossWeight()==null?"":ep.getGrossWeight())+"',");
			sb.append("'"+UtilFuns.convertNull(ep.getNetWeight())+"',");
			sb.append("'"+UtilFuns.convertNull(ep.getSizeLength())+"',");
			sb.append("'"+UtilFuns.convertNull(ep.getSizeWidth())+"',");
			sb.append("'"+UtilFuns.convertNull(ep.getSizeHeight())+"',");
			sb.append("'"+UtilFuns.convertNull(ep.getExPrice())+"',");
			sb.append("'"+UtilFuns.convertNull(ep.getTax())+"');");
		}
		
		//放入值栈
		this.put("mRecordData", sb.toString());
		return "toupdate";
	}

	private String[] mr_id;
	private Integer[] mr_changed;
	private Integer[] mr_cnumber;
	private Double[] mr_grossWeight;
	private Double[] mr_netWeight;
	private Double[] mr_sizeLength;
	private Double[] mr_sizeWidth;
	private Double[] mr_sizeHeight;
	private Double[] mr_exPrice;
	private Double[] mr_tax;
	
	private ExportProductService exportProductService;
	
	public void setExportProductService(ExportProductService exportProductService) {
		this.exportProductService = exportProductService;
	}

	/**
	 * 更新属性和报运的货物的信息
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		//查询数据
		//获取报运单
		Export export = exportService.get(Export.class, model.getId());
//		/修改报运单的数据
		//把页面你的属性赋值给export这个持久化对象
		//报运号
		export.setCustomerContract(model.getCustomerContract());
		//制单日期
		export.setInputDate(model.getInputDate());
		//信用证号
		export.setLcno(model.getLcno());
		//收货人及地址
		export.setConsignee(model.getConsignee());
		//装运港
		export.setShipmentPort(model.getShipmentPort());
		//目的港
		export.setDestinationPort(model.getDestinationPort());
		//运输方式
		export.setTransportMode(model.getTransportMode());
		//价格条件
		export.setPriceCondition(model.getPriceCondition());
		//唛头
		export.setMarks(model.getMarks());
		//备注
		export.setRemark(model.getRemark());
		
		//修改货物的数据：准备货物的集合
		Set<ExportProduct> epSet = new HashSet<ExportProduct>();
		for(int i=0;i<mr_id.length;i++){
			//获取这个货物的持久态对象
			ExportProduct ep = exportProductService.get(ExportProduct.class, mr_id[i]);
			if(mr_changed[i]!=null&&mr_changed[i]==1){
				ep.setCnumber(mr_cnumber[i]);
				ep.setGrossWeight(mr_grossWeight[i]);
				ep.setNetWeight(mr_netWeight[i]);
				ep.setSizeLength(mr_sizeLength[i]);
				ep.setSizeWidth(mr_sizeWidth[i]);
				ep.setSizeHeight(mr_sizeHeight[i]);
				ep.setExPrice(mr_exPrice[i]);
				ep.setTax(mr_tax[i]);
			}
			epSet.add(ep);
		}
		//将epSet给export
		export.setExportProducts(epSet);
		
		exportService.saveOrUpdate(export);
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
		//exportService.deleteById(Export.class, model.getId());
		//多条删除
		String[] ids = model.getId().split(", ");
		//调用service
		exportService.delete(Export.class, ids);
		
		/**
		 * 删除需要完成的事情：
		 * 1 判断当前报运单是否已经装箱，如果已经装箱了， 提醒无法删除，
		 * 2 如果没有装箱，删除之后，还需要 修改购销合同的状态
		 */
		
		
		return SUCCESS;
	}
	/**
	 * 海关报运的方法
	 * 
	 * @return
	 * @throws Exception
	 */
	public String export() throws Exception {
		//1 组装数据
		//1.1 根据id查找要报运的报运单信息
		Export export = exportService.get(Export.class, model.getId());
		//1.2 将export中的数据给exportVo
		ExportVo exportVo = new ExportVo();
		//1.3 拷贝属性
		BeanUtils.copyProperties(export, exportVo);
		//1.4 手动设置id
		exportVo.setExportId(export.getId());
		//1.5 准备ExportProductVo的数据
		//1.5.1 报运单下货物的信息
		Set<ExportProduct> epSet = export.getExportProducts();
		//1.5.2 准备ExportProductVo信息
		Set<ExportProductVo> epVoSet = new HashSet<ExportProductVo>();
		//1.5.3 遍历epSet
		for(ExportProduct ep:epSet){
			//准备epVo
			ExportProductVo epVo = new ExportProductVo();
			//拷贝属性
			BeanUtils.copyProperties(ep, epVo);
			//设置id
			epVo.setExportProductId(ep.getId());
			//添加至集合
			epVoSet.add(epVo);
		}
		//1.6 将epVo给export
		exportVo.setProducts(epVoSet);
		
		//2 发送请求  +  接收数据 ;请求的URL是什么？
		ExportResult er = WebClient.create("http://localhost:8090/CustomsReportSystem/ws/export/exportE")
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)	
				.put(exportVo, ExportResult.class);
				
		//9 接收数据，已经在第二步完成
		//10 解析数据:修改  报运状态 和税收
		
		export.setState(er.getState());
		export.setRemark(er.getRemark());
		if(er.getState()==2){
			//获取所有的海关返回的报运货物信息
			Set<ExportProductResult> eprSet = er.getProducts();
			//遍历报运货物信息
			for(ExportProductResult epr:eprSet){
				//根据id获取本系统的货物信息
				ExportProduct ep = exportProductService.get(ExportProduct.class, epr.getExportProductId());
				//修改税收
				ep.setTax(epr.getTax());
				//提交修改
				exportProductService.saveOrUpdate(ep);
			}
		}
		//11 修改本地数据-报运单信息
		exportService.saveOrUpdate(export);
		
		return SUCCESS;
	}
	
	
	

	public String[] getMr_id() {
		return mr_id;
	}

	public void setMr_id(String[] mr_id) {
		this.mr_id = mr_id;
	}

	public Integer[] getMr_changed() {
		return mr_changed;
	}

	public void setMr_changed(Integer[] mr_changed) {
		this.mr_changed = mr_changed;
	}

	public Integer[] getMr_cnumber() {
		return mr_cnumber;
	}

	public void setMr_cnumber(Integer[] mr_cnumber) {
		this.mr_cnumber = mr_cnumber;
	}

	public Double[] getMr_grossWeight() {
		return mr_grossWeight;
	}

	public void setMr_grossWeight(Double[] mr_grossWeight) {
		this.mr_grossWeight = mr_grossWeight;
	}

	public Double[] getMr_netWeight() {
		return mr_netWeight;
	}

	public void setMr_netWeight(Double[] mr_netWeight) {
		this.mr_netWeight = mr_netWeight;
	}

	public Double[] getMr_sizeLength() {
		return mr_sizeLength;
	}

	public void setMr_sizeLength(Double[] mr_sizeLength) {
		this.mr_sizeLength = mr_sizeLength;
	}

	public Double[] getMr_sizeWidth() {
		return mr_sizeWidth;
	}

	public void setMr_sizeWidth(Double[] mr_sizeWidth) {
		this.mr_sizeWidth = mr_sizeWidth;
	}

	public Double[] getMr_sizeHeight() {
		return mr_sizeHeight;
	}

	public void setMr_sizeHeight(Double[] mr_sizeHeight) {
		this.mr_sizeHeight = mr_sizeHeight;
	}

	public Double[] getMr_exPrice() {
		return mr_exPrice;
	}

	public void setMr_exPrice(Double[] mr_exPrice) {
		this.mr_exPrice = mr_exPrice;
	}

	public Double[] getMr_tax() {
		return mr_tax;
	}

	public void setMr_tax(Double[] mr_tax) {
		this.mr_tax = mr_tax;
	}
	


}
