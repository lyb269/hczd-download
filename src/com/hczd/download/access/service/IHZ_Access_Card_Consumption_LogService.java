package com.hczd.download.access.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hczd.download.access.module.HZ_Access_Card_Consumption_Log;
import com.hczd.download.base.module.HZ_PageData;
import com.hczd.download.base.service.IHZ_BaseService;

/**
 * 类描述：通行卡消费数据逻辑层接口
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-1-3 时间：下午2:06:05
 */
public interface IHZ_Access_Card_Consumption_LogService extends IHZ_BaseService<HZ_Access_Card_Consumption_Log, Integer> {
	
	/**去除重复的消费数据
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-1-9 时间：上午11:28:24
	 */
	public void deleteRepeat();
	
	/**获取最近两天内消费数据唯一码有时间和卡号组成
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-1-9 时间：上午11:28:24
	 */
	public Set<String> getUnique_Var();
	
	/**获取通行卡明细
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-19 时间：上午9:37:18
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryDetailInfo(Map<String, Object> params,HZ_PageData pageData);
	
	/**根据卡id和时间获取卡余额
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-19 时间：下午3:10:38
	 */
	public String getCardIdTime(Integer card_id,String time);
	
	/**获取一段时间内的该主卡的所有卡消费记录
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-20 时间：下午3:42:33
	 * @param map
	 * @return
	 */
	public int countListByMainNo(Map<String, Object> map);
	
	/**获取一段时间内的该主卡的所有卡消费记录
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-20 时间：下午4:39:25
	 * @param map
	 * @return
	 */
	public List<HZ_Access_Card_Consumption_Log> getListByMainNo(Map<String, Object> map);
}
