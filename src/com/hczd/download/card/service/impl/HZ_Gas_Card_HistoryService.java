package com.hczd.download.card.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hczd.download.base.module.HZ_BaseRecordParams;
import com.hczd.download.base.service.impl.HZ_BaseService;
import com.hczd.download.card.dao.IHZ_Gas_Card_HistoryDao;
import com.hczd.download.card.dao.impl.HZ_Gas_Card_HistoryDao;
import com.hczd.download.card.module.HZ_Gas_Card;
import com.hczd.download.card.module.HZ_Gas_Card_History;
import com.hczd.download.card.service.IHZ_Gas_Card_HistoryService;
import com.hczd.download.common.util.HZ_DateFormateUtil;
import com.hczd.download.common.util.HZ_Date_CalacUtil;

/**
 * 加油卡历史记录逻辑层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-6 下午6:25:18
 */
@Service
public class HZ_Gas_Card_HistoryService extends HZ_BaseService<HZ_Gas_Card_History, Integer> implements IHZ_Gas_Card_HistoryService {
	@Autowired
	private IHZ_Gas_Card_HistoryDao hz_gas_card_historyDao;
	
	@Autowired
	public HZ_Gas_Card_HistoryService(HZ_Gas_Card_HistoryDao dao) {
		super(dao);
	}

	/**
	 * 对新发卡的客户记录发卡
	 * @author linjian 
	 * @create_date 2013-6-7 上午10:01:37  
	 * @param hz_gas_card
	 * @param type 发卡类型 1为发卡、-1为退卡
	 */
	@Override
	@Transactional
	public Integer recordCardHistory(HZ_Gas_Card hz_gas_card,HZ_BaseRecordParams rParams) {
		Integer rs = 200;
			//根据该卡的来源情况来记录历史归属
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("card_no", hz_gas_card.getCard_no());
			params.put("order_by", "start_time_desc");
			params.put("limit", "1");
			List<HZ_Gas_Card_History> listCardHistory = hz_gas_card_historyDao.list(params);
			//历史记录使用卡
			if(listCardHistory.size() >= 1){
				HZ_Gas_Card_History card_history = listCardHistory.get(0);
				//如果上次退卡记录丢失、或者其他原因则该卡的结束归属时间为现在,或者现在是退卡状态
				if(StringUtils.isBlank(card_history.getEnd_time())){
					//设置当前时间
					card_history.setEnd_time(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date()));
					//记录操作信息
					card_history.append(rParams);
					hz_gas_card_historyDao.update(card_history);
				}
			}
		
