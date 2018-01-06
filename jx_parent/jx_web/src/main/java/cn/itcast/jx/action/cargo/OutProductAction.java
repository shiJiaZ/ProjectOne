package cn.itcast.jx.action.cargo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.Contract;
import cn.itcast.jx.domain.ContractProduct;
import cn.itcast.jx.service.ContractProductService;
import cn.itcast.jx.util.DownloadUtil;
import cn.itcast.jx.util.UtilFuns;

public class OutProductAction extends BaseAction{
	
	private String inputDate;
	
	
	public String getInputDate() {
		return inputDate;
	}


	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}


	public String toedit() throws Exception {
		return "toedit";
	}
	
	private ContractProductService contractProductService;
	
	public void setContractProductService(
			ContractProductService contractProductService) {
		this.contractProductService = contractProductService;
	}
	
	/**
	 * 使用模板打印
	 * 
	 */
	@SuppressWarnings({ "deprecation", "resource" })
	public String printWithTemplate() throws Exception {
		
		//1 读取模板
		//获取模板的路径
		//localhost:8088/jx_web/
		//jx_web/
		//E:\itheima18\envirment\apache-tomcat-7.0.69\webapps\jx_web\   正确答案！！！
		String path = ServletActionContext.getRequest().getRealPath("/");
		path+="make/xlsprint/tOUTPRODUCT.xlsx";
		
		//使用流 读取模板
		FileInputStream is = new FileInputStream(new File(path));
		//转换成wb对象
//		Workbook wb = new HSSFWorkbook(is);
		Workbook wb = new XSSFWorkbook(is);
		//2 获取sheet
		Sheet sheet = wb.getSheetAt(0);
		
		//定义公共变量
		int rowNo = 0;
		int cellNo = 1;
		Row nRow = null;
		Cell nCell = null;
		
		
		/********大标题*********/
		//3 获取行
		nRow = sheet.getRow(rowNo);
		//4 获取列
		nCell = nRow.getCell(cellNo);		
		//5 设置内容
		nCell.setCellValue(inputDate.replace("-0", "-").replace("-", "年")+"月份出货表");
		// 设置格式-不需要了，模板中有
		
		/********小标题********/
		rowNo++;
		
		/********正文*********/
		rowNo++;
		//保留格式 :客人	订单号	货号	数量	工厂	工厂交期	船期	贸易条款
		nRow = sheet.getRow(rowNo);
		CellStyle customNameCellStyle = nRow.getCell(cellNo++).getCellStyle();
		CellStyle contractNoCellStyle = nRow.getCell(cellNo++).getCellStyle();
		CellStyle productNoCellStyle = nRow.getCell(cellNo++).getCellStyle();
		CellStyle cnumberCellStyle = nRow.getCell(cellNo++).getCellStyle();
		CellStyle factoryNameCellStyle = nRow.getCell(cellNo++).getCellStyle();
		CellStyle deliveryPeriodCellStyle = nRow.getCell(cellNo++).getCellStyle();
		CellStyle shipTimeCellStyle = nRow.getCell(cellNo++).getCellStyle();
		CellStyle tradeTermsCellStyle = nRow.getCell(cellNo++).getCellStyle();
		
		String hql = "from ContractProduct where to_char(contract.shipTime,'yyyy-mm')='"+inputDate+"'";
		//查找货物的数据
		List<ContractProduct> cpList = contractProductService.find(hql, ContractProduct.class, null);
		
		
		
		for(int i=0;i<1000;i++)
		for(ContractProduct cp:cpList){
			//cellNo归1
			cellNo = 1;
			nRow = sheet.createRow(rowNo++);
			nRow.setHeightInPoints(24f);
			
			//客人:直接创建行，覆盖原有的行	
			nCell = nRow.createCell(cellNo++);
			Contract contract = cp.getContract();
			String name = contract.getCustomName();
//			
			nCell.setCellValue(cp.getContract().getCustomName()+"");
			nCell.setCellStyle(customNameCellStyle);
			//订单号	
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getContract().getContractNo());
			nCell.setCellStyle(contractNoCellStyle);
			//货号	
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getProductNo());
			nCell.setCellStyle(productNoCellStyle);
			//数量	
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getCnumber());
			nCell.setCellStyle(cnumberCellStyle);
			//工厂	
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getFactoryName());
			nCell.setCellStyle(factoryNameCellStyle);
			//工厂交期	
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));
			nCell.setCellStyle(deliveryPeriodCellStyle);
			//船期	
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));
			nCell.setCellStyle(shipTimeCellStyle);
			//贸易条款
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getContract().getTradeTerms());
			nCell.setCellStyle(tradeTermsCellStyle);
		}
		
		//6 下载
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		wb.write(os);
		
		DownloadUtil downloadUtil = new DownloadUtil();
		downloadUtil.download(os, ServletActionContext.getResponse(), "出货表.xlsx");
		
		
		return NONE;
	}
	
	
	

	/**
	 * 不使用模板打印
	 * 导出xls结尾的表格
	 * 
	 */
	@SuppressWarnings("resource")
	public String print() throws Exception {
		//新建工作表
//		Workbook wb = new HSSFWorkbook();
		Workbook wb = new SXSSFWorkbook();
		//创建sheet
		Sheet sheet = wb.createSheet();
		//设置列宽
		sheet.setColumnWidth(0, 6*256);
		sheet.setColumnWidth(1, 26*256);
		sheet.setColumnWidth(2, 12*256);
		sheet.setColumnWidth(3, 30*256);
		sheet.setColumnWidth(4, 12*256);
		sheet.setColumnWidth(5, 15*256);
		sheet.setColumnWidth(6, 10*256);
		sheet.setColumnWidth(7, 10*256);
		sheet.setColumnWidth(8, 10*256);
		
		//定义一些公共变量
		int rowNo = 0;
		int cellNo = 1;
		Row nRow = null;
		Cell nCell = null;
		
		/***********1 大标题的打印***********/
		//创建行对象
		nRow = sheet.createRow(rowNo);
//		创建单元格对象
		nCell = nRow.createCell(cellNo);
//		设置内容
		// 2015-07  2015年7月份出货表
		//2015-11   2015年11月份出货表
		//inputDate.replace("-0","年").replace("-","年")
		//inputDate.replace("-0","-").replace("-","年")
		nCell.setCellValue(inputDate.replace("-0", "年").replace("-", "年")+"月份出货表");
		
//		设置格式
		//合并单元格
		/***
		 * CellRangeAddress：单元格范围
		 * 起始行，结束行，起始列，结束列
		 */
		CellRangeAddress cra=new CellRangeAddress(0, 0, 1, 8);
		sheet.addMergedRegion(cra);
		//设置行高
		nRow.setHeightInPoints(36f);
		nCell.setCellStyle(bigTitle(wb));
		
		
		/***********2 小标题的打印***********/
		//rowNo变化?
		rowNo++;
		//创建行
		nRow = sheet.createRow(rowNo);
		//设置行高
		nRow.setHeightInPoints(27f);
		
		String[] titles = {"客户","订单号","货号","数量	","工厂","工厂交期","船期","贸易条款"};
		for(String title:titles){
			//创建单元格
			nCell = nRow.createCell(cellNo++);
			//设置内容
			nCell.setCellValue(title);
			//设置格式
			nCell.setCellStyle(title(wb));
		}
		/***********3 内容的打印***********/
		//准备数据？--查找货物的信息
//		String hql = "from ContractProduct where contract.shipTime like '"+inputDate+"%'";//mysql支持，oracle不支持
		//to_char是oracle的函数，hql中可以使用oracle的函数吗？答：可以的！！！
		String hql = "from ContractProduct where to_char(contract.shipTime,'yyyy-mm')='"+inputDate+"'";
		//查找货物的数据
		List<ContractProduct> cpList = contractProductService.find(hql, ContractProduct.class, null);
		for(int i=0;i<6000;i++)//170000*6  102万
		for(ContractProduct cp:cpList){
			//rowNo需要变化吗
			rowNo++;
			cellNo=1;
			
			//创建行
			nRow = sheet.createRow(rowNo);
			//设置高度
			nRow.setHeightInPoints(24f);
			
			//"客户",
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getContract().getCustomName());
			nCell.setCellStyle(text(wb));
			//"订单号",
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getContract().getContractNo());
			nCell.setCellStyle(text(wb));
			//"货号",
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getProductNo());
			nCell.setCellStyle(text(wb));
			//"数量	
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getCnumber());
			nCell.setCellStyle(text(wb));
			//工厂",
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getFactoryName());
			nCell.setCellStyle(text(wb));
			//"工厂交期"
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(cp.getContract().getDeliveryPeriod()));
			nCell.setCellStyle(text(wb));
			//,"船期"
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));
			nCell.setCellStyle(text(wb));
			//"贸易条款"
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getContract().getTradeTerms());
			nCell.setCellStyle(text(wb));
			
		}
		
		
		
		/***********4 下载***********/
		DownloadUtil downloadUtil = new DownloadUtil();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		wb.write(byteArrayOutputStream);
		
		/**
		 * 第一个参数：下载的流内容 ：byteArrayOutputStream  字节数组流 ，不管什么类型的文件，都可以使用它下载
		 * 第二个参数：response
		 * 第三个参数：下载的名字
		 */
		downloadUtil.download(byteArrayOutputStream, response, inputDate.replace("-0", "年").replace("-", "年")+"月份出货表.xlsx");
		
		
		return NONE;
	}
	
	
	public CellStyle bigTitle(Workbook wb){
		CellStyle cellStyle = wb.createCellStyle();
		//字体+大小++加粗
		Font font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		//把font给cellStyle
		cellStyle.setFont(font);
		//横向和纵向居中
//		cellStyle.setAlignment(HorizontalAlignment.CENTER);//横向居中
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//纵向居中
		
		return cellStyle;
	}
	
	public CellStyle title(Workbook wb){
		CellStyle cellStyle = wb.createCellStyle();
		//黑体+12
		Font font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short)12);
		
		cellStyle.setFont(font);
		//横向和纵向居中
		/*cellStyle.setAlignment(HorizontalAlignment.CENTER);//横向居中
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//纵向居中
		//边框:上左下右的顺序------逆时针的顺序
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);*/
		
		return cellStyle;
	}
	
	public CellStyle text(Workbook wb){
		CellStyle cellStyle = wb.createCellStyle();
		//字体+大小+边框
		Font font = wb.createFont();
		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short)10);
		
		cellStyle.setFont(font);
		
		//边框
		/*cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);*/
		
		
		return cellStyle;
	}
	
	
	
}
