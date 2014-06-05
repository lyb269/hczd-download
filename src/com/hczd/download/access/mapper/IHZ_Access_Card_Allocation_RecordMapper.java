package com.hczd.download.access.mapper;

import java.util.List;
import java.util.Map;

import com.hczd.download.access.module.HZ_Access_Card_Allocation_Record;
import com.hczd.download.base.mapper.IHZ_BaseMapper;

/**
 * 类描述：通行卡分配记录mapper
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-5-15 时间：下午4:33:10
 */
public interface IHZ_Access_Card_Allocation_RecordMapper extends IHZ_BaseMapper<HZ_Access_Card_Allocation_Record, Integer> { 
	/**获取一段时间内的该主卡的所有卡分配记录
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-20 时间：下午5:14:01
	 * @param map
	 * @return
	 */
	public List<HZ_Access_Card_Allocation_Record> getListByMainNo(Map<String, Object> map);
	
	/**获取一段时间内的该主卡的所有卡分配记录数
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-20 时间：下午5:07:18
	 * @param map
	 * @return
	 */
	public int countListByMainNo(Map<String, Object> map);
}
