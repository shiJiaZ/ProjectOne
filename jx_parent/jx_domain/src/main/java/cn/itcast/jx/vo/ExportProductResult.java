package cn.itcast.jx.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="exportProduct")
public class ExportProductResult {

	private String exportProductId;
	private Double tax;

	public String getExportProductId() {
		return exportProductId;
	}

	public void setExportProductId(String exportProductId) {
		this.exportProductId = exportProductId;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

}
