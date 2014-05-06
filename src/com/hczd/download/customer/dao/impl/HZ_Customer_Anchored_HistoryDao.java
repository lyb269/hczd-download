package com.hczd.download.customer.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hczd.download.base.dao.impl.HZ_BaseDao;
import com.hczd.download.customer.dao.IHZ_Customer_Anchored_HistoryDao;
import com.hczd.download.customer.mapper.IHZ_Customer_Anchored_HistoryMapper;
import com.hczd.download.customer.module.HZ_Customer_Anchored_History;

/**
 * 客户挂靠历史数据层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-7-9 下午4:38:59
 */
@Repository
public class HZ_Customer_Anchored_HistoryDao extends HZ_BaseDao<HZ_Customer_Anchored_History, Integer> implements IHZ_Customer_Anchored_HistoryDao {

	@Autowired
	public HZ_Customer_Anchored_HistoryDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setMapperClass(IHZ_Customer_Anchored_HistoryMapper.class);
	}

	/**根据时间点获取历史挂靠关系
	 * @author linjian 
	 * @create_date 2013-7-10 下午4:36:56
	 * @param params
	 * @return 挂靠关系
	 */
	@Override
	public HZ_Customer_Anchored_History getAnchoredByTime(String time,
			Integer customer_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer_id", customer_id);
		params.put("time", time);
		return getMapper().getAnchoredByTime(params);
	}
	
	/**
	 * 获取Mapper
	 * @author linjian 
	 * @create_date 2013-7-10 下午4:38:42
	 * @return
	 */
	public IHZ_Customer_Anchored_HistoryMapper getMapper() {
		// TODO Auto-generated method stub
		return super.getMapper(IHZ_Customer_Anchored_HistoryMapper.class);
	}

}
