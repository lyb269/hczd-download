package com.hczd.download.access.dao;

import java.util.List;
import java.util.Map;

import com.hczd.download.access.module.HZ_Access_Card_Allocation_Record;
import com.hczd.download.base.dao.IHZ_BaseDao;

/**
 * 类描述：通行卡分配记录数据层接口
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-5-15 时间：下午4:41:47
 */
public interface IHZ_Access_Card_Allocation_RecordDao extends IHZ_BaseDao<HZ_Access_Card_Allocation_Record, Integer> {
	/**获取一段时间内的该主卡的所有卡分配记录
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-20 时间：下午5:17:35
	 * @param map
	 * @return
	 */
	public List<HZ_Access_Card_Allocation_Record> getListByMainNo(Map<String, Object> map);
	
	/**获取一段时间内的该主卡的所有卡分配记录数
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-20 时间：下午5:08:28
	 * @param map
	 * @return
	 */
	public int countListByMainNo(Map<String, Object> map);
}
