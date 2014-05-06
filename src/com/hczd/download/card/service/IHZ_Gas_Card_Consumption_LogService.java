package com.hczd.download.card.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hczd.download.base.service.IHZ_BaseService;
import com.hczd.download.card.module.HZ_Gas_Card_Consumption_Log;

/**
 * 加油卡消费数据逻辑层接口
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-16 上午9:06:48
 */
public interface IHZ_Gas_Card_Consumption_LogService extends IHZ_BaseService<HZ_Gas_Card_Consumption_Log, Integer> {
	/**
	 * 去除时间段内重复的消费记录
	 * @author linjian 
	 * @create_date 2013-6-17 下午4:19:12
	 * @param start_time
	 * @param end_time
	 * @return 重复数量
	 */
	public int deleteRepeat(String start_time,String end_time);
	
	/**
	 * 根据时间段获取主卡下的加油卡的消费记录唯一编码
	 * @author linjian 
	 * @create_date 2013-6-17 下午7:17:49
	 * @param start_time
	 * @param end_time
	 * @param main_card_no
	 * @return 主卡下的加油卡的消费记录唯一编码列表
	 */
	public Set<String> getUnique_Var(String start_time,String main_card_no);
	
	
	/**根据加油卡卡号获取最后一条消费记录
	 * @author linjian 
	 * @create_date 2013-7-23 下午2:49:53
	 * @param card_no
	 * @return 消费记录
	 */
	public HZ_Gas_Card_Consumption_Log getLastRecordByCardNo(String card_no);
	
	
	/**
	 * 获取一段时间内的该主卡的所有加油卡消费记录
	 * @author linhui
	 * @create_date 2013-8-19 上午10:20:42
	 * @param mainCardNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<HZ_Gas_Card_Consumption_Log> getListByMainNo(Map<String, Object> map);
	
	
	/**
	 * 获取一段时间内的该主卡的所有加油卡消费记录数
	 * @author linhui
	 * @create_date 2013-8-19 上午10:20:42
	 * @param mainCardNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int countListByMainNo(Map<String, Object> map);
	
	/**根据月份、客户编号判断客户是否在当月的加油卡有未计算完整
	 * @author linjian
	 * @create_date 2013-12-3 上午10:11:51
	 * @param customer_id 客户编号
	 * @param month 当月月份
	 * @return 是否有计算完整
	 */
	public boolean judgeCalc(Integer customer_id,String month);
	
}
