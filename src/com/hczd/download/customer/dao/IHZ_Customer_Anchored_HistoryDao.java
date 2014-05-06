package com.hczd.download.customer.dao;

import com.hczd.download.base.dao.IHZ_BaseDao;
import com.hczd.download.customer.module.HZ_Customer_Anchored_History;

/**
 * 客户挂靠关系历史记录
 * @author linjian 
 * @version 2.0
 * @create_date 2013-7-9 下午2:47:42
 */
public interface IHZ_Customer_Anchored_HistoryDao extends IHZ_BaseDao<HZ_Customer_Anchored_History, Integer> {
	/**根据时间点获取历史挂靠关系
	 * @author linjian 
	 * @create_date 2013-7-10 下午4:36:56
	 * @param params
	 * @return 挂靠关系
	 */
	public HZ_Customer_Anchored_History getAnchoredByTime(String time,Integer customer_id);
}
