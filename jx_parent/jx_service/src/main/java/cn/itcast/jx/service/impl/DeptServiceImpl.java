package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.jx.common.SysConstant;
import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.Dept;
import cn.itcast.jx.service.DeptService;
import cn.itcast.jx.util.Page;

public class DeptServiceImpl implements DeptService {
	
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}



	@Override
	public Page<Dept> findPage(String hql, Page<Dept> page,
			Class<Dept> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}



	@Override
	public Dept get(Class<Dept> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}



	@Override
	public List<Dept> find(String hql, Class<Dept> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}



	@Override
	public void saveOrUpdate(Dept entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			entity.setState(SysConstant.ENABLED);
			
			baseDao.saveOrUpdate(entity);
		}else{
			//修改:先查再改
			Dept dept = baseDao.get(Dept.class, entity.getId());
			dept.setParent(entity.getParent());
			dept.setDeptName(entity.getDeptName());
			dept.setState(entity.getState());
			
			baseDao.saveOrUpdate(dept);
		}
		
	}



	@Override
	public void deleteById(Class<Dept> entityClass, Serializable id) {
		//递归删除父子部门
		List<Dept> list = baseDao.find("from Dept where parent.id = ?", entityClass, new Serializable[]{id});
		
		if(list.size()>0){
			for(Dept d:list){
				//递归
				deleteById(entityClass, d.getId());
			}
		}
		//进入此处，表明没有子部门了，可以直接删除--先查再删
		/*if(baseDao.get(entityClass, id)!=null){
			baseDao.deleteById(entityClass, id);
		}*/
		
		//假删除  --- 修改状态
		Dept dept = baseDao.get(entityClass, id);
		if(dept!=null){
			dept.setState(SysConstant.DISABLED);
			baseDao.saveOrUpdate(dept);//这句代码必须要写吗？答：不是，因为我们是先查再改，可以使用hibernate的快照更新
		}
		
	}



	@Override
	public void delete(Class<Dept> entityClass, Serializable[] ids) {
		for(Serializable id:ids){
			deleteById(entityClass, id);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
