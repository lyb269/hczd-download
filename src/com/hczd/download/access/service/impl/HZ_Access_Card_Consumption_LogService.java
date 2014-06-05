package com.hczd.download.access.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hczd.download.access.dao.IHZ_Access_Card_Consumption_LogDao;
import com.hczd.download.access.dao.impl.HZ_Access_Card_Consumption_LogDao;
import com.hczd.download.access.module.HZ_Access_Card_Consumption_Log;
import com.hczd.download.access.service.IHZ_Access_Card_Allocation_RecordService;
import com.hczd.download.access.service.IHZ_Access_Card_Consumption_LogService;
import com.hczd.download.base.module.HZ_PageData;
import com.hczd.download.base.service.impl.HZ_BaseService;
import com.hczd.download.common.util.HZ_AccountUtil;

/**
 * 类描述：通行卡消费数据逻辑层实现类
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-1-3 时间：下午2:10:11
 */
@Service
public class HZ_Access_Card_Consumption_LogService extends HZ_BaseService<HZ_Access_Card_Consumption_Log, Integer> implements IHZ_Access_Card_Consumption_LogService {
	@Autowired
	private IHZ_Access_Card_Consumption_LogDao hz_access_card_consumption_logDao;
	@Autowired
	private IHZ_Access_Card_Allocation_RecordService hz_access_card_allocation_recordService;
	
	@Autowired
	public HZ_Access_Card_Consumption_LogService(HZ_Access_Card_Consumption_LogDao dao) {
		super(dao);
	}

	/**去除重复的消费数据
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-1-9 时间：上午11:28:24
	 */
	public void deleteRepeat() {
		hz_access_card_consumption_logDao.deleteRepeat();
	}
	
	/**获取最近两天内消费数据唯一码有时间和卡号组成
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-1-9 时间：上午11:28:24
	 */
	public Set<String> getUnique_Var(){
		String start_time = "2014-01-01 00:00:00";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("column", "card_no,outlet_transit_time");
		if(StringUtils.isNotBlank(start_time))
			params.put("outlet_transit_time$time_>=", start_time);
		params.put("card_no_in", "select card_no from hz_access_card ");
		List<Map<String, Object>> list = hz_access_card_consumption_logDao.getScaleList(params);
		Set<String> set = new HashSet<String>();
		for (Map<String, Object> map : list) {
			set.add(map.get("card_no").toString().trim() + map.get("outlet_transit_time").toString().trim());
		}
		return set;  
	}

	
	/**获取通行卡明细
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-19 时间：上午9:37:18
	 * @param params
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryDetailInfo(Map<String, Object> params,HZ_PageData pageData){
		
		return hz_access_card_consumption_logDao.queryDetailInfo(params, pageData);
	}
	
	/**根据卡id和时间获取卡余额
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-19 时间：下午3:10:38
	 */
	public String getCardIdTime(Integer card_id,String time){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("card_id", card_id);
		params.put("s_sql", " com_time <='"+time+"' ");
		params.put("column", "cast(app_num as float)");
		Double topUp = hz_access_card_allocation_recordService.sum(params);
		
		params = new HashMap<String, Object>();
		params.put("card_id", card_id);
		params.put("s_sql", " outlet_transit_time <='"+time+"' ");
		params.put("column", "cast(money_num as float)");
		Double consumption = sum(params);
		
		return HZ_AccountUtil.subtraction(topUp+"", consumption+"", "#.##");
	}
	
	/**
	 * 获取一段时间内的该主卡的所有卡消费记录数
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-20 时间：下午3:41:55
	 * @param map
	 * @return
	 */
	public int countListByMainNo(Map<String, Object> map){
		return hz_access_card_consumption_logDao.countListByMainNo(map);
	}
	
	/**获取一段时间内的该主卡的所有加油卡消费记录
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-20 时间：下午4:38:30
	 * @param map
	 * @return
	 */
	public List<HZ_Access_Card_Consumption_Log> getListByMainNo(Map<String, Object> map){
		return hz_access_card_consumption_logDao.getListByMainNo(map);
	}
}
