package cn.itcast.export.webservice;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import cn.itcast.export.domain.Export;
import cn.itcast.export.domain.ExportProduct;
import cn.itcast.export.service.ExportService;
import cn.itcast.export.vo.ExportProductResult;
import cn.itcast.export.vo.ExportProductVo;
import cn.itcast.export.vo.ExportResult;
import cn.itcast.export.vo.ExportVo;

public class EpService implements IEpService{
	
	private ExportService exportService;
	
	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}



	@Override
	public ExportResult exportE(ExportVo exportVo) throws Exception {
		//3 接收数据：已经接收好了，方法参数的值就是接收到的内容
		//4 解析参数，将vo转成实体
		//4.1 准备domain
		Export export = new Export();
		//4.2 复制属性
		BeanUtils.copyProperties(exportVo, export);
		//4.3 拷贝货物信息
		Set<ExportProductVo> epVoSet = exportVo.getProducts();
		//4.4 准备ExportProduct货物信息
		Set<ExportProduct> epSet = new HashSet<ExportProduct>();
		// 4.5 拷贝属性
		for(ExportProductVo epVo:epVoSet){
			//准备ep
			ExportProduct ep = new ExportProduct();
			//拷贝属性
			BeanUtils.copyProperties(epVo, ep);
			//添加至集合
			epSet.add(ep);
		}
		//4.6 将集合给export
		export.setProducts(epSet);
		//5 计算税收
		for(ExportProduct ep:epSet){
			ep.setTax(10d);
		}
		//5.1 报运成功
		export.setState(2);
		export.setReason("报运通过");
		
		//6 保存到海关数据库
		exportService.saveOrUpdate(export);
		
		//7 准备返回数据
		//7.1 准备ExportResult
		ExportResult er = new ExportResult();
		er.setExportId(export.getExportId());
		er.setRemark(export.getReason());
		er.setState(export.getState());
		
		//7.2 准备ExportProductResult
		Set<ExportProductResult> eprSet = new HashSet<ExportProductResult>();
//		7.3 遍历ExportProduct，将数据给eprSet
		for(ExportProduct ep:epSet){
			//7.3.1 准备epr
			ExportProductResult epr = new ExportProductResult();
			//7.3.2 赋值
			epr.setExportProductId(ep.getExportProductId());
			epr.setTax(ep.getTax());
			//7.3.3 添加至集合
			eprSet.add(epr);
		}
		//7.4 将集合给exportResult
		er.setProducts(eprSet);
		//8 返回
		return er;
	}

}
