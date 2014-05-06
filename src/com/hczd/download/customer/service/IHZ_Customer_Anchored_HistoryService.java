package com.hczd.download.customer.service;

import com.hczd.download.base.service.IHZ_BaseService;
import com.hczd.download.customer.module.HZ_Customer_Anchored_History;

/**
 * 客户挂靠历史记录逻辑层接口
 * @author linjian 
 * @version 2.0
 * @create_date 2013-7-9 下午5:24:56
 */
public interface IHZ_Customer_Anchored_HistoryService extends IHZ_BaseService<HZ_Customer_Anchored_History, Integer> {
	
	/**
	 * 根据时间点获取有效的挂靠关系
	 * @author linjian 
	 * @create_date 2013-7-10 下午4:15:24
	 * @param customer_id
	 * @param time
	 * @return 有效的挂靠关系
	 */
	public HZ_Customer_Anchored_History getAnchoredByTime(Integer customer_id,String time);
}
