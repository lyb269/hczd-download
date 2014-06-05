package com.hczd.download.access.dao;

import java.util.List;
import java.util.Map;

import com.hczd.download.access.module.HZ_Access_Card_Consumption_Log;
import com.hczd.download.base.dao.IHZ_BaseDao;
import com.hczd.download.base.module.HZ_PageData;

/**
 * 类描述：通行卡消费数据数据层接口
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-1-3 时间：下午1:55:16
 */
public interface IHZ_Access_Card_Consumption_LogDao extends IHZ_BaseDao<HZ_Access_Card_Consumption_Log, Integer> {
	
	/**
	 * 去除时间段内重复的消费记录
	 * @author linjian 
	 * @create_date 2013-6-17 下午4:19:12
	 * @param start_time
	 * @param end_time
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
	public List<Map<String, Object>> queryDetailInfo(Map<String, Object> params,HZ_PageData pageData);
	
	/**
	 * 获取一段时间内的该主卡的所有卡消费记录数
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-20 时间：下午3:40:58
	 * @param map
	 * @return
	 */
	public int countListByMainNo(Map<String, Object> map);
	
	/**获取一段时间内的该主卡的所有卡消费记录
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-20 时间：下午4:37:22
	 * @param map
	 * @return
	 */
	public List<HZ_Access_Card_Consumption_Log> getListByMainNo(Map<String, Object> map);
}
