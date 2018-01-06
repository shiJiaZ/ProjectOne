package cn.itcast.jx.domain;

import java.util.HashSet;
import java.util.Set;

public class Module extends BaseEntity{
	//主键id
	private String id;
	//父模块的编号:自关联
	private Module parent;
	//父模块的名字：冗余，减少查询数据库的次数
	private String parentName;
	//名字
	private String name;
	//层数：1 一级菜单， 2  左侧菜单  3 三级菜单     （可有可无）
	private Integer layerNum;
	//是否是叶子模块 （可有可无）
	private Integer isLeaf;
	//ico：图标 ，专业给三级菜单使用的
	private String ico;
	//权限标识  ， 通常情况下，与name一致
	private String cpermission;
	//请求路径
	private String curl;
	//菜单类型，与层数重复了  0 主菜单  1 左侧菜单  2 三级菜单
	private Integer ctype;
	//状态
	private Integer state;
	//从属于：（可有可无）
	private String belong;
	//cwhich：复用标识 （可有可无）
	private String cwhich;
	//引用次数
	private Integer quoteNum;
	//备注
	private String remark;
	//排序号
	private Integer orderNo;
	
	
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	//模块与角色  多对多
	private Set<Role> roles = new HashSet<Role>();
	
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Module getParent() {
		return parent;
	}
	public void setParent(Module parent) {
		this.parent = parent;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLayerNum() {
		return layerNum;
	}
	public void setLayerNum(Integer layerNum) {
		this.layerNum = layerNum;
	}
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public String getCpermission() {
		return cpermission;
	}
	public void setCpermission(String cpermission) {
		this.cpermission = cpermission;
	}
	public String getCurl() {
		return curl;
	}
	public void setCurl(String curl) {
		this.curl = curl;
	}
	public Integer getCtype() {
		return ctype;
	}
	public void setCtype(Integer ctype) {
		this.ctype = ctype;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	public String getCwhich() {
		return cwhich;
	}
	public void setCwhich(String cwhich) {
		this.cwhich = cwhich;
	}
	public Integer getQuoteNum() {
		return quoteNum;
	}
	public void setQuoteNum(Integer quoteNum) {
		this.quoteNum = quoteNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
}
