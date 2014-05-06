package com.hczd.download.card.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hczd.download.base.module.HZ_BaseRecordParams;
import com.hczd.download.base.module.HZ_PageData;
import com.hczd.download.base.service.impl.HZ_BaseService;
import com.hczd.download.card.dao.IHZ_Gas_CardDao;
import com.hczd.download.card.dao.impl.HZ_Gas_CardDao;
import com.hczd.download.card.module.HZ_Gas_Card;
import com.hczd.download.card.module.HZ_Gas_Card_Consumption_Log;
import com.hczd.download.card.module.HZ_Gas_Card_History;
import com.hczd.download.card.service.IHZ_Gas_CardService;
import com.hczd.download.card.service.IHZ_Gas_Card_HistoryService;
import com.hczd.download.card.service.IHZ_Main_Gas_CardService;
import com.hczd.download.common.util.HZ_DateFormateUtil;
import com.hczd.download.common.util.HZ_Date_CalacUtil;

/**
 * 加油卡逻辑层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-28 上午11:38:23
 */
@Service
public class HZ_Gas_CardService extends HZ_BaseService<HZ_Gas_Card, Integer> implements IHZ_Gas_CardService {
	@Autowired
	private IHZ_Gas_CardDao hz_gas_cardDao;
	
	@Autowired
	private IHZ_Gas_Card_HistoryService hz_gas_card_historyService;
	
	@Autowired
	private IHZ_Main_Gas_CardService hz_main_gas_cardService;
	
	@Autowired
	public HZ_Gas_CardService(HZ_Gas_CardDao dao) {
		super(dao);
	}

	/**
	 * 根据主卡获取加油卡卡号列表
	 * @author linjian 
	 * @create_date 2013-6-16 上午11:38:13
	 * @param mainCardNo
	 * @return 加油卡卡号列表
	 */
	@Override
	public List<Object> getCardByMainCard(String mainCardNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("column", "id");
		params.put("card_no", mainCardNo);
		Object mainId = hz_main_gas_cardService.getScale(params);
		if(mainId != null){
			params = new HashMap<String, Object>();
			params.put("column", "card_no");
			params.put("main_card_id", Integer.parseInt(mainId.toString()));
			List<Object> listNo = getScaleListOne(params);
			return listNo;
		}
		return null;
	}

	@Override
	public List<String> getArea(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return hz_gas_cardDao.getArea(params);
	}

