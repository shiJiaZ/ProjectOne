package cn.itcast.jx.action.sysadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.Module;
import cn.itcast.jx.domain.Role;
import cn.itcast.jx.service.ModuleService;
import cn.itcast.jx.service.RoleService;
import cn.itcast.jx.util.Page;
import cn.itcast.jx.util.ResponseUtil;
import cn.itcast.jx.vo.ZtreeData;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ModelDriven;

public class RoleAction extends BaseAction implements ModelDriven<Role>{
	//模型驱动，一定要手动实例化
	private Role model = new Role();
	
	private JedisPool pool;
	
	public void setPool(JedisPool pool) {
		this.pool = pool;
	}

	@Override
	public Role getModel() {
		return model;
	}
	
	private RoleService roleService;
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}



	/**
	 * 增加一个list方法，在这个方法中，需要分页查找所有的部门信息
	 */
	public String list() throws Exception {
		//分页查找所有的部门信息
		roleService.findPage("from Role", page, Role.class, null);
		//设置分页请求的URL
		page.setUrl("roleAction_list");
		
//		HttpServletRequest servletRequest = ServletActionContext.getRequest();
		
		
		//将page放入值栈--root栈
		//ActionContext.getContext().getValueStack().push(page);
		this.push(page);
		
		return "list";
	}
	
	/**
	 * 实现什么业务？
	 * 如何获取页面勾选的id
	 * <input type="checkbox" name="id" value="100">
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toview() throws Exception {
		
		//根据id查找部门信息
		Role role = roleService.get(Role.class, model.getId());
		//role应该放入哪个栈
//		ActionContext.getContext().getValueStack().push(role);
		this.push(role);
		
		return "toview";
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String tocreate() throws Exception {
		
		return "tocreate";
	}
	
	/**
	 * 保存：
	 * 1 获取页面的父部门和部门名称
	 * 2 执行保存操作
	 * @return
	 * @throws Exception
	 */
	public String insert() throws Exception {
		
		//直接调用Service层执行保存操作
		roleService.saveOrUpdate(model);
		
		return SUCCESS;
	}
	
	/**
	 * 进入修改页面，需要准备的数据：
	 * @throws Exception
	 */
	public String toupdate() throws Exception {
		//查找要修改的角色信息
		Role role = roleService.get(Role.class, model.getId());
		//放入值栈
		this.push(role);
		
		return "toupdate";
	}

	
	
	
	
	
	public String update() throws Exception {
		roleService.saveOrUpdate(model);
		return SUCCESS;
	}
	
	
	
	
	/**
	 * 只要考虑单条删除的情况
	 * <input type="checkbox" name="id" value="100">
	 * <input type="checkbox" name="id" value="97f88a8c-90fc-4d52-aed7-2046f62fb498">
	 * <input type="checkbox" name="id" value="97f88a8c-90fc-4d52-aed7-2046f62fb497">
	 * <input type="checkbox" name="id" value="97f88a8c-90fc-4d52-aed7-2046f62fb496">
	 * 页面多个同名input文本框，后台可以怎么接收值？
	 * String  页面文本框的名字--->97f88a8c-90fc-4d52-aed7-2046f62fb498, 97f88a8c-90fc-4d52-aed7-2046f62fb497, 97f88a8c-90fc-4d52-aed7-2046f62fb496
	 * String[] 页面文本框的名字-->{'97f88a8c-90fc-4d52-aed7-2046f62fb498','97f88a8c-90fc-4d52-aed7-2046f62fb497','97f88a8c-90fc-4d52-aed7-2046f62fb496'}
	 * List<String> ...   -->.................
	 */
	public String delete() throws Exception {
		//单条删除
		//roleService.deleteById(Role.class, model.getId());
		//多条删除
		String[] ids = model.getId().split(", ");
		//调用service
		roleService.delete(Role.class, ids);
		
		return SUCCESS;
	}
	
	/**
	 * 进入模块分配分页
	 * 1 准备角色的信息
	 */
	public String tomodule() throws Exception {
		Role role = roleService.get(Role.class, model.getId());
		
		//放入值栈
		this.push(role);
		
		return "tomodule";
	}
	
	private ModuleService moduleService;
	
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	/**
	 * 组装模块
	 * { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
	 * 
	 * 组装json格式：
	 * 1 struts2-json-plugin：这种技术了解即可
	 * 2 fastjson-掌握即可
	 * 3   手动拼接
	 * @return
	 * @throws Exception
	 */
	public String roleModuleStr1() throws Exception {
		//1 获取所有的模块
		List<Module> moduelList = moduleService.find("from Module where state = 1", Module.class, null);
		//2 获取角色
		Role role = roleService.get(Role.class, model.getId());
		//3 获取角色已经拥有的模块
		Set<Module> modules = role.getModules();
		//4 组装json数据
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Module m:moduelList){
			sb.append("{");
			sb.append("'id':'"+m.getId()+"',");
			sb.append("'pId':'"+(m.getParent()==null?"0":m.getParent().getId())+"',");
			sb.append("'name':'"+m.getName()+"',");
			boolean flag = modules.contains(m);
			sb.append("'checked':'"+flag+"',");
			sb.append("'open':'true'");
			sb.append("},");
		}
		//截取最后一个逗号
		sb.deleteCharAt(sb.length()-1);
		
		sb.append("]");
		
		//传回前台
		ResponseUtil.writeContentToClient(sb.toString());
		
		return NONE;
	}
	/**
	 * fastjson的用法
	 * 
	 * @return
	 * @throws Exception
	 */
	public String roleModuleStr2() throws Exception {
		//1 获取所有的模块
		List<Module> moduelList = moduleService.find("from Module where state = 1", Module.class, null);
		//2 获取角色
		Role role = roleService.get(Role.class, model.getId());
		//3 获取角色已经拥有的模块
		Set<Module> modules = role.getModules();
		//4 组装json数据
		List<ZtreeData> list = new ArrayList<ZtreeData>();
		
		for(Module m:moduelList){
			String id = m.getId();
			String pId = m.getParent()==null?"0":m.getParent().getId();
			String name = m.getName();
			String checked = modules.contains(m)+"";
			String open = "true";
			
			list.add(new ZtreeData(id, pId, name, checked, open));
		}
		//转json
		String result = JSON.toJSONString(list);
		
		//传回前台
		ResponseUtil.writeContentToClient(result);
		
		return NONE;
	}
	
	/**
	 * fastjson转map集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public String roleModuleStr() throws Exception {
		/***先从redis中取数据***/
		Jedis jedis = pool.getResource();
		/**
		 * 如何准备这个key？可以防止与其他冲突？
		 * 
		 * 公有key：所有人，不管是谁，点击这个按钮，调用的key都是同一个key   "tree_json"+role_id  
		 * 私有key：只能被这个人共享
		 */
		String key = "tree_json"+model.getId();
		String json = jedis.get(key);
		
		if(StringUtils.isNotBlank(json)){
			ResponseUtil.writeContentToClient(json);
			return NONE;
		}
		
		//1 获取所有的模块
		List<Module> moduelList = moduleService.find("from Module where state = 1", Module.class, null);
		//2 获取角色
		Role role = roleService.get(Role.class, model.getId());
		//3 获取角色已经拥有的模块
		Set<Module> modules = role.getModules();
		//4 组装json数据
		
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		
		for(Module m:moduelList){
			String id = m.getId();
			String pId = m.getParent()==null?"0":m.getParent().getId();
			String name = m.getName();
			String checked = modules.contains(m)+"";
			String open = "true";
			
			Map<String , String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("pId", pId);
			map.put("name", name);
			map.put("checked", checked);
			map.put("open", open);
			
			list.add(map);
		}
		//转json
		String result = JSON.toJSONString(list);
		//会传到前台之前，还需保存数据
		jedis.set(key, result);
		
		
		//传回前台
		ResponseUtil.writeContentToClient(result);
		
		return NONE;
	}
	
	
	private String moduleIds;
	
	public String getModuleIds() {
		return moduleIds;
	}

	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}

	/**
	 * 保存，将更新后的角色对应的模块，保存进数据库
	 * 
	 * @return
	 * @throws Exception
	 */
	public String module() throws Exception {
		//1 分割字符串
		String[] ids = moduleIds.split(",");
		//2 准备新的模块集合
		Set<Module> modules = new HashSet<Module>();
		for(String id:ids){
			Module module = moduleService.get(Module.class, id);
			modules.add(module);
		}
		
		//3 整体替换
		//3.1 查找角色
		Role role = roleService.get(Role.class, model.getId());
		//3.2 整体替换集合
		role.setModules(modules);
		roleService.saveOrUpdate(role);
		
		//删除数据
		Jedis jedis = pool.getResource();
		String key = "tree_json"+model.getId();
		jedis.del(key);
		
		
		return SUCCESS;
	}
	
	
	public static void main(String[] args) {
		//fastjson根据json转对象
		ZtreeData data = new ZtreeData("1", "2", "tom", "true", "true");
		//转json
		String result = JSON.toJSONString(data);
		
		//将result转对象
		//第一个参数：json
		//第二个参数：接收对象的类型
		ZtreeData data2 = JSON.parseObject(result, ZtreeData.class);
		
	}
	
	
	
	

}
