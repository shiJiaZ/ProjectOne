package cn.itcast.jx.service;


import java.io.Serializable;
import java.util.List;

import cn.itcast.jx.domain.Module;
import cn.itcast.jx.util.Page;

public interface ModuleService {
	
	public Page<Module> findPage(String hql, Page<Module> page, Class<Module> entityClass, Object[] params);
	/**
	 * 根据id查找部门信息
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public Module get(Class<Module> entityClass, Serializable id);
	
	
	/**
	 * 查询所有，带条件查询
	 * @param hql
	 * @param entityClass
	 * @param params
	 * @return
	 */
	public List<Module> find(String hql, Class<Module> entityClass, Object[] params);
	
	
	/**
	 * 新增和修改保存
	 * @param entity
	 */
	public  void saveOrUpdate(Module entity);
	
	
	
	/**
	 *单条删除，按id
	 *
	 */
	public void deleteById(Class<Module> entityClass, Serializable id);
	
	//批量删除
	public  void delete(Class<Module> entityClass, Serializable[] ids);
}
