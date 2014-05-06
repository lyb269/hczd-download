package com.hczd.download.card.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hczd.download.base.service.impl.HZ_BaseService;
import com.hczd.download.card.dao.IHZ_Main_Gas_CardDao;
import com.hczd.download.card.dao.impl.HZ_Main_Gas_CardDao;
import com.hczd.download.card.module.HZ_Main_Gas_Card;
import com.hczd.download.card.service.IHZ_Main_Gas_CardService;
import com.hczd.download.common.util.HZ_AccountUtil;

/**
 * 主卡逻辑层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-28 上午11:46:15
 */
@Service
public class HZ_Main_Gas_CardService extends HZ_BaseService<HZ_Main_Gas_Card, Integer> implements IHZ_Main_Gas_CardService {
	@Autowired
	private IHZ_Main_Gas_CardDao hz_main_gas_cardDao;
	
	@Autowired
	public HZ_Main_Gas_CardService(HZ_Main_Gas_CardDao dao) {
		super(dao);
	}



	
	/**单线程操控修改主卡余额，并且返回修改后的主卡余额
	 * @author linjian
	 * @create_date 2013-11-7 上午11:17:37
	 * @param main_card_no 主卡卡号
	 * @param num 数量变差
	 * @return 修改后的主卡余额
	 */
	public synchronized String getCurBalanceAndUpdateBalance(String main_card_no,String num){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("column", "balance");
		params.put("card_no", main_card_no);
		String balance = hz_main_gas_cardDao.getScale(params).toString();
		//修改余额
		String t_balance = HZ_AccountUtil.sum(balance, num, "#.##");
		params = new HashMap<String, Object>();
		params.put("update_balance", t_balance);
		params.put("where_card_no", main_card_no);
		hz_main_gas_cardDao.updateField(params);
		return t_balance;
	}

	/**获取中石化主卡列表
	 * @author linjian 
	 * @create_date 2013-7-19 下午8:13:36
	 * @return
	 */
	@Override
	public List<HZ_Main_Gas_Card> listSinopec() {
		// TODO Auto-generated method stub
		return hz_main_gas_cardDao.listSinopec();
	}

	/**获取中石油主卡列表
	 * @author linjian 
	 * @create_date 2013-7-19 下午8:14:31
	 * @return
	 */
	@Override
	public List<HZ_Main_Gas_Card> listPetroChina() {
		// TODO Auto-generated method stub
		return hz_main_gas_cardDao.listPetroChina();
	}
	
	/**
	 * 获取主卡副卡数
	 * @author linhui
	 * @create_date 上午10:08:12
	 * @param card_no
	 * @return
	 */
	public HZ_Main_Gas_Card getMainCardInfo(String card_no){
		return hz_main_gas_cardDao.getMainCardInfo(card_no);
	}
	
	/**
	 * 获取主卡未确认余额
	 * @author linhui
	 * @create_date 上午10:09:43
	 * @param card_no
	 * @return
	 */
	public String getMainCardAllocationApp(String card_no){
		return hz_main_gas_cardDao.getMainCardAllocationApp(card_no);
	}

	/**获取开始时间到现在的主卡充值金额
	 * @author linjian
	 * @create_date 2013-11-6 上午11:36:07
	 * @param main_card_no 主卡卡号
	 * @param start_time 开始时间（不包含）
	 * @return 时间段内的主卡充值金额
	 */
	public String getStempCardRecharge(String main_card_no,String start_time){
		return hz_main_gas_cardDao.getStempCardRecharge(main_card_no, start_time);
	}
	
	/**获取开始时间到现在的主卡分配金额
	 * @author linjian 
	 * @create_date 2013-11-6 上午11:38:04
	 * @param main_card_no 主卡卡号
	 * @param start_time 开始时间（不包含）
	 * @return 时间段内的主卡分配金额
	 */
	public String getStempCardAllocation(String main_card_no,String start_time){
		return hz_main_gas_cardDao.getStempCardAllocation(main_card_no, start_time);
	}

	/**主卡余额调整
	 * @author linjian
	 * @create_date 2013-11-6 下午2:15:56
	 * @param main_card_balance 开始时间点的余额
	 * @param main_card_no 主卡卡号
	 * @param start_time 开始时间（不包含计算）
	 */
	@Override
	public void updateBalance(String main_card_balance, String main_card_no,
			String start_time) {
		hz_main_gas_cardDao.updateBalance(main_card_balance, main_card_no, start_time);
	}

}
