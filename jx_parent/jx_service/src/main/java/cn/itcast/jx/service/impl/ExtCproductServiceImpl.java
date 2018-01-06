package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.Contract;
import cn.itcast.jx.domain.ExtCproduct;
import cn.itcast.jx.service.ExtCproductService;
import cn.itcast.jx.util.Page;
import cn.itcast.jx.util.UtilFuns;

public class ExtCproductServiceImpl implements ExtCproductService{
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<ExtCproduct> find(String hql, Class<ExtCproduct> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public ExtCproduct get(Class<ExtCproduct> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<ExtCproduct> findPage(String hql, Page<ExtCproduct> page,
			Class<ExtCproduct> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(ExtCproduct entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			Double amount = 0d;
			if(UtilFuns.isNotEmpty(entity.getCnumber())&&UtilFuns.isNotEmpty(entity.getPrice())){
				amount = entity.getCnumber() * entity.getPrice();
			}
			//查找购销合同
			Contract contract = baseDao.get(Contract.class, entity.getContractProduct().getContract().getId());
			//购销合同总金额 = 购销合同原总金额  + 附件总价
			contract.setTotalAmount(contract.getTotalAmount()+amount);
			//提交修改
			baseDao.saveOrUpdate(contract);
			
			//将附件总金额给附件
			entity.setAmount(amount);
			
			baseDao.saveOrUpdate(entity);
			
		}else{
			//修改
			ExtCproduct extCproduct = baseDao.get(ExtCproduct.class, entity.getId());
			//修改属性
			extCproduct.setFactory(entity.getFactory());
			extCproduct.setFactoryName(entity.getFactoryName());
			extCproduct.setProductNo(entity.getProductNo());
			extCproduct.setProductImage(entity.getProductImage());
			extCproduct.setCnumber(entity.getCnumber());
			extCproduct.setPackingUnit(entity.getPackingUnit());
			extCproduct.setPrice(entity.getPrice());
			extCproduct.setOrderNo(entity.getOrderNo());
			extCproduct.setProductDesc(entity.getProductDesc());
			extCproduct.setProductRequest(entity.getProductRequest());
			
			//修改价格
			Double amount = 0d;
			if(UtilFuns.isNotEmpty(entity.getCnumber())&&UtilFuns.isNotEmpty(entity.getPrice())){
				amount = entity.getCnumber() * entity.getPrice();
			}

			//修改购销合同的价格  = 购销合同原总金额 - 附件原总金额 + 附件新总金额
			Contract contract = extCproduct.getContractProduct().getContract();
			contract.setTotalAmount(contract.getTotalAmount()-extCproduct.getAmount()+amount);
			//提交修改
			baseDao.saveOrUpdate(contract);
			
			//将附件总金额给附件
			extCproduct.setAmount(amount);
			
			baseDao.saveOrUpdate(extCproduct);
		}
		
	}

	@Override
	public void saveOrUpdateAll(Collection<ExtCproduct> entitys) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Class<ExtCproduct> entityClass, Serializable id) {
		//修改购销合同的总金额
		//获取附件
		ExtCproduct extCproduct = baseDao.get(entityClass, id);
		//通过附件对象导航到购销合同
		Contract contract = extCproduct.getContractProduct().getContract();
		contract.setTotalAmount(contract.getTotalAmount()-extCproduct.getAmount());
		
		//提交修改
		baseDao.saveOrUpdate(contract);
		
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<ExtCproduct> entityClass, Serializable[] ids) {
		for(Serializable id:ids){
			deleteById(entityClass, id);
		}
	}

}
