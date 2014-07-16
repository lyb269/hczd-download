package com.hczd.download.card.dao;

import java.util.List;
import java.util.Map;

import com.hczd.download.base.dao.IHZ_BaseDao;
import com.hczd.download.base.module.HZ_PageData;
import com.hczd.download.card.module.HZ_Main_Gas_Card;

/**
 * 主卡数据层接口
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-28 上午11:40:30
 */
public interface IHZ_Main_Gas_CardDao extends IHZ_BaseDao<HZ_Main_Gas_Card, Integer> {
	/**获取中石化主卡列表
	 * @author linjian 
	 * @create_date 2013-7-19 下午8:13:36
	 * @return
	 */
	public List<HZ_Main_Gas_Card> listSinopec();
	
	/**获取中石油主卡列表
	 * @author linjian 
	 * @create_date 2013-7-19 下午8:14:31
	 * @return
	 */
	public List<HZ_Main_Gas_Card> listPetroChina();
	
	/**
	 * 获取主卡基本信息
	 * @author linhui
	 * @create_date 上午10:08:12
	 * @param card_no
	 * @return
	 */
	public HZ_Main_Gas_Card getMainCardInfo(String card_no);
	
	/**
	 * 获取主卡未确认余额
	 * @author linhui
	 * @create_date 上午10:09:43
	 * @param id
	 * @return
	 */
	public String getMainCardAllocationApp(String card_no);
	
	/**获取开始时间到现在的主卡充值金额
	 * @author linjian
	 * @create_date 2013-11-6 上午11:36:07
	 * @param main_card_no 主卡卡号
	 * @param start_time 开始时间（不包含）
	 * @return 时间段内的主卡充值金额
	 */
	public String getStempCardRecharge(String main_card_no,String start_time);
	
	/**获取开始时间到现在的主卡分配金额
	 * @author linjian 
	 * @create_date 2013-11-6 上午11:38:04
	 * @param main_card_no 主卡卡号
	 * @param start_time 开始时间（不包含）
	 * @return 时间段内的主卡分配金额
	 */
	public String getStempCardAllocation(String main_card_no,String start_time);
	
	/**主卡余额调整
	 * @author linjian
	 * @create_date 2013-11-6 下午2:15:56
	 * @param main_card_balance 开始时间点的余额
	 * @param main_card_no 主卡卡号
	 * @param start_time 开始时间（不包含计算）
	 */
	public void updateBalance(String main_card_balance,String main_card_no,String start_time);
	/**
	 * 中石油主卡列表
	 * @author linyb
	 * @create_date 2014-7-14下午1:54:19
	 * @param params
	 * @param page
	 * @return
	 */
	public List<HZ_Main_Gas_Card> listPetroChinaByPage(Map<String,Object> params , HZ_PageData page);
	/**
	 * 中石油主卡数
	 * @author linyb
	 * @create_date 2014-7-14下午1:55:08
	 * @param params
	 * @return
	 */
	public int countPetroChina(Map<String,Object> params);
}
