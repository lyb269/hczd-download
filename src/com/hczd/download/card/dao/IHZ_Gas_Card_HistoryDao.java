package com.hczd.download.card.dao;

import java.util.List;
import java.util.Map;

import com.hczd.download.base.dao.IHZ_BaseDao;
import com.hczd.download.card.module.HZ_Gas_Card_History;

/**
 * 加油卡历史记录数据层接口
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-6 下午6:21:12
 */
public interface IHZ_Gas_Card_HistoryDao extends IHZ_BaseDao<HZ_Gas_Card_History, Integer> {
	/*** 根据加油卡卡号、时间点获取历史归属记录
	 * @author linjian 
	 * @create_date 2013-7-18 下午2:19:08
	 * @param params
	 * @return
	 */
	public HZ_Gas_Card_History getHistoryByCardNoWithTime(Map<String, Object> params);
	
	/**根据月份获取加油卡历史记录
	 * @author linjian 
	 * @create_date 2013-7-26 下午3:39:35
	 * @param params
	 * @return 加油卡的历史记录
	 */
	public HZ_Gas_Card_History getHistoryByCardAndMonth(Map<String, Object> params);
	
	/**
	 * 获取未上传行驶证的加油卡
	 * @author linhui
	 * @create_date 2013-12-4 下午1:50:26
	 * @param card_nos
	 * @return
	 */
	public List<HZ_Gas_Card_History> getNotDrivingLicense(String card_nos);
}
