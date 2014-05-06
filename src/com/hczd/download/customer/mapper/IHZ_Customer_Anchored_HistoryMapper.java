package com.hczd.download.customer.mapper;

import java.util.Map;

import com.hczd.download.base.mapper.IHZ_BaseMapper;
import com.hczd.download.customer.module.HZ_Customer_Anchored_History;

/**
 * 客户挂靠关系Mapper
 * @author linjian 
 * @version 2.0
 * @create_date 2013-7-9 下午3:46:50
 */
public interface IHZ_Customer_Anchored_HistoryMapper extends IHZ_BaseMapper<HZ_Customer_Anchored_History, Integer> {
	/**根据时间点获取历史挂靠关系
	 * @author linjian 
	 * @create_date 2013-7-10 下午4:36:56
	 * @param params
	 * @return 挂靠关系
	 */
	public HZ_Customer_Anchored_History getAnchoredByTime(Map<String, Object> params);
}
