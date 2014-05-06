package com.hczd.download.card.mapper;

import java.util.List;
import java.util.Map;

import com.hczd.download.base.mapper.IHZ_BaseMapper;
import com.hczd.download.card.module.HZ_Gas_Card_Consumption_Log;

/**
 * 加油卡消费记录Mapper
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-16 上午8:44:42
 */
public interface IHZ_Gas_Card_Consumption_LogMapper extends IHZ_BaseMapper<HZ_Gas_Card_Consumption_Log, Integer> {
	/**删除重复消费记录
	 * @author linjian 
	 * @create_date 2013-6-17 下午4:58:12
	 * @param params
	 * @return 重复数量
	 */
	public int deleteRepeat(Map<String, Object> params);
	
	/**获取最后一条消费记录
	 * @author linjian 
	 * @create_date 2013-7-22 下午5:45:36
	 * @param params
	 * @return 最后一条消费记录
	 */
	public HZ_Gas_Card_Consumption_Log getLast(Map<String, Object> params);
	
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
}
