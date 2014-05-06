package com.hczd.download.card.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hczd.download.base.dao.impl.HZ_BaseDao;
import com.hczd.download.base.module.HZ_PageData;
import com.hczd.download.base.util.HZ_SQL_ParamsUtil;
import com.hczd.download.card.dao.IHZ_Gas_CardDao;
import com.hczd.download.card.mapper.IHZ_Gas_CardMapper;
import com.hczd.download.card.module.HZ_Gas_Card;
import com.hczd.download.card.module.HZ_Gas_Card_Consumption_Log;
import com.hczd.download.common.util.HZ_AccountUtil;

/**
 * 加油卡数据层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-28 上午11:29:00
 */
@Repository
public class HZ_Gas_CardDao extends HZ_BaseDao<HZ_Gas_Card, Integer> implements IHZ_Gas_CardDao {

	@Autowired
	public HZ_Gas_CardDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setMapperClass(IHZ_Gas_CardMapper.class);
	}

	@Override
	public List<String> getArea(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
		//添加sql条件
		params.put("where", " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params));
		
		return getMapper().getArea(params);
	}
	
	/**获取Mapper类型
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2013-6-24 时间：下午6:59:16
	 * @return
	 */
	public IHZ_Gas_CardMapper getMapper() {
		// TODO Auto-generated method stub
		return super.getMapper(IHZ_Gas_CardMapper.class);
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
	public List<String> getMonthChartCardNoList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return getMapper().getMonthChartCardNoList(params);
	}
	
	/**
	 * 获取加油卡明细
	 * @author linhui
	 * @create_date 2013-8-24 下午2:30:21
	 * @param params
	 * @return
	 */
	public List<HZ_Gas_Card_Consumption_Log> getCardDetail(Map<String, Object> params,HZ_PageData pageData){
		if(pageData == null)pageData = new HZ_PageData();
		if(params == null)params = new HashMap<String, Object>();
	
		//添加sql条件
		pageData.setTotalSize(getCardDetailCount(params));
		//添加分页功能
		params.put("pageData", pageData);
		return getMapper().getCardDetail(params);
	}
	
	/**
	 * 获取加油卡明细数量
	 * @author linhui
	 * @create_date 2013-8-24 下午2:30:21
	 * @param params
	 * @return
	 */
	public int getCardDetailCount(Map<String, Object> params){
		return getMapper().getCardDetailCount(params);
	}

	/**获取上期卡余额
	 * @author linjian
	 * @create_date 2013-11-7 下午5:21:18
	 * @param params 参数
	 * @return 加油卡的上期卡余额
	 */
	@Override
	public String getpre_balance(String card_no, String pre_month_last_day,
			Integer customer_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("card_no", card_no);
		params.put("pre_month_last_day", pre_month_last_day);
		params.put("customer_id", customer_id);
		return getMapper().getpre_balance(params);
	}
	
	/**获取下载消费主卡下的加油卡
	 * @author linjian
	 * @create_date 2013-11-7 下午6:24:04
	 * @param params
	 * @return 加油卡列表
	 */
	public List<Map<String, Object>> getConsumptionCardNoByMainCard(Map<String, Object> params){
		return getMapper().getConsumptionCardNoByMainCard(params);
	}

	/**统计加油卡信息
	 * @author linjian
	 * @create_date 2013-11-15 下午4:32:38
	 * @param params 统计参数
	 * @return 统计结果
	 */
	@Override
	public Map<String, Object> countCard(Map<String, Object> params) {
		return getMapper().countCard(params);
	}
	
	/**获取单张加油卡的截止时间卡余额
	 * @author linjian
	 * @create_date 2013-12-3 下午5:02:07
	 * @param params 参数
	 * @return 截止时间的加油卡余额
	 */
	public String getMonthLastDayCardBalance(Map<String, Object> params){
		Map<String, Object> map = getMapper().getMonthLastDayCardBalance(params);
		String total_app_num = "0";
		if(map != null && map.containsKey("total_app_num") && map.get("total_app_num") != null){
			total_app_num = map.get("total_app_num").toString();
		}
		String total_money_num = "0";
		if(map != null && map.containsKey("total_money_num") && map.get("total_money_num") != null){
			total_money_num = map.get("total_money_num").toString();
		}
		return HZ_AccountUtil.subtraction(total_app_num, total_money_num, "#.##");
	}
	
	/**获取客户所有加油卡的截止时间的余额
	 * @author linjian
	 * @create_date 2013-12-3 下午5:03:23
	 * @param params 参数
	 * @return 截止时间的加油卡总额
	 */
	public String getMonthLastDayBalance(Map<String, Object> params){
		Map<String, Object> map = getMapper().getMonthLastDayBalance(params);
		String total_app_num = "0";
		if(map != null && map.containsKey("total_app_num") && map.get("total_app_num") != null){
			total_app_num = map.get("total_app_num").toString();
		}
		String total_money_num = "0";
		if(map != null && map.containsKey("total_money_num") && map.get("total_money_num") != null){
			total_money_num = map.get("total_money_num").toString();
		}
		return HZ_AccountUtil.subtraction(total_app_num, total_money_num, "#.##");
	}
}
