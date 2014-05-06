package com.hczd.download.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hczd.download.base.dao.impl.HZ_BaseDao;
import com.hczd.download.base.util.HZ_SQL_ParamsUtil;
import com.hczd.download.card.dao.IHZ_Gas_Card_Consumption_LogDao;
import com.hczd.download.card.mapper.IHZ_Gas_Card_Consumption_LogMapper;
import com.hczd.download.card.module.HZ_Gas_Card_Consumption_Log;

/**
 * 加油卡消费数据数据层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-16 上午9:04:32
 */
@Repository
public class HZ_Gas_Card_Consumption_LogDao extends HZ_BaseDao<HZ_Gas_Card_Consumption_Log, Integer> implements IHZ_Gas_Card_Consumption_LogDao {

	@Autowired
	public HZ_Gas_Card_Consumption_LogDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setMapperClass(IHZ_Gas_Card_Consumption_LogMapper.class);
	}

	/**删除重复消费记录
	 * @author linjian 
	 * @create_date 2013-6-17 下午4:58:12
	 * @param params
	 * @return 重复数量
	 */
	@Override
	public int deleteRepeat(Map<String, Object> params) {
		return this.getMapper().deleteRepeat(params);
	}

	/**重写获取Mapper
	 * @author linjian 
	 * @create_date 2013-6-17 下午5:02:04
	 * @return
	 */
	public IHZ_Gas_Card_Consumption_LogMapper getMapper() {
		return getMapper(IHZ_Gas_Card_Consumption_LogMapper.class);
	}

	/**
	 * 根据车牌号获取最后一条加油记录
	 * @author linjian 
	 * @create_date 2013-7-22 下午5:50:47
	 * @param vehicle_no
	 * @return 消费记录
	 */
	@Override
	public HZ_Gas_Card_Consumption_Log getLast(Map<String, Object> params) {
		if(params != null && !params.containsKey("where"))
			params.put("where", "where 1=1 "+HZ_SQL_ParamsUtil.tran2sql(params));
		return getMapper().getLast(params);
	}
	
	/**
	 * 获取一段时间内的该主卡的所有加油卡消费记录
	 * @author linhui
	 * @create_date 2013-8-19 上午10:20:42
	 * @param mainCardNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<HZ_Gas_Card_Consumption_Log> getListByMainNo(Map<String, Object> map){
		return getMapper().getListByMainNo(map);
	}
	
	/**
	 * 获取一段时间内的该主卡的所有加油卡消费记录数
	 * @author linhui
	 * @create_date 2013-8-19 上午10:20:42
	 * @param mainCardNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int countListByMainNo(Map<String, Object> map){
		return getMapper().countListByMainNo(map);
	}
}
