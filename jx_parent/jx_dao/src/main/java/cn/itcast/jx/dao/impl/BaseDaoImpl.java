package cn.itcast.jx.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.util.Page;


/**
 * @Description:
 * @Author:		传智播客 java学院	传智.袁新奇
 * @Company:	http://java.itcast.cn
 * @CreateDate:	2014年10月31日
 */
public class BaseDaoImpl implements BaseDao{
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	//带条件查询
	//from Dept where state = ?                                       
	public <T> List<T> find(String hql, Class<T> entityClass, Object[] params) {
		Query query = this.getSession().createQuery(hql);
		//注入参数
		if(params!=null){
			for(int i=0;i<params.length;i++){
				query.setParameter(i, params[i]);
			}
		}
		
		return (List<T>)query.list();
	}
	
	//获取一条，根据主键id
	public <T> T get(Class<T> entityClass, Serializable id) {
		return (T) this.getSession().get(entityClass, id);
	}

	//分页查询，查询两次，一次查询总数，一次查询分页记录
	/**
	 * 这是baseDao，在这一层需要准备：总记录数、总页数、每页结果
	 * 
	 */
	public <T> Page<T> findPage(String hql, Page<T> page, Class<T> entityClass, Object[] params){
		//创建Query对象
		Query query = this.getSession().createQuery(hql);
		
		//设置参数--支持参数的分页查找功能
		if(params!=null){
			for(int i=0;i<params.length;i++){
				query.setParameter(i, params[i]);
			}
		}
		//查询
		int totalRecord = query.list().size();
		//将这个总记录数给page
		page.setTotalRecord(totalRecord);
		
		//分页查找 limit ?,?
		//hibernate分页：设置起始索引和每页条数
		int fr = (page.getPageNo()-1)*page.getPageSize();
		query.setFirstResult(fr);//设置每页索引
		//设置每页条数
		query.setMaxResults(page.getPageSize());
		//查询结果
		page.setResults(query.list());
		
		return page;
	}
	
	//新增和修改，hibernate根据id是否为null自动判断
	public <T> void saveOrUpdate(T entity) {
		/**
		 * 当entity这个对象id=null----->新增
		 *             id!=null---->修改
		 */
		this.getSession().saveOrUpdate(entity);
	}
	
	//集合保存，这时新增还是修改，就自动判断，调用时是否简洁。适合批量新增和修改时。（Mrecord控件）
	public <T> void saveOrUpdateAll(Collection<T> entitys){
		for(T entity : entitys){
			this.saveOrUpdate(entity);//为什么hibernate批量操作时，要用循环一条一条记录去更新？
		}
	}

	//按主键id删除
	public <T> void deleteById(Class<T> entityClass, Serializable id) {
		this.getSession().delete(get(entityClass, id));
	}

	//批量删除
	public <T> void delete(Class<T> entityClass, Serializable[] ids) {
		for(Serializable s : ids){
			deleteById(entityClass, s);
		}
	}

}

