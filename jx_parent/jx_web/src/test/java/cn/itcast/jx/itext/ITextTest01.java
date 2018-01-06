package cn.itcast.jx.itext;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ITextTest01 {

	public static void main(String[] args) throws Exception {
		//1 创建document文档对象
		Document document = new Document();
		//2 设置输出位置
		PdfWriter.getInstance(document, new FileOutputStream(new File("d:\\a.pdf")));
		//3 打开文档
		document.open();
		//4 写入内容
		document.add(new Paragraph("itcast,一统江湖,千秋万代!!!"));
		
		//5 关闭文档
		document.close();
		
		System.out.println("ok");
	}

}
