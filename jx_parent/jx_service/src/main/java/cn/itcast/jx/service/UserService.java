package cn.itcast.jx.service;


import java.io.Serializable;
import java.util.List;

import cn.itcast.jx.domain.User;
import cn.itcast.jx.util.Page;

public interface UserService {
	
	public Page<User> findPage(String hql, Page<User> page, Class<User> entityClass, Object[] params);
	/**
	 * 根据id查找部门信息
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public User get(Class<User> entityClass, Serializable id);
	
	
	/**
	 * 查询所有，带条件查询
	 * @param hql
	 * @param entityClass
	 * @param params
	 * @return
	 */
	public List<User> find(String hql, Class<User> entityClass, Object[] params);
	
	
	/**
	 * 新增和修改保存
	 * @param entity
	 */
	public  void saveOrUpdate(User entity);
	
	
	
	/**
	 *单条删除，按id
	 *
	 */
	public void deleteById(Class<User> entityClass, Serializable id);
	
	//批量删除
	public  void delete(Class<User> entityClass, Serializable[] ids);
	public User findUserByUsername(String username);
}
