package cn.itcast.jx.action.cargo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.Factory;
import cn.itcast.jx.service.FactoryService;
import cn.itcast.jx.util.DownloadUtil;


public class POIAction extends BaseAction{
	
	/**
	 * 进入导入的页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toImport() throws Exception {
		// TODO Auto-generated method stub
		return "toImport";
	}
	
	@SuppressWarnings({ "deprecation", "resource" })
	public String download() throws Exception {
		//读取tomcat的路径，获取工厂模板.xls
		String path = ServletActionContext.getRequest().getRealPath("/");
		path+="make/xlsprint/tFactory.xls";
		
		FileInputStream is = new FileInputStream(new File(path));
		
		/*****方案一：使用输入流读取模板，再写进输出流中****/
		/*ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		byte[] array = new byte[(int) new File(path).length()];
		
		is.read(array);
		os.write(array);*/
		
		/***方案二：***/
		//创建wb
		Workbook wb = new HSSFWorkbook(is);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		wb.write(os);
		
		DownloadUtil downloadUtil = new DownloadUtil();
		downloadUtil.download(os, ServletActionContext.getResponse(), "工厂模板.xls");
		
		
		
		return NONE;
	}
	
	private File upload;//用来接收上传的文件

	//xxx页面的file的名字+FileName(固定搭配),获取上传的文件的名字
	private String uploadFileName;
	//upload  + contentType(固定搭配)
	private String uploadContentType;//上传文件mime格式
	
	private FactoryService factoryService;
	
	public void setFactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}

	@SuppressWarnings("resource")
	public String upload() throws Exception {
		//将上传的文件放入wb中
		Workbook wb = new HSSFWorkbook(new FileInputStream(upload));
		//读取
		Sheet sheet = wb.getSheetAt(0);
		//定义公共变量
		int rowNo = 2;
		int cellNo = 0;
		
		
		while(true){
			try {
				cellNo = 0;
				//获取行
				Row row = sheet.getRow(rowNo++);
				//获取数据
				//类型	
				String ctype = row.getCell(cellNo++).getStringCellValue();
				//工厂全称	
				String fullName = row.getCell(cellNo++).getStringCellValue();
				//工厂简称	
				String factoryName = row.getCell(cellNo++).getStringCellValue();
				//联系人	
				String contacts = row.getCell(cellNo++).getStringCellValue();
				//联系电话	
				String phone = row.getCell(cellNo++).getStringCellValue();
				//手机号	
				String mobile = row.getCell(cellNo++).getStringCellValue();
				//传真	
				String fax = row.getCell(cellNo++).getStringCellValue();
				//地址	
				String address = row.getCell(cellNo++).getStringCellValue();
				//杰信代表	
				String inspector = row.getCell(cellNo++).getStringCellValue();
				//备注	
				String remark = row.getCell(cellNo++).getStringCellValue();
				//排序号	
				Integer orderNo = (int)row.getCell(cellNo++).getNumericCellValue();
				//状态（0禁用1启用）
				Integer state = (int)row.getCell(cellNo++).getNumericCellValue();
				
				Factory factory = new Factory(ctype, fullName, factoryName, contacts, phone, mobile, fax, address, inspector, remark, orderNo, state);
				
				//调用Service层，保存
				factoryService.saveOrUpdate(factory);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
		
		return "toImport";
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	
	
	
	
	
}
