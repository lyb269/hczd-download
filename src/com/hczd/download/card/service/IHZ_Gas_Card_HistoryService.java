package com.hczd.download.card.service;

import java.util.List;

import com.hczd.download.base.module.HZ_BaseRecordParams;
import com.hczd.download.base.service.IHZ_BaseService;
import com.hczd.download.card.module.HZ_Gas_Card;
import com.hczd.download.card.module.HZ_Gas_Card_History;

/**
 * 加油卡历史记录逻辑层接口
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-6 下午6:24:11
 */
public interface IHZ_Gas_Card_HistoryService extends IHZ_BaseService<HZ_Gas_Card_History, Integer> {
	/**
	 * 对新发卡的客户记录发卡
	 * @author linjian 
	 * @create_date 2013-6-7 上午10:01:37
	 * @param hz_gas_card
	 */
	public Integer recordCardHistory(HZ_Gas_Card hz_gas_card,HZ_BaseRecordParams rParams);
	
	/**
	 * 根据加油卡卡号、时间点获取历史归属记录
	 * @author linjian 
	 * @create_date 2013-6-17 下午2:55:54
	 * @param card_no
	 * @param time
	 * @return 历史归属记录
	 */
	public HZ_Gas_Card_History getHistoryByCardNoWithTime(String card_no,String time);
	
	/**
	 * 根据加油卡记录退卡信息
	 * @author linjian 
	 * @create_date 2013-7-3 下午3:59:59
	 * @param hz_gas_card
	 * @return 退卡信息
	 */
	public HZ_Gas_Card_History returnCard(HZ_Gas_Card hz_gas_card,HZ_BaseRecordParams rParams);
	
	/**修改历史记录的优惠区域
	 * @author linjian 
	 * @create_date 2013-7-18 上午11:24:31
	 * @param hz_gas_card
	 * @param rParams
	 * @return 修改历史记录的优惠区域
	 */
	public HZ_Gas_Card_History updateArea(HZ_Gas_Card hz_gas_card,String area,HZ_BaseRecordParams rParams);
	
	/**历史加油卡记录更换加油卡车牌号
	 * @author linjian 
	 * @create_date 2013-7-18 下午1:41:14
	 * @param hz_gas_card
	 * @param vehicle_no
	 * @param rParams
	 * @return 更换历史记录
	 */
	public HZ_Gas_Card_History updateVehicleNo(HZ_Gas_Card hz_gas_card,String vehicle_no,HZ_BaseRecordParams rParams);
	
	/**根据月份获取加油卡历史记录
	 * @author linjian 
	 * @create_date 2013-7-26 下午3:46:19
	 * @param customer_id
	 * @param month
	 * @param card_no
	 * @return 加油卡的历史记录
	 */
	public HZ_Gas_Card_History getHistoryByCardAndMonth(Integer customer_id,String month,String card_no);

	/**
	 * 获取未上传行驶证的加油卡
	 * @author linhui
	 * @create_date 2013-12-4 下午1:50:26
	 * @param card_nos
	 * @return
	 */
	public List<HZ_Gas_Card_History> getNotDrivingLicense(String card_nos);
}
