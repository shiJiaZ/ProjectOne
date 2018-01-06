package cn.itcast.jx.poi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class POI_2003_Test01 {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		//1 创建工作表 excel2003 使用HSSFWorkbook对象
		Workbook wb = new HSSFWorkbook();
		//2 创建sheet
		Sheet sheet = wb.createSheet();
		//3 创建行对象 :POI的技术是从0开始
		Row row = sheet.createRow(3);
		//4 创建单元格对象
		Cell cell = row.createCell(3);
		//5 设置内容
		cell.setCellValue("itcast一统江湖千秋万代");
		//6 设置格式
		Font font = wb.createFont();
		font.setFontName("华文行楷");
		font.setFontHeightInPoints((short) 24);
		
		//将字体给单元格
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(font);
		
		cell.setCellStyle(cellStyle);
		
		//7 输出
		FileOutputStream os = new FileOutputStream(new File("d:\\a.xls"));
		//将wb写入到os中-- POI提供的功能
		wb.write(os);
		
		//刷出流
		os.flush();
		os.close();
		
		System.out.println("ok");
		//8 下载（web项目才有这个功能）
	}

}
