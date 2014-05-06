package com.hczd.download.card.mapper;

import java.util.List;
import java.util.Map;

import com.hczd.download.base.mapper.IHZ_BaseMapper;
import com.hczd.download.card.module.HZ_Gas_Card_Allocation_Record;

/**
 * 分配记录Mapper
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-15 下午1:59:51
 */
public interface IHZ_Gas_Card_Allocation_RecordMapper extends IHZ_BaseMapper<HZ_Gas_Card_Allocation_Record, Integer> {
	/**获取最后一笔分配记录
	 * @author linjian 
	 * @create_date 2013-7-23 下午2:22:56
	 * @param params
	 * @return 分配记录
	 */
	public HZ_Gas_Card_Allocation_Record getLastAllocation(Map<String, Object> params);
	
	/**
	 * 获取一段时间内的该主卡的所有加油卡分配记录
	 * @author linhui
	 * @create_date 2013-8-19 上午10:20:42
	 * @param mainCardNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<HZ_Gas_Card_Allocation_Record> getListByMainNo(Map<String, Object> map);
	

	/**
	 * 获取一段时间内的该主卡的所有加油卡分配记录数
	 * @author linhui
	 * @create_date 2013-8-19 上午10:20:42
	 * @param mainCardNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int countListByMainNo(Map<String, Object> map);
	
	/**统计可客户端申请中的数量
	 * @author linjian
	 * @create_date 2013-11-25 上午11:31:24
	 * @return 数量
	 */
	public int countClientApp();
}
