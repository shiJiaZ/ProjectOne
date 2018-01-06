package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.Contract;
import cn.itcast.jx.domain.ContractProduct;
import cn.itcast.jx.domain.ExtCproduct;
import cn.itcast.jx.service.ContractProductService;
import cn.itcast.jx.util.Page;
import cn.itcast.jx.util.UtilFuns;

public class ContractProductServiceImpl implements ContractProductService{
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<ContractProduct> find(String hql, Class<ContractProduct> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public ContractProduct get(Class<ContractProduct> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<ContractProduct> findPage(String hql, Page<ContractProduct> page,
			Class<ContractProduct> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(ContractProduct entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			//计算总金额
			Double amount = 0d;
			//if(StringUtils.isNoneBlank(entity.getPrice()))
			if(UtilFuns.isNotEmpty(entity.getCnumber())&&UtilFuns.isNotEmpty(entity.getPrice())){
				amount = entity.getCnumber() * entity.getPrice();
			}
			
			//查找购销合同，修改购销合同总金额，只有持久态才可以对象导航
//			Contract contract = entity.getContract();//不可以的
			//通过dao去查找数据，获取持久态对象
			Contract contract = baseDao.get(Contract.class, entity.getContract().getId());
			//修改总金额    购销合同总金额 = 原总金额   +   当前货物的总价
			contract.setTotalAmount(contract.getTotalAmount()+amount);
			
			//提交修改
			baseDao.saveOrUpdate(contract);
			
			//将价格给货物
			entity.setAmount(amount);
			//新增货物
			baseDao.saveOrUpdate(entity);
			
		}else{
			//修改 : 持久态，可以对象导航
			ContractProduct contractProduct = baseDao.get(ContractProduct.class, entity.getId());
//			/修改属性
			//生产厂家
			contractProduct.setFactory(entity.getFactory());
			//厂家名字
			contractProduct.setFactoryName(entity.getFactoryName());
			//货号
			contractProduct.setProductNo(entity.getProductNo());
			//货物照片
			contractProduct.setProductImage(entity.getProductImage());
			//数量
			contractProduct.setCnumber(entity.getCnumber());
			//包装单位
			contractProduct.setPackingUnit(entity.getPackingUnit());
			//装率
			contractProduct.setLoadingRate(entity.getLoadingRate());
			//箱数
			contractProduct.setBoxNum(entity.getBoxNum());
			//单价
			contractProduct.setPrice(entity.getPrice());
			//排序号
			contractProduct.setOrderNo(entity.getOrderNo());
			//货物描述
			contractProduct.setProductDesc(entity.getProductDesc());
			//要求
			contractProduct.setProductRequest(entity.getProductRequest());
			
			/******计算总价 *******/
			Double amount = 0d;
			if(UtilFuns.isNotEmpty(entity.getCnumber())&&UtilFuns.isNotEmpty(entity.getPrice())){
				amount = entity.getCnumber() * entity.getPrice();
			}
			
			
			//获取购销合同
			Contract contract = contractProduct.getContract();
			
			//修改购销合同总金额 =  购销合同原总金额  - 货物原总金额  +  货物新总金额
			contract.setTotalAmount(contract.getTotalAmount()-contractProduct.getAmount()+amount);
			//修改数据ku
			baseDao.saveOrUpdate(contract);
			
			//修改货物的总金额
			contractProduct.setAmount(amount);
			
			baseDao.saveOrUpdate(contractProduct);
		}
		
	}

	@Override
	public void saveOrUpdateAll(Collection<ContractProduct> entitys) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Class<ContractProduct> entityClass, Serializable id) {
		//0 查找货物
		ContractProduct contractProduct = baseDao.get(entityClass, id);
		//1 查找购销合同
		Contract contract = contractProduct.getContract();
		//2 减去货物的价格
		contract.setTotalAmount(contract.getTotalAmount()-contractProduct.getAmount());
		//3 减去附件的价格
		Set<ExtCproduct> extCproducts = contractProduct.getExtCproducts();
		
		for(ExtCproduct extC:extCproducts){
			contract.setTotalAmount(contract.getTotalAmount()-extC.getAmount());
		}
		
		//提交至数据库修改
		baseDao.saveOrUpdate(contract);
		
		//删除货物
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<ContractProduct> entityClass, Serializable[] ids) {
		for(Serializable id:ids){
			deleteById(entityClass, id);
		}
	}

}
