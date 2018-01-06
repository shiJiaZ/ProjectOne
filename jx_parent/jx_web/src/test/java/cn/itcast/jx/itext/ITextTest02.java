package cn.itcast.jx.itext;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

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

public class ITextTest02 {

	public static void main(String[] args) throws Exception {
		//创建document文档
		/**
		 * 	1 ：纸张大小  A3    A4    A6 
		 *  2 -5 :左右上下的边距
		 */
		Document document = new Document(PageSize.A4, 15, 15, 15, 15);
		//设置输出位置
		PdfWriter.getInstance(document, new FileOutputStream(new File("d:\\a.pdf")));
		//打开文档
		document.open();
		
		//写入内容
		//创建支持中文的根字体
		/**
		 * 第一个参数：字体
		 * 第二个参数：编码+布局方式
		 * 第三个参数：是否以内嵌的方式在pdf中加入字体，
		 */
		BaseFont baseFont = BaseFont.createFont(AsianFontMapper.ChineseSimplifiedFont, 
					AsianFontMapper.ChineseSimplifiedEncoding_H, BaseFont.NOT_EMBEDDED);
		
		/*********大标题的输出**********/
		//创建大标题的字体
		Font bigTitleFont = new Font(baseFont, 24, Font.BOLD, BaseColor.RED);
		//创建大标题
		Paragraph bigTitleParagraph = new Paragraph("出货表", bigTitleFont);
		//设置对齐方式
		bigTitleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
		//添加至document中
		document.add(bigTitleParagraph);
		
		/**********作者的输出*********/
		//创建作者的字体
		Font authorFont = new Font(baseFont, 16, Font.BOLDITALIC, BaseColor.PINK);
		//创建作者
		Paragraph authorParagraph = new Paragraph("黑马18.受", authorFont);
		//设置对齐方式
		authorParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
		//添加至文档中
		document.add(authorParagraph);
		
		/**********内容的输出*********/
		List<Object[]> list = new ArrayList<Object[]>();
		list.add(new Object[]{"七月份",1000,1050});
		list.add(new Object[]{"八月份",1100,1050});
		list.add(new Object[]{"九月份",950,1050});
		
		//创建字体
		Font contentFont = new Font(baseFont, 12, Font.NORMAL, BaseColor.GREEN);
		
		//创建表格
		//参数：表格的列数
		PdfPTable table = new PdfPTable(3);
		//设置table的对齐方式和距离上个元素的距离
		table.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
		//
		table.setSpacingBefore(10);
		
		//只有列，没有行
		table.addCell(new PdfPCell(new Phrase("月份", contentFont)));
		table.addCell(new PdfPCell(new Phrase("去年销量", contentFont)));
		table.addCell(new PdfPCell(new Phrase("今年销量", contentFont)));
		
		for(Object[] values:list){
			table.addCell(new PdfPCell(new Phrase(values[0]+"", contentFont)));
			table.addCell(new PdfPCell(new Phrase(values[1]+"", contentFont)));
			table.addCell(new PdfPCell(new Phrase(values[2]+"", contentFont)));
			
		}
		//将table添加至文档中
		document.add(table);
		
		//关闭文档
		document.close();
		

	}

}
