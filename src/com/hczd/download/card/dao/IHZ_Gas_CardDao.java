package com.hczd.download.card.dao;

import java.util.List;
import java.util.Map;

import com.hczd.download.base.dao.IHZ_BaseDao;
import com.hczd.download.base.module.HZ_PageData;
import com.hczd.download.card.module.HZ_Gas_Card;
import com.hczd.download.card.module.HZ_Gas_Card_Consumption_Log;

/**
 * 加油卡数据层接口
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-28 上午11:26:12
 */
public interface IHZ_Gas_CardDao extends IHZ_BaseDao<HZ_Gas_Card, Integer> {
	/**获取加油卡不重复的区域集合
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2013-6-24 时间：下午6:38:24
	 * @param params
	 * @return
	 */
	public List<String> getArea(Map<String, Object> params);
	
	/**
	 * 根据客户、月份获取客户的月结报表加油卡
	 * @author linjian 
	 * @create_date 2013-7-25 下午8:07:05
	 * @param customer_id
	 * @param month
	 * @return 加油卡列表
	 */
	public List<String> getMonthChartCardNoList(Map<String, Object> params);
	
	/**
	 * 获取加油卡明细
	 * @author linhui
	 * @create_date 2013-8-24 下午2:30:21
	 * @param params
	 * @return
	 */
	public List<HZ_Gas_Card_Consumption_Log> getCardDetail(Map<String, Object> params,HZ_PageData pageData);
	
	/**
	 * 获取加油卡明细数量
	 * @author linhui
	 * @create_date 2013-8-24 下午2:30:21
	 * @param params
	 * @return
	 */
	public int getCardDetailCount(Map<String, Object> params);
	
	/**获取上期卡余额
	 * @author linjian
	 * @create_date 2013-11-7 下午5:21:18
	 * @param params 参数
	 * @return 加油卡的上期卡余额
	 */
	public String getpre_balance(String card_no,String pre_month_last_day,Integer customer_id);
	
	/**获取下载消费主卡下的加油卡
	 * @author linjian
	 * @create_date 2013-11-7 下午6:24:04
	 * @param params
	 * @return 加油卡列表
	 */
	public List<Map<String, Object>> getConsumptionCardNoByMainCard(Map<String, Object> params);
	
	/**统计加油卡信息
	 * @author linjian
	 * @create_date 2013-11-15 下午4:32:38
	 * @param params 统计参数
	 * @return 统计结果
	 */
	public Map<String, Object> countCard(Map<String, Object> params);
	
	/**获取单张加油卡的截止时间卡余额
	 * @author linjian
	 * @create_date 2013-12-3 下午5:02:07
	 * @param params 参数
	 * @return 截止时间的加油卡余额
	 */
	public String getMonthLastDayCardBalance(Map<String, Object> params);
	
	/**获取客户所有加油卡的截止时间的余额
	 * @author linjian
	 * @create_date 2013-12-3 下午5:03:23
	 * @param params 参数
	 * @return 截止时间的加油卡总额
	 */
	public String getMonthLastDayBalance(Map<String, Object> params);
}
