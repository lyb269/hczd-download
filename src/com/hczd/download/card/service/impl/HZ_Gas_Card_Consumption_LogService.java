package com.hczd.download.card.service.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hczd.download.base.service.impl.HZ_BaseService;
import com.hczd.download.card.dao.IHZ_Gas_Card_Allocation_RecordDao;
import com.hczd.download.card.dao.IHZ_Gas_Card_Consumption_LogDao;
import com.hczd.download.card.dao.impl.HZ_Gas_Card_Consumption_LogDao;
import com.hczd.download.card.module.HZ_Gas_Card_Allocation_Record;
import com.hczd.download.card.module.HZ_Gas_Card_Consumption_Log;
import com.hczd.download.card.service.IHZ_Gas_CardService;
import com.hczd.download.card.service.IHZ_Gas_Card_Consumption_LogService;
import com.hczd.download.common.util.HZ_Date_CalacUtil;

/**
 * 加油卡消费数据逻辑层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-16 上午9:08:07
 */
@Service
public class HZ_Gas_Card_Consumption_LogService extends HZ_BaseService<HZ_Gas_Card_Consumption_Log, Integer> implements IHZ_Gas_Card_Consumption_LogService {
	@Autowired
	private IHZ_Gas_Card_Consumption_LogDao hz_gas_card_consumption_logDao;
	@Autowired
	private IHZ_Gas_Card_Allocation_RecordDao hz_gas_card_allocation_recordDao;
	@Autowired
	private IHZ_Gas_CardService hz_gas_cardService;
	
	@Autowired
	public HZ_Gas_Card_Consumption_LogService(HZ_Gas_Card_Consumption_LogDao dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 去除时间段内重复的消费记录
	 * @author linjian 
	 * @create_date 2013-6-17 下午4:19:12
	 * @param start_time
	 * @param end_time
	 * @return 重复数量
	 */
	@Override
	public int deleteRepeat(String start_time, String end_time) {
		Map<String, Object> params = new HashMap<String, Object>();
//		if(StringUtils.isNotBlank(start_time))
//			params.put("start_time", start_time);
//		if(StringUtils.isNotBlank(end_time))
//			params.put("end_time", end_time);
		return hz_gas_card_consumption_logDao.deleteRepeat(params);
	}

	/**
	 * 获取时间段内的消费数据唯一码
	 * @author linjian 
	 * @create_date 2013-6-17 下午4:19:12
	 * @param start_time
	 * @param end_time
	 * @return 重复数量
	 */
	@Override
	public Set<String> getUnique_Var(String start_time,String main_card_no) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("column", "card_no,charge_time");
		if(StringUtils.isNotBlank(start_time))
			params.put("charge_time$time_>=", start_time);
		params.put("card_no_in", "select substr(card_no,1," + main_card_no.length() + ") as card_no from hz_gas_card where main_card_no = '" + main_card_no + "'");
		List<Map<String, Object>> list = hz_gas_card_consumption_logDao.getScaleList(params);
		Set<String> set = new HashSet<String>();
		for (Map<String, Object> map : list) {
			set.add(map.get("card_no").toString().trim() + map.get("charge_time").toString().trim());
		}
		return set;  
	}
	


	/**根据加油卡卡号获取最后一条消费记录
	 * @author linjian 
	 * @create_date 2013-7-23 下午2:49:53
	 * @param card_no
	 * @return 消费记录
	 */
	@Override
	public HZ_Gas_Card_Consumption_Log getLastRecordByCardNo(String card_no) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("card_no", card_no);
		return hz_gas_card_consumption_logDao.getLast(params);
	}

	
	/**
	 * 获取一段时间内的该主卡的所有加油卡消费记录
	 * @author linhui
	 * @create_date 2013-8-19 上午10:20:42
	 * @param mainCardNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<HZ_Gas_Card_Consumption_Log> getListByMainNo(Map<String, Object> map){
		return hz_gas_card_consumption_logDao.getListByMainNo(map);
	}
	
	
	
	/**
	 * 获取一段时间内的该主卡的所有加油卡消费记录数
	 * @author linhui
	 * @create_date 2013-8-19 上午10:20:42
	 * @param mainCardNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int countListByMainNo(Map<String, Object> map){
		return hz_gas_card_consumption_logDao.countListByMainNo(map);
	}

	/**根据月份、客户编号判断客户是否在当月的加油卡有未计算完整
	 * @author linjian
	 * @create_date 2013-12-3 上午10:11:51
	 * @param customer_id 客户编号
	 * @param month 当月月份
	 * @return 是否有计算完整
	 */
	@Override
	public boolean judgeCalc(Integer customer_id, String month) {
		try {
			/**
			 * 第一步，获取该客户对应月份的加油卡
			 */
			List<String> cardNoList = hz_gas_cardService.getMonthChartCardNoList(customer_id, month);
			StringBuilder cards= new StringBuilder("");
			if(cardNoList!=null&&cardNoList.size()>0){
				for(String card : cardNoList){
					cards.append(card).append(",");
				}
				cards = cards.deleteCharAt(cards.length());
			}
			/**
			 * 第二步，计算该客户指定月份为止的总分配额、总消费额、总卡余额
			 */
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("column", "sum(cast(app_num as DECIMAL(100,2)))");
			params.put("status", HZ_Gas_Card_Allocation_Record.STATUS_SUCCESS);
			params.put("com_time$time_<=", HZ_Date_CalacUtil.getMaxMonthDate(month));
			params.put("customer_id", customer_id);
			params.put("a_sql", " card_no in ("+cards.toString()+")");
			hz_gas_card_allocation_recordDao.getScale(params);
			
			params = new HashMap<String, Object>();
			params.put("column", "sum(cast(money_num as DECIMAL(100,2)))");
			params.put("customer_id", customer_id);
			params.put("charge_time$time_<=", HZ_Date_CalacUtil.getMaxMonthDate(month));
			params.put("a_sql", " card_no in ("+cards.toString()+")");
			hz_gas_card_consumption_logDao.getScale(params);
			
			params = new HashMap<String, Object>();
			params.put("column", "sum(cast(money_num as DECIMAL(100,2)))");
			params.put("customer_id", customer_id);
			params.put("charge_time$time_<=", HZ_Date_CalacUtil.getMaxMonthDate(month));
			params.put("a_sql", " card_no in ("+cards.toString()+")");
//			Object consumption_amount = hz_gas_card_consumption_logDao.getScale(params);
			
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
