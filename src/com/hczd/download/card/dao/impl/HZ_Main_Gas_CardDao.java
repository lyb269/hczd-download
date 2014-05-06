package com.hczd.download.card.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hczd.download.base.dao.impl.HZ_BaseDao;
import com.hczd.download.card.dao.IHZ_Main_Gas_CardDao;
import com.hczd.download.card.mapper.IHZ_Main_Gas_CardMapper;
import com.hczd.download.card.module.HZ_Main_Gas_Card;

/**
 * 主卡数据层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-28 上午11:41:33
 */
@Repository
public class HZ_Main_Gas_CardDao extends HZ_BaseDao<HZ_Main_Gas_Card, Integer> implements IHZ_Main_Gas_CardDao {
	
	@Autowired
	public HZ_Main_Gas_CardDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setMapperClass(IHZ_Main_Gas_CardMapper.class);
	}

	/**获取中石化主卡列表
	 * @author linjian 
	 * @create_date 2013-7-19 下午8:13:36
	 * @return
	 */
	@Override
	public List<HZ_Main_Gas_Card> listSinopec() {
		// TODO Auto-generated method stub
		return getMapper().listSinopec();
	}

	/**获取中石油主卡列表
	 * @author linjian 
	 * @create_date 2013-7-19 下午8:14:31
	 * @return
	 */
	@Override
	public List<HZ_Main_Gas_Card> listPetroChina() {
		// TODO Auto-generated method stub
		return getMapper().listPetroChina();
	}
	
	/**获取主卡Mapper
	 * @author linjian 
	 * @create_date 2013-7-19 下午8:18:52
	 * @return
	 */
	public IHZ_Main_Gas_CardMapper getMapper() {
		// TODO Auto-generated method stub
		return super.getMapper(IHZ_Main_Gas_CardMapper.class);
	}
	
	/**
	 * 获取主卡副卡数
	 * @author linhui
	 * @create_date 上午10:08:12
	 * @param card_no
	 * @return
	 */
	public HZ_Main_Gas_Card getMainCardInfo(String card_no){
		return getMapper().getMainCardInfo(card_no);
	}
	
	/**
	 * 获取主卡未确认余额
	 * @author linhui
	 * @create_date 上午10:09:43
	 * @param id
	 * @return
	 */
	public String getMainCardAllocationApp(String card_no){
		return getMapper().getMainCardAllocationApp(card_no);
	}

	/**获取开始时间到现在的主卡充值金额
	 * @author linjian
	 * @create_date 2013-11-6 上午11:36:07
	 * @param main_card_no 主卡卡号
	 * @param start_time 开始时间（不包含）
	 * @return 时间段内的主卡充值金额
	 */
	public String getStempCardRecharge(String main_card_no,String start_time){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("main_card_no", main_card_no);
		params.put("start_time", start_time);
		return getMapper().getStempCardRecharge(params);
	}
	
	/**获取开始时间到现在的主卡分配金额
	 * @author linjian 
	 * @create_date 2013-11-6 上午11:38:04
	 * @param main_card_no 主卡卡号
	 * @param start_time 开始时间（不包含）
	 * @return 时间段内的主卡分配金额
	 */
	public String getStempCardAllocation(String main_card_no,String start_time){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("main_card_no", main_card_no);
		params.put("start_time", start_time);
		return getMapper().getStempCardAllocation(params);
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
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("main_card_balance", main_card_balance);
		params.put("main_card_no", main_card_no);
		params.put("start_time", start_time);
		getMapper().updateBalance(params);
	}

}
