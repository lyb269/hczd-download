package com.hczd.download.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hczd.download.base.service.impl.HZ_BaseService;
import com.hczd.download.customer.dao.IHZ_Customer_Anchored_HistoryDao;
import com.hczd.download.customer.dao.impl.HZ_Customer_Anchored_HistoryDao;
import com.hczd.download.customer.module.HZ_Customer_Anchored_History;
import com.hczd.download.customer.service.IHZ_Customer_Anchored_HistoryService;

/**
 * 挂靠关系历史记录逻辑层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-7-9 下午5:32:48
 */
@Service
public class HZ_Customer_Anchored_HistoryService extends HZ_BaseService<HZ_Customer_Anchored_History, Integer> implements IHZ_Customer_Anchored_HistoryService {
	@Autowired
	private IHZ_Customer_Anchored_HistoryDao hz_customer_anchored_historyDao;

	@Autowired
	public HZ_Customer_Anchored_HistoryService(HZ_Customer_Anchored_HistoryDao dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 根据时间点获取有效的挂靠关系
	 * @author linjian 
	 * @create_date 2013-7-10 下午4:15:24
	 * @param customer_id
	 * @param time
	 * @return 有效的挂靠关系
	 */
	@Override
	public HZ_Customer_Anchored_History getAnchoredByTime(Integer customer_id,
			String time) {
		return hz_customer_anchored_historyDao.getAnchoredByTime(time, customer_id);
	}
	

}
