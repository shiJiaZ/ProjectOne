package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.jx.common.SysConstant;
import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.Contract;
import cn.itcast.jx.domain.User;
import cn.itcast.jx.service.ContractService;
import cn.itcast.jx.util.Page;

public class ContractServiceImpl implements ContractService{
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Contract> find(String hql, Class<Contract> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Contract get(Class<Contract> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<Contract> findPage(String hql, Page<Contract> page,
			Class<Contract> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(Contract entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			//修改总金额和状态
			entity.setTotalAmount(0d);
			entity.setState(SysConstant.CAO_GAO);
			
			//新增购销合同的时候，给那5个字段赋值
			//创建 人：创建者的id
			//获取当前登录用户
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute(SysConstant.CURRENT_USER_INFO);
			entity.setCreateBy(user.getId());
			//创建部门：创建者所在部门的id
			entity.setCreateDept(user.getDept().getId());
			//创建时间
			entity.setCreateTime(new Timestamp(new Date().getTime()));
			
			
			baseDao.saveOrUpdate(entity);
			
		}else{
			//修改
			Contract contract = baseDao.get(Contract.class, entity.getId());
			
			//补充完整修改的属性
			
			
			
			
			baseDao.saveOrUpdate(contract);
		}
		
	}

	@Override
	public void saveOrUpdateAll(Collection<Contract> entitys) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Class<Contract> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<Contract> entityClass, Serializable[] ids) {
		for(Serializable id:ids){
			deleteById(entityClass, id);
		}
	}

}
