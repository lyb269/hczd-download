package com.hczd.download.card.service;

import java.util.List;
import java.util.Map;

import com.hczd.download.base.module.HZ_BaseRecordParams;
import com.hczd.download.base.module.HZ_PageData;
import com.hczd.download.base.service.IHZ_BaseService;
import com.hczd.download.card.module.HZ_Gas_Card;
import com.hczd.download.card.module.HZ_Gas_Card_Consumption_Log;
import com.hczd.download.card.module.HZ_Gas_Card_History;

/**
 * 加油卡业务类接口
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-28 上午11:36:06
 */
public interface IHZ_Gas_CardService extends IHZ_BaseService<HZ_Gas_Card, Integer> {
	/**
	 * 根据主卡获取加油卡卡号列表
	 * @author linjian 
	 * @create_date 2013-6-16 上午11:38:13
	 * @param mainCardNo
	 * @return 加油卡卡号列表
	 */
	public List<Object> getCardByMainCard(String mainCardNo);
	
	/**获取加油卡不重复的区域集合
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2013-6-24 时间：下午6:38:24
	 * @param params
	 * @return
	 */
	public List<String> getArea(Map<String, Object> params);
	
	/**修改加油卡的优惠区域
	 * @author linjian 
	 * @create_date 2013-7-18 下午1:49:30
	 * @param hz_gas_card
	 * @param area
	 * @param baseParams
	 * @return 加油卡
	 */
	public HZ_Gas_Card updateArea(HZ_Gas_Card hz_gas_card,String area,HZ_BaseRecordParams baseParams);
	
	/**
	 * 修改加油卡的车牌号
	 * @author linjian 
	 * @create_date 2013-7-18 下午1:50:54
	 * @param hz_gas_card
	 * @param vehicle_no
	 * @param vehicle_id
	 * @param baseParams
	 * @return 加油卡
	 */
	public HZ_Gas_Card updateVehicleNo(HZ_Gas_Card hz_gas_card,String vehicle_no,Integer vehicle_id,HZ_BaseRecordParams baseParams);
	
	/**
	 * 根据客户、月份获取客户的月结报表加油卡
	 * @author linjian 
	 * @create_date 2013-7-25 下午8:07:05
	 * @param customer_id
	 * @param month
	 * @return 加油卡列表
	 */
	public List<String> getMonthChartCardNoList(Integer customer_id,String month);
	
	/**验证
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2013-8-6 时间：下午6:17:58
	 * @return
	 */
	public String verify(HZ_Gas_Card hz_gas_card);
	
	/**
	 * 获取加油卡明细
	 * @author linhui
	 * @create_date 2013-8-24 下午2:30:21
	 * @param params
	 * @return
	 */
	public List<HZ_Gas_Card_Consumption_Log> getCardDetail(Map<String, Object> params,HZ_PageData pageData);
	
	/**
	 * 根据加油卡卡号、时间点获取历史归属记录
	 * @author linjian 
	 * @create_date 2013-6-17 下午2:55:54
	 * @param card_no
	 * @param time
	 * @return 历史归属记录
	 */
	public HZ_Gas_Card_History getHistoryByCardNoWithTime(String card_no,String time);

	/**获取上期卡余额
	 * @author linjian
	 * @create_date 2013-11-7 下午5:21:18
	 * @param card_no 加油卡卡号
	 * @param month 月份
	 * @param customer_id 客户编号
	 * @return 加油卡的上期卡余额
	 */
	public String getpre_balance(String card_no, String month,Integer customer_id);
	
	/**获取下载消费主卡下的加油卡
	 * @author linjian
	 * @create_date 2013-11-7 下午6:24:04
	 * @param start_time 消费数据开始时间
	 * @param end_time 消费数据截止时间
	 * @return 加油卡列表
	 */
	public List<Object> getConsumptionCardNoByMainCard(String main_card_no,String start_time,String end_time);
	
	/**统计加油卡信息
	 * @author linjian
	 * @create_date 2013-11-15 下午4:32:38
	 * @param params 统计参数
	 * @return 统计结果
	 */
	public Map<String, Object> countCard(Map<String, Object> params);
	

	/**获取本期卡余额
	 * @author linjian
	 * @create_date 2013-12-02 下午5:21:18
	 * @param card_no 加油卡卡号
	 * @param month 当前月份
	 * @param customer_id 客户编号
	 * @return 加油卡的上期卡余额
	 */
	public String getthis_balance(String card_no, String month,Integer customer_id);
	
	/**获取单张加油卡的截止时间卡余额
	 * @author linjian
	 * @create_date 2013-12-3 下午5:02:07
	 * @param params 参数
	 * @return 截止时间的加油卡余额
	 */
	public String getMonthLastDayCardBalance(String card_no,Integer customer_id,String month_last_day);
	
	/**获取客户所有加油卡的截止时间的余额
	 * @author linjian
	 * @create_date 2013-12-3 下午5:03:23
	 * @param params 参数
	 * @return 截止时间的加油卡总额
	 */
	public String getMonthLastDayBalance(Integer customer_id,String month_last_day);
}
