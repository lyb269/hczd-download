package com.hczd.download.access.mapper;

import java.util.List;
import java.util.Map;

import com.hczd.download.access.module.HZ_Access_Card_Consumption_Log;
import com.hczd.download.base.mapper.IHZ_BaseMapper;

/**
 * 类描述：通行卡消费记录mapper
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-1-3 时间：下午1:51:25
 */
public interface IHZ_Access_Card_Consumption_LogMapper extends IHZ_BaseMapper<HZ_Access_Card_Consumption_Log, Integer> {
	/**
	 * 去除时间段内重复的消费记录
	 * @return 重复数量
	 */
	public void deleteRepeat();
	/**获取通行卡明细
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-19 时间：上午9:37:18
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryDetailInfo(Map<String, Object> params);
	
	/**获取通行卡明细数量
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-19 时间：上午9:37:02
	 * @param params
	 * @return
	 */
	public Integer countDetailInfo(Map<String, Object> params);
	
	/**获取一段时间内的该主卡的所有卡消费记录
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-20 时间：下午3:47:51
	 * @param map
	 * @return
	 */
	public List<HZ_Access_Card_Consumption_Log> getListByMainNo(Map<String, Object> map);
	
	/**获取一段时间内的该主卡的所有卡消费记录数
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-20 时间：下午3:38:58
	 * @param map
	 * @return
	 */
	public int countListByMainNo(Map<String, Object> map);
}
