package cn.itcast.jx.service;


import java.io.Serializable;
import java.util.List;

import cn.itcast.jx.domain.Role;
import cn.itcast.jx.util.Page;

public interface RoleService {
	
	public Page<Role> findPage(String hql, Page<Role> page, Class<Role> entityClass, Object[] params);
	/**
	 * 根据id查找部门信息
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public Role get(Class<Role> entityClass, Serializable id);
	
	
	/**
	 * 查询所有，带条件查询
	 * @param hql
	 * @param entityClass
	 * @param params
	 * @return
	 */
	public List<Role> find(String hql, Class<Role> entityClass, Object[] params);
	
	
	/**
	 * 新增和修改保存
	 * @param entity
	 */
	public  void saveOrUpdate(Role entity);
	
	
	
	/**
	 *单条删除，按id
	 *
	 */
	public void deleteById(Class<Role> entityClass, Serializable id);
	
	//批量删除
	public  void delete(Class<Role> entityClass, Serializable[] ids);
}
