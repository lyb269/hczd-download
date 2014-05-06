package com.hczd.download.card.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hczd.download.base.dao.impl.HZ_BaseDao;
import com.hczd.download.card.dao.IHZ_Gas_Card_HistoryDao;
import com.hczd.download.card.mapper.IHZ_Gas_Card_HistoryMapper;
import com.hczd.download.card.module.HZ_Gas_Card_History;

/**加油卡历史归属记录数据层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-6 下午6:22:15
 */
@Repository
public class HZ_Gas_Card_HistoryDao extends HZ_BaseDao<HZ_Gas_Card_History, Integer> implements IHZ_Gas_Card_HistoryDao {

	@Autowired
	public HZ_Gas_Card_HistoryDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setMapperClass(IHZ_Gas_Card_HistoryMapper.class);
	}

	/*** 根据加油卡卡号、时间点获取历史归属记录
	 * @author linjian 
	 * @create_date 2013-7-18 下午2:19:08
	 * @param params
	 * @return
	 */
	@Override
	public HZ_Gas_Card_History getHistoryByCardNoWithTime(
			Map<String, Object> params) {
		return getMapper().getHistoryByCardNoWithTime(params);
	}
	
	public IHZ_Gas_Card_HistoryMapper getMapper() {
		// TODO Auto-generated method stub
		return super.getMapper(IHZ_Gas_Card_HistoryMapper.class);
	}

	/**根据月份获取加油卡历史记录
	 * @author linjian 
	 * @create_date 2013-7-26 下午3:39:35
	 * @param params
	 * @return 加油卡的历史记录
	 */
	@Override
	public HZ_Gas_Card_History getHistoryByCardAndMonth(
			Map<String, Object> params) {
		return getMapper().getHistoryByCardAndMonth(params);
	}
	
	/**
	 * 获取未上传行驶证的加油卡
	 * @author linhui
	 * @create_date 2013-12-4 下午1:50:26
	 * @param card_nos
	 * @return
	 */
	public List<HZ_Gas_Card_History> getNotDrivingLicense(String card_nos){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("card_nos", card_nos);
		return getMapper().getNotDrivingLicense(params);
	}

}