	/**修改加油卡的优惠区域
	 * @author linjian 
	 * @create_date 2013-7-18 下午1:49:30
	 * @param hz_gas_card
	 * @param area
	 * @param baseParams
	 * @return 加油卡
	 */
	@Override
	@Transactional
	public HZ_Gas_Card updateArea(HZ_Gas_Card hz_gas_card, String area,
			HZ_BaseRecordParams baseParams) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("update_area", area);
		params.put("where_id", hz_gas_card.getId());
		hz_gas_cardDao.updateField(params);
		hz_gas_card_historyService.updateArea(hz_gas_card, area, baseParams);
		return hz_gas_card;
	}

	/**
	 * 修改加油卡的车牌号
	 * @author linjian 
	 * @create_date 2013-7-18 下午1:50:54
	 * @param hz_gas_card
	 * @param vehicle_no
	 * @param vehicle_id
	 * @param baseParams
	 * @return 加油卡
	 */
	@Override
	public HZ_Gas_Card updateVehicleNo(HZ_Gas_Card hz_gas_card,
			String vehicle_no, Integer vehicle_id,
			HZ_BaseRecordParams baseParams) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("update_vehicle_no", vehicle_no);
		params.put("update_vehicle_id", vehicle_id);
		params.put("where_id", hz_gas_card.getId());
		hz_gas_cardDao.updateField(params);
		hz_gas_card.setVehicle_id(vehicle_id);
		hz_gas_card_historyService.updateVehicleNo(hz_gas_card, vehicle_no, baseParams);
		return hz_gas_card;
	}

	/**
	 * 根据客户、月份获取客户的月结报表加油卡
	 * @author linjian 
	 * @create_date 2013-7-25 下午8:07:05
	 * @param customer_id
	 * @param month
	 * @return 加油卡列表
	 */
	@Override
	public List<String> getMonthChartCardNoList(Integer customer_id,String month) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer_id", customer_id);
		params.put("month_first_day", HZ_Date_CalacUtil.getFirstDayOfMonth(month + "-01 00:00:00", HZ_DateFormateUtil.FORMATE_TIME));
		params.put("month_last_day", HZ_Date_CalacUtil.getLastDayOfMonth(month + "-01 00:00:00", HZ_DateFormateUtil.FORMATE_TIME));
		params.put("month", month);
		return hz_gas_cardDao.getMonthChartCardNoList(params);
	}

	@Override
	public String verify(HZ_Gas_Card hz_gas_card) {
		
		Map<String, Object> sys_params = new HashMap<String, Object>();
		if(hz_gas_card != null && hz_gas_card.getId() != null){
			HZ_Gas_Card card = hz_gas_cardDao.get(hz_gas_card.getId());
			if(StringUtils.isNotBlank(hz_gas_card.getCard_no()) && !hz_gas_card.getCard_no().equals(card.getCard_no())){
				sys_params.put("c_sql", " id<>'"+hz_gas_card.getId()+"' and status<>0 and card_no='"+hz_gas_card.getCard_no()+"' ");
				if(hz_gas_cardDao.get(sys_params) != null){
					return "该加油卡号已经存在";
				}
			}
		}else{
			if(StringUtils.isNotBlank(hz_gas_card.getCard_no())){
				sys_params.put("c_sql", " status<>0 and card_no='"+hz_gas_card.getCard_no()+"' ");
				if(hz_gas_cardDao.get(sys_params) != null){
					return "该加油卡号已经存在";
				}
			}
		}
		
		return null;
	}

	/**
	 * 获取加油卡明细
	 * @author linhui
	 * @create_date 2013-8-24 下午2:30:21
	 * @param params
	 * @return
	 */
	public List<HZ_Gas_Card_Consumption_Log> getCardDetail(Map<String, Object> params,HZ_PageData pageData){
		return hz_gas_cardDao.getCardDetail(params, pageData);
	}
	
	/**
	 * 根据加油卡卡号、时间点获取历史归属记录
	 * @author linjian 
	 * @create_date 2013-6-17 下午2:55:54
	 * @param card_no
	 * @param time
	 * @return 历史归属记录
	 */
	@Override
	public HZ_Gas_Card_History getHistoryByCardNoWithTime(String card_no,String time){
		return hz_gas_card_historyService.getHistoryByCardNoWithTime(card_no, time);
	}
	
	/**获取上期卡余额
	 * @author linjian
	 * @create_date 2013-11-7 下午5:21:18
	 * @param card_no 加油卡卡号
	 * @param month 月份
	 * @param customer_id 客户编号
	 * @return 加油卡的上期卡余额
	 */
	@Override
	public String getpre_balance(String card_no, String month,
			Integer customer_id) {
		String pre_month_last_day = "";
		try {
			pre_month_last_day = HZ_Date_CalacUtil.getLastMaxMonthDate(month + "-01").trim() + " 23:59:59";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hz_gas_cardDao.getpre_balance(card_no, pre_month_last_day, customer_id);
	}

	/**获取下载消费主卡下的加油卡
	 * @author linjian
	 * @create_date 2013-11-7 下午6:24:04
	 * @param start_time 消费数据开始时间
	 * @param end_time 消费数据截止时间
	 * @return 加油卡列表
	 */
	@Override
	public List<Object> getConsumptionCardNoByMainCard(String main_card_no,
			String start_time, String end_time) {
		List<Object> listCardNo = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("main_card_no", main_card_no);
		params.put("start_time", start_time);
		params.put("end_time", end_time);
		List<Map<String, Object>> listMap = hz_gas_cardDao.getConsumptionCardNoByMainCard(params);
		if(listMap != null && listMap.size() > 0) listCardNo = new ArrayList<Object>();
		for (Map<String, Object> map : listMap) {
			listCardNo.add(map.get("card_no"));
		}
		return listCardNo;
	}

	/**统计加油卡信息
	 * @author linjian
	 * @create_date 2013-11-15 下午4:32:38
	 * @param params 统计参数
	 * @return 统计结果
	 */
	@Override
	public Map<String, Object> countCard(Map<String, Object> params) {
		return hz_gas_cardDao.countCard(params);
	}
	

	/**获取本期卡余额
	 * @author linjian
	 * @create_date 2013-12-02 下午5:21:18
	 * @param card_no 加油卡卡号
	 * @param month 当前月份
	 * @param customer_id 客户编号
	 * @return 加油卡的上期卡余额
	 */
	@Override
	public String getthis_balance(String card_no, String month,
			Integer customer_id) {
		String month_last_day = "";
		month_last_day = HZ_Date_CalacUtil.getLastDayOfMonth(month + "-01 23:59:59", HZ_DateFormateUtil.FORMATE_TIME);
		return hz_gas_cardDao.getpre_balance(card_no, month_last_day, customer_id);
	}
	
	/**获取单张加油卡的截止时间卡余额
	 * @author linjian
	 * @create_date 2013-12-3 下午5:02:07
	 * @param params 参数
	 * @return 截止时间的加油卡余额
	 */
	public String getMonthLastDayCardBalance(String card_no,Integer customer_id,String month_last_day){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("card_no", card_no);
		params.put("customer_id", customer_id);
		params.put("month_last_day", month_last_day);
		return hz_gas_cardDao.getMonthLastDayCardBalance(params);
	}
	
	/**获取客户所有加油卡的截止时间的余额
	 * @author linjian
	 * @create_date 2013-12-3 下午5:03:23
	 * @param params 参数
	 * @return 截止时间的加油卡总额
	 */
	public String getMonthLastDayBalance(Integer customer_id,String month_last_day){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer_id", customer_id);
		params.put("month_last_day", month_last_day);
		return hz_gas_cardDao.getMonthLastDayBalance(params);
	}
}
