package com.hczd.download.card.mapper;

import java.util.List;
import java.util.Map;

import com.hczd.download.base.mapper.IHZ_BaseMapper;
import com.hczd.download.base.module.HZ_PageData;
import com.hczd.download.card.module.HZ_Main_Gas_Card;

/**
 * 主卡相关Mapper
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-28 上午11:39:36
 */
public interface IHZ_Main_Gas_CardMapper extends IHZ_BaseMapper<HZ_Main_Gas_Card, Integer> {
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
	
	/**获取时间段内的主卡充值金额
	 * @author linjian
	 * @create_date 2013-11-6 上午11:36:07
	 * @param main_card_no 主卡卡号
	 * @param start_time 开始时间（不包含）
	 * @param end_time 结束时间（包含）
	 * @return 时间段内的主卡充值金额
	 */
	public String getStempCardRecharge(Map<String, Object> params);
	
	/**获取时间段内的主卡分配金额
	 * @author linjian 
	 * @create_date 2013-11-6 上午11:38:04
	 * @param main_card_no 主卡卡号
	 * @param start_time 开始时间（不包含）
	 * @param end_time 结束时间（包含）
	 * @return 时间段内的主卡分配金额
	 */
	public String getStempCardAllocation(Map<String, Object> params);
	
	/**主卡余额调整
	 * @author linjian
	 * @create_date 2013-11-6 下午2:15:23
	 * @param params 调整参数
	 */
	public void updateBalance(Map<String, Object> params);
	
	/**使用事务主卡金额增加
	 * @author linjian
	 * @create_date 2014-04-09 上午11:43:10
	 * @param params 参数
	 * @return 增加后的主卡余额
	 */
	public String in_account(Map<String, Object> params);
	
	/**
	 * 中石油主卡列表
	 * @author linyb
	 * @create_date 2014-7-14下午1:54:19
	 * @param params
	 * @param page
	 * @return
	 */
	public List<HZ_Main_Gas_Card> listPetroChinaByPage(Map<String,Object> params );
	/**
	 * 中石油主卡数
	 * @author linyb
	 * @create_date 2014-7-14下午1:55:08
	 * @param params
	 * @return
	 */
	public int countPetroChina(Map<String,Object> params);
}
