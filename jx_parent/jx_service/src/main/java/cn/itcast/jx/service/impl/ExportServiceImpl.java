package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import cn.itcast.jx.common.SysConstant;
import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.Contract;
import cn.itcast.jx.domain.ContractProduct;
import cn.itcast.jx.domain.Export;
import cn.itcast.jx.domain.ExportProduct;
import cn.itcast.jx.domain.ExtCproduct;
import cn.itcast.jx.domain.ExtEproduct;
import cn.itcast.jx.service.ExportService;
import cn.itcast.jx.util.Page;
import cn.itcast.jx.util.UtilFuns;

public class ExportServiceImpl implements ExportService{
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Export> find(String hql, Class<Export> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Export get(Class<Export> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<Export> findPage(String hql, Page<Export> page,
			Class<Export> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(Export entity) {
		if(StringUtils.isBlank(entity.getId())){
			/**
			 * 新增 报运单的业务：
			 * 1 保存报运单信息
			 * 2 货物搬家
			 * 3 附件搬家
			 */
			//拼接合同和确认书号
			String[] ids = entity.getContractIds().split(", ");
			String customerContract = "";
			for(String id:ids){//拼接的是合同号
				Contract contract = baseDao.get(Contract.class, id);
				customerContract+=contract.getContractNo()+" ";
				//修改购销合同的状态--已报运
				contract.setState(SysConstant.YI_BAO_YUN);
				baseDao.saveOrUpdate(contract);
			}
			//将合同和确认书号给实体
			entity.setCustomerContract(customerContract);
			//设置报运单的状态  0 草稿  
			entity.setState(SysConstant.CAO_GAO);
			//设置报运单的录入时间
			entity.setInputDate(new Date());
			
			//如何查找货物？
			/*for(String id:ids){
				Contract contract = baseDao.get(Contract.class, id);
				contract.getContractProducts();
			}*/
			//采用跳跃查询，查找货物的信息
			//拼接in字符串
			String inIds = "";
			for(String id:ids){
				inIds = "'"+id+"',";
			}
			inIds = inIds.substring(0, inIds.length()-1);
			
//			String hql = "from ContractProduct where contract.id in ("+inIds+")";
			String hql = "from ContractProduct where contract.id in ("+UtilFuns.joinInStr(ids)+")";
			
			List<ContractProduct> cpList = baseDao.find(hql, ContractProduct.class, null);
			Set<ExportProduct> epSet = new HashSet<ExportProduct>();
			//epSet给entity
			entity.setExportProducts(epSet);//建立关联关系
			
			//遍历cpList
			for(ContractProduct cp:cpList){
				//创建ep
				ExportProduct ep = new ExportProduct();
				//复制属性
				//工厂，货号，包装单位，数量，单价
				ep.setFactory(cp.getFactory());
				ep.setProductNo(cp.getProductNo());
				ep.setPackingUnit(cp.getPackingUnit());
				ep.setCnumber(cp.getCnumber());
				ep.setPrice(cp.getPrice());
				
//				/设置关系
				epSet.add(ep);//集合中添加ep元素
				//ep.setExport(entity);//ep与export建立关系
				
				//搬附件
				Set<ExtCproduct> extCproducts = cp.getExtCproducts();
				Set<ExtEproduct> extEproducts = new HashSet<ExtEproduct>();
				//将extEproducts给ep
				ep.setExtEproducts(extEproducts);
				
				
				for(ExtCproduct extC:extCproducts){
					//创建报运附件
					ExtEproduct extE = new ExtEproduct();
					//复制属性
					//第一个参数：源
					//第二个参数：目标
					BeanUtils.copyProperties(extC, extE);
					//将extE中的id置空
					extE.setId(null);
					
					
					//设置关系-设置双向关联关系
					extEproducts.add(extE);
					///extE.setExportProduct(ep);//反向关系
				}
				
			}
			
			//保存entity的时候，级联保存附件和货物
			baseDao.saveOrUpdate(entity);
		}else{
//			修改
			Export export = baseDao.get(Export.class, entity.getId());
			
			
			baseDao.saveOrUpdate(export);
		}
		
	}

	@Override
	public void saveOrUpdateAll(Collection<Export> entitys) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Class<Export> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<Export> entityClass, Serializable[] ids) {
		for(Serializable id:ids){
			deleteById(entityClass, id);
		}
	}
	
}
