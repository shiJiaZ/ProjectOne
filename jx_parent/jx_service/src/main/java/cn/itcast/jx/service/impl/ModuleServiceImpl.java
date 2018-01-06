package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.jx.common.SysConstant;
import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.Module;
import cn.itcast.jx.service.ModuleService;
import cn.itcast.jx.util.Page;

public class ModuleServiceImpl implements ModuleService {
	
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}



	@Override
	public Page<Module> findPage(String hql, Page<Module> page,
			Class<Module> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}



	@Override
	public Module get(Class<Module> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}



	@Override
	public List<Module> find(String hql, Class<Module> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}



	@Override
	public void saveOrUpdate(Module entity) {
		if(StringUtils.isBlank(entity.getId())){
			
			
			baseDao.saveOrUpdate(entity);
		}else{
			//修改:先查再改
			Module module = baseDao.get(Module.class, entity.getId());
			//给属性赋值
			module.setName(entity.getName());
			module.setRemark(entity.getRemark());
			
			baseDao.saveOrUpdate(module);
		}
		
	}



	@Override
	public void deleteById(Class<Module> entityClass, Serializable id) {
		if(baseDao.get(entityClass, id)!=null){
			baseDao.deleteById(entityClass, id);
		}
	}



	@Override
	public void delete(Class<Module> entityClass, Serializable[] ids) {
		for(Serializable id:ids){
			deleteById(entityClass, id);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
