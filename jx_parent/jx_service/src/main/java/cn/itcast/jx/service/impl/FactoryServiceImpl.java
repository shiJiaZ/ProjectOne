package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.Factory;
import cn.itcast.jx.service.FactoryService;
import cn.itcast.jx.util.Page;

public class FactoryServiceImpl implements FactoryService{
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Factory> find(String hql, Class<Factory> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Factory get(Class<Factory> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<Factory> findPage(String hql, Page<Factory> page,
			Class<Factory> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(Factory entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			baseDao.saveOrUpdate(entity);
			
		}else{
			//修改
			Factory factory = baseDao.get(Factory.class, entity.getId());
			baseDao.saveOrUpdate(factory);
		}
		
	}

	@Override
	public void saveOrUpdateAll(Collection<Factory> entitys) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Class<Factory> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<Factory> entityClass, Serializable[] ids) {
		for(Serializable id:ids){
			deleteById(entityClass, id);
		}
	}

}
