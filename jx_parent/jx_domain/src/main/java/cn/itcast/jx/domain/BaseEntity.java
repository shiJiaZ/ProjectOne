package cn.itcast.jx.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class BaseEntity implements Serializable{
	//创建者的id
	private String createBy;
	//创建者所在部门的id
	private String createDept;
	//创建时间
	private Timestamp createTime;
	//更新者的id
	private String updateBy;
	//更新时间
	private Timestamp updateTime;
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateDept() {
		return createDept;
	}
	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	

}
