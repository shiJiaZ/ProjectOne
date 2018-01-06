package cn.itcast.jx.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @Author:		传智播客 java学院	传智袁新奇
 * @Company:	http://java.itcast.cn
 * @CreateDate:	2015年1月8日
 */
public class Contract extends BaseEntity implements Serializable {
	private Set<ContractProduct> contractProducts = new HashSet<ContractProduct>();		//合同和货物，一对多
	
	private String id;
	private String offeror;			//收购方
	private String contractNo;		//合同号，订单号
	private Date signingDate;		//签单日期
	private String inputBy;			//制单人
	private String checkBy;			//审单人
	private String inspector;		//验货员:国外公司的代表
	private Double totalAmount;		//总金额=货物的总金额+附件的总金额    冗余字段，为了进行分散计算
	private String crequest;		//要求
	private String customName;		//客户名称
	private Date shipTime;			//船期--杰信公司的交货日期
	private Integer importNum;		//重要程度
	private Date deliveryPeriod;	//交货期限--与国内厂家签订的生产合同，是国内厂家的交货日期
	private Integer oldState;		//旧的状态，报运        生产阶段、已报运阶段 
	private Integer outState;		//出货状态， 
	private String tradeTerms;		//贸易条款   交易方式（FOB/CFR/CIF）+付款方式(LC/TT)
	private String printStyle;		//打印板式，1打印一个货物2打印两个货物
	private String remark;			//备注
	private Integer state;			//状态：0草稿 1已上报待报运
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOfferor() {
		return offeror;
	}
	public void setOfferor(String offeror) {
		this.offeror = offeror;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getSigningDate() {
		return signingDate;
	}
	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}
	public String getInputBy() {
		return inputBy;
	}
	public void setInputBy(String inputBy) {
		this.inputBy = inputBy;
	}
	public String getCheckBy() {
		return checkBy;
	}
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	public String getInspector() {
		return inspector;
	}
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getCrequest() {
		return crequest;
	}
	public void setCrequest(String crequest) {
		this.crequest = crequest;
	}
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	public Date getShipTime() {
		return shipTime;
	}
	public void setShipTime(Date shipTime) {
		this.shipTime = shipTime;
	}
	
	public Integer getImportNum() {
		return importNum;
	}
	public void setImportNum(Integer importNum) {
		this.importNum = importNum;
	}
	public Date getDeliveryPeriod() {
		return deliveryPeriod;
	}
	public void setDeliveryPeriod(Date deliveryPeriod) {
		this.deliveryPeriod = deliveryPeriod;
	}
	public Integer getOldState() {
		return oldState;
	}
	public void setOldState(Integer oldState) {
		this.oldState = oldState;
	}
	public Integer getOutState() {
		return outState;
	}
	public void setOutState(Integer outState) {
		this.outState = outState;
	}
	public String getTradeTerms() {
		return tradeTerms;
	}
	public void setTradeTerms(String tradeTerms) {
		this.tradeTerms = tradeTerms;
	}
	public String getPrintStyle() {
		return printStyle;
	}
	public void setPrintStyle(String printStyle) {
		this.printStyle = printStyle;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Set<ContractProduct> getContractProducts() {
		return contractProducts;
	}
	public void setContractProducts(Set<ContractProduct> contractProducts) {
		this.contractProducts = contractProducts;
	}
	
}
