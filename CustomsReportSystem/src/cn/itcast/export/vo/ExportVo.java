package cn.itcast.export.vo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="export")
public class ExportVo {
	  	
	private String exportId;	
	
	private Date inputDate;			
	private String shipmentPort;			
	private String destinationPort;			
	private String transportMode;			//SEA/AIR
	private String priceCondition;			//FBO/CIF
	private Integer boxNums;			//冗余，为委托服务，一个报运的总箱数
	private Double grossWeights;			//冗余，为委托服务，一个报运的总毛重
	private Double measurements;			//冗余，为委托服务，一个报运的总体积
	
	
	private Set<ExportProductVo> products = new HashSet<ExportProductVo>();


	public String getExportId() {
		return exportId;
	}


	public void setExportId(String exportId) {
		this.exportId = exportId;
	}


	public Date getInputDate() {
		return inputDate;
	}


	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}


	public String getShipmentPort() {
		return shipmentPort;
	}


	public void setShipmentPort(String shipmentPort) {
		this.shipmentPort = shipmentPort;
	}


	public String getDestinationPort() {
		return destinationPort;
	}


	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}


	public String getTransportMode() {
		return transportMode;
	}


	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}


	public String getPriceCondition() {
		return priceCondition;
	}


	public void setPriceCondition(String priceCondition) {
		this.priceCondition = priceCondition;
	}


	public Integer getBoxNums() {
		return boxNums;
	}


	public void setBoxNums(Integer boxNums) {
		this.boxNums = boxNums;
	}


	public Double getGrossWeights() {
		return grossWeights;
	}


	public void setGrossWeights(Double grossWeights) {
		this.grossWeights = grossWeights;
	}


	public Double getMeasurements() {
		return measurements;
	}


	public void setMeasurements(Double measurements) {
		this.measurements = measurements;
	}


	public Set<ExportProductVo> getProducts() {
		return products;
	}


	public void setProducts(Set<ExportProductVo> products) {
		this.products = products;
	}
	
	
	
	
}
