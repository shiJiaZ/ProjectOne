package cn.itcast.jx.action.cargo;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.ContractProduct;
import cn.itcast.jx.service.ContractProductService;
import cn.itcast.jx.util.DownloadUtil;
import cn.itcast.jx.util.UtilFuns;

public class PDFAction extends BaseAction{
	private String inputDate;

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	
	private ContractProductService contractProductService;
	
	public void setContractProductService(
			ContractProductService contractProductService) {
		this.contractProductService = contractProductService;
	}

	public String print() throws Exception {
		
		//创建文档
		Document document = new Document(PageSize.A3, 10, 10, 10, 10);
		
		//设置输出流
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, byteArrayOutputStream);
		//打开文档
		document.open();
		//写入内容
		BaseFont baseFont = BaseFont.createFont(AsianFontMapper.ChineseSimplifiedFont,
				AsianFontMapper.ChineseSimplifiedEncoding_H, BaseFont.NOT_EMBEDDED);
		/***大标题***/
		Font bigTitleFont = new Font(baseFont, 20, Font.BOLD, BaseColor.BLACK);
		//大标题
		Paragraph bigTitleParagraph = new Paragraph("出货表",bigTitleFont);
//		/设置对齐方式
		bigTitleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
		//添加至文档中
		document.add(bigTitleParagraph);
		
		/***内容***/
		String hql = "from ContractProduct where to_char(contract.shipTime,'yyyy-mm')='"+inputDate+"'";
		//查找货物的数据
		List<ContractProduct> cpList = contractProductService.find(hql, ContractProduct.class, null);
		//创建表格和表格需要的字体,表头信息
		Font contentFont = new Font(baseFont, 12, Font.NORMAL, BaseColor.BLACK);
		
		PdfPTable table = new PdfPTable(8);
		
		//客人	订单号	货号	数量	工厂	工厂交期	船期	贸易条款
		table.addCell(new PdfPCell(new Phrase("客人", contentFont)));
		table.addCell(new PdfPCell(new Phrase("订单号", contentFont)));
		table.addCell(new PdfPCell(new Phrase("货号", contentFont)));
		table.addCell(new PdfPCell(new Phrase("数量", contentFont)));
		table.addCell(new PdfPCell(new Phrase("工厂", contentFont)));
		table.addCell(new PdfPCell(new Phrase("工厂交期", contentFont)));
		table.addCell(new PdfPCell(new Phrase("船期", contentFont)));
		table.addCell(new PdfPCell(new Phrase("贸易条款", contentFont)));
		
		for(ContractProduct cp:cpList){
			//客人	订单号	货号	数量	工厂	工厂交期	船期	贸易条款
			table.addCell(new PdfPCell(new Phrase(cp.getContract().getCustomName(), contentFont)));
			table.addCell(new PdfPCell(new Phrase(cp.getContract().getContractNo(), contentFont)));
			table.addCell(new PdfPCell(new Phrase(cp.getProductNo(), contentFont)));
			table.addCell(new PdfPCell(new Phrase(cp.getCnumber()+"", contentFont)));
			table.addCell(new PdfPCell(new Phrase(cp.getFactoryName(), contentFont)));
			table.addCell(new PdfPCell(new Phrase(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()), contentFont)));
			table.addCell(new PdfPCell(new Phrase(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()), contentFont)));
			table.addCell(new PdfPCell(new Phrase(cp.getContract().getTradeTerms(), contentFont)));
		}
		
		//将table添加至文档中
		document.add(table);
		
		//关闭文档
		document.close();
//		/下载
		DownloadUtil downloadUtil = new DownloadUtil();
		downloadUtil.download(byteArrayOutputStream, ServletActionContext.getResponse(), "出货表.pdf");
		
		return NONE;
	}
	
	
	
}
