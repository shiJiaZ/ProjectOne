package cn.itcast.jx.service;


import java.io.Serializable;
import java.util.List;

import cn.itcast.jx.domain.Dept;
import cn.itcast.jx.util.Page;

public interface DeptService {
	
	public Page<Dept> findPage(String hql, Page<Dept> page, Class<Dept> entityClass, Object[] params);
	/**
	 * 根据id查找部门信息
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public Dept get(Class<Dept> entityClass, Serializable id);
	
	
	/**
	 * 查询所有，带条件查询
	 * @param hql
	 * @param entityClass
	 * @param params
	 * @return
	 */
	public List<Dept> find(String hql, Class<Dept> entityClass, Object[] params);
	
	
	/**
	 * 新增和修改保存
	 * @param entity
	 */
	public  void saveOrUpdate(Dept entity);
	
	
	
	/**
	 *单条删除，按id
	 *
	 */
	public void deleteById(Class<Dept> entityClass, Serializable id);
	
	//批量删除
	public  void delete(Class<Dept> entityClass, Serializable[] ids);
}