		/***记录发卡情况下**/
		HZ_Gas_Card_History hz_gas_card_history = new HZ_Gas_Card_History();
		//记录发卡时间
		hz_gas_card_history.setStart_time(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date()));
		hz_gas_card_history.setCustomer_id(hz_gas_card.getCustomer_id());
		hz_gas_card_history.setCard_no(hz_gas_card.getCard_no());
		//客户名称
		hz_gas_card_history.setCustomer_name(hz_gas_card.getCustomer_name());
		//记录该卡的优惠区域
		hz_gas_card_history.setPerfer_area(hz_gas_card.getArea());
		//记录车牌号
		hz_gas_card_history.setVehicle_id(hz_gas_card.getVehicle_id());
		hz_gas_card_history.setVehicle_no(hz_gas_card.getVehicle_no());
		//记录操作信息
		hz_gas_card_history.append(rParams);
		hz_gas_card_historyDao.save(hz_gas_card_history);
		return rs;
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
	public HZ_Gas_Card_History getHistoryByCardNoWithTime(String card_no,String time) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("card_no", card_no);
		params.put("time", time);
		return hz_gas_card_historyDao.getHistoryByCardNoWithTime(params);
	}

	/**
	 * 根据加油卡记录退卡信息
	 * @author linjian 
	 * @create_date 2013-7-3 下午3:59:59
	 * @param hz_gas_card
	 * @return 退卡信息
	 */
	@Override
	@Transactional
	public HZ_Gas_Card_History returnCard(HZ_Gas_Card hz_gas_card,HZ_BaseRecordParams rParams) {
		HZ_Gas_Card_History hz_gas_card_history = null;
		//根据该卡的来源情况来记录历史归属
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("and_card_no", hz_gas_card.getCard_no());
		params.put("order_by", "start_time_desc");
		params.put("limit", "1");
		hz_gas_card_history = hz_gas_card_historyDao.get(params);
		if(hz_gas_card_history != null ){
			hz_gas_card_history.setEnd_time(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date()));
			params = new HashMap<String, Object>();
			params.put("where_id", hz_gas_card_history.getId());
			params.put("update_end_time", hz_gas_card_history.getEnd_time());
			params.put("update_perfer_area", hz_gas_card.getArea());
			hz_gas_card_historyDao.updateField(params);
		}else{
			//如果之前历史记录丢失
			/***记录发卡情况下**/
			hz_gas_card_history = new HZ_Gas_Card_History();
			//记录发卡时间
			hz_gas_card_history.setStart_time(hz_gas_card.getRegisterdate());
			hz_gas_card_history.setEnd_time(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date()));
			hz_gas_card_history.setCustomer_id(hz_gas_card.getCustomer_id());
			hz_gas_card_history.setCard_no(hz_gas_card.getCard_no());
			//设置优惠区域
			hz_gas_card_history.setPerfer_area(hz_gas_card.getArea());
			//客户名称
			hz_gas_card_history.setCustomer_name(hz_gas_card.getCustomer_name());
			hz_gas_card_history.setVehicle_id(hz_gas_card.getVehicle_id());
			hz_gas_card_history.setVehicle_no(hz_gas_card.getVehicle_no());
			//记录操作信息
			hz_gas_card_history.append(rParams);
			hz_gas_card_historyDao.save(hz_gas_card_history);
		}
		return hz_gas_card_history;
	}

	/**修改历史记录的优惠区域 
	 * @author linjian 
	 * @create_date 2013-7-18 上午11:24:31
	 * @param hz_gas_card
	 * @param rParams
	 * @return 修改历史记录的优惠区域
	 */
	@Override
	public HZ_Gas_Card_History updateArea(HZ_Gas_Card hz_gas_card,String area,
			HZ_BaseRecordParams rParams) {
		
		HZ_Gas_Card_History new_history = null;
		/**
		 * 第一步：获取当前的历史记录
		 */
		HZ_Gas_Card_History gas_card_history = getHistoryByCardNoWithTime(hz_gas_card.getCard_no(), new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date()));
		if(gas_card_history != null){
			/***
			 * 第二步：结束之前的历史记录
			 */
			if(StringUtils.isBlank(gas_card_history.getEnd_time())){
				//克隆一个历史记录
				try {
					new_history = gas_card_history.clone();
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("where_id", gas_card_history.getId());
					params.put("update_end_time", new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date()));
					hz_gas_card_historyDao.updateField(params);
					/**
					 * 创建新的历史记录
					 */
					new_history.setId(null);
					new_history.setStart_time(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date()));
					new_history.setPerfer_area(area);
					new_history.append(rParams);
					hz_gas_card_historyDao.save(new_history);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}//end if
		}else{
			//补一条旧数据
			new_history = new HZ_Gas_Card_History();
			new_history.setStart_time(hz_gas_card.getRegisterdate());
			new_history.setCard_no(hz_gas_card.getCard_no());
			new_history.setEnd_time(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date()));
			new_history.setCustomer_id(hz_gas_card.getCustomer_id());
			new_history.setCustomer_name(hz_gas_card.getCustomer_name());
			new_history.setVehicle_id(hz_gas_card.getVehicle_id());
			new_history.setVehicle_no(hz_gas_card.getVehicle_no());
			hz_gas_card_historyDao.save(new_history);
			
			//新增一条新数据
			new_history = new HZ_Gas_Card_History();
			new_history.setCard_no(hz_gas_card.getCard_no());
			new_history.setStart_time(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date()));
			new_history.append(rParams);
			new_history.setCustomer_id(hz_gas_card.getCustomer_id());
			new_history.setCustomer_name(hz_gas_card.getCustomer_name());
			new_history.setPerfer_area(area);
			new_history.setVehicle_id(hz_gas_card.getVehicle_id());
			new_history.setVehicle_no(hz_gas_card.getVehicle_no());
			hz_gas_card_historyDao.save(new_history);
		}
		return new_history;
	}

	/**历史加油卡记录更换加油卡车牌号
	 * @author linjian 
	 * @create_date 2013-7-18 下午1:41:14
	 * @param hz_gas_card
	 * @param vehicle_no
	 * @param rParams
	 * @return 更换历史记录
	 */
	@Override
	public HZ_Gas_Card_History updateVehicleNo(HZ_Gas_Card hz_gas_card,
			String vehicle_no, HZ_BaseRecordParams rParams) {
		HZ_Gas_Card_History new_history = null;
		/**
		 * 第一步：获取当前的历史记录
		 */
		HZ_Gas_Card_History gas_card_history = getHistoryByCardNoWithTime(hz_gas_card.getCard_no(), new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date()));
		if(gas_card_history != null){
			/***
			 * 第二步：结束之前的历史记录
			 */
			if(StringUtils.isBlank(gas_card_history.getEnd_time())){
				//克隆一个历史记录
				try {
					new_history = gas_card_history.clone();
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("where_id", gas_card_history.getId());
					params.put("update_end_time", new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date()));
					hz_gas_card_historyDao.updateField(params);
					/**
					 * 创建新的历史记录
					 */
					new_history.setId(null);
					new_history.setStart_time(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date()));
					new_history.setVehicle_id(hz_gas_card.getVehicle_id());
					new_history.setVehicle_no(vehicle_no);
					new_history.append(rParams);
					hz_gas_card_historyDao.save(new_history);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}//end if
		}else{
			
			//补充一条旧数据
			new_history = new HZ_Gas_Card_History();
			new_history.setCard_no(hz_gas_card.getCard_no());
			new_history.setStart_time(hz_gas_card.getRegisterdate());
			new_history.setEnd_time(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date()));
			new_history.setCustomer_id(hz_gas_card.getCustomer_id());
			new_history.setCustomer_name(hz_gas_card.getCustomer_name());
			new_history.setPerfer_area(hz_gas_card.getArea());
			new_history.setVehicle_id(hz_gas_card.getVehicle_id());
			new_history.setVehicle_no(vehicle_no);
			hz_gas_card_historyDao.save(new_history);
			
			//补充一条新数据
			new_history = new HZ_Gas_Card_History();
			new_history.setCard_no(hz_gas_card.getCard_no());
			new_history.setStart_time(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date()));
			new_history.append(rParams);
			new_history.setCustomer_id(hz_gas_card.getCustomer_id());
			new_history.setCustomer_name(hz_gas_card.getCustomer_name());
			new_history.setPerfer_area(hz_gas_card.getArea());
			new_history.setVehicle_id(hz_gas_card.getVehicle_id());
			new_history.setVehicle_no(vehicle_no);
			hz_gas_card_historyDao.save(new_history);
		}
		return new_history;
	}

	/**根据月份获取加油卡历史记录
	 * @author linjian 
	 * @create_date 2013-7-26 下午3:46:19
	 * @param customer_id
	 * @param month
	 * @param card_no
	 * @return 加油卡的历史记录
	 */
	@Override
	public HZ_Gas_Card_History getHistoryByCardAndMonth(Integer customer_id,
			String month, String card_no) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer_id", customer_id);
		params.put("month", month);
		params.put("card_no", card_no);
		params.put("month_first_day", month + "-01 00:00:00");
		params.put("month_last_day", HZ_Date_CalacUtil.getLastDayOfMonth(month+"-01 00:00:00", HZ_DateFormateUtil.FORMATE_TIME));
		return hz_gas_card_historyDao.getHistoryByCardNoWithTime(params);
	}

	/**
	 * 获取未上传行驶证的加油卡
	 * @author linhui
	 * @create_date 2013-12-4 下午1:50:26
	 * @param card_nos
	 * @return
	 */
	public List<HZ_Gas_Card_History> getNotDrivingLicense(String card_nos){
		return hz_gas_card_historyDao.getNotDrivingLicense(card_nos);
	}
}
