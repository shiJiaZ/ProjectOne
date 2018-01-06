package cn.itcast.jx.quartz;

import java.util.List;

import cn.itcast.jx.domain.Contract;
import cn.itcast.jx.service.ContractService;
import cn.itcast.jx.util.MailUtil;

public class DeliveryPeriodJob {
	
	private ContractService contractService;
	
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}



	/**
	 * 交期提醒
	 */
	public void execute(){
		String hql = "from Contract where to_char(deliveryPeriod-3,'yyyymmdd')=to_char(sysdate,'yyyymmdd')";
		List<Contract> list = contractService.find(hql, Contract.class, null);
		
		for(final Contract c:list){
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						MailUtil.sendMail("主人您好,您的购销合同编号为"+c.getContractNo()+"的交期到了，需要交货", "交期提醒", "1638064027@qq.com");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}
		
		
		
	}
	
	
	
}	
