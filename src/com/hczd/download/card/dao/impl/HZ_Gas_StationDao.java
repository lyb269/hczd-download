package com.hczd.download.card.dao.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hczd.download.base.dao.impl.HZ_BaseDao;
import com.hczd.download.card.dao.IHZ_Gas_StationDao;
import com.hczd.download.card.mapper.IHZ_Gas_StationMapper;
import com.hczd.download.card.module.HZ_Gas_Station;

/**
 * 加油站数据层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-17 上午11:13:20
 */
@Repository
public class HZ_Gas_StationDao extends HZ_BaseDao<HZ_Gas_Station, Integer> implements IHZ_Gas_StationDao {

	@Autowired
	public HZ_Gas_StationDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setMapperClass(IHZ_Gas_StationMapper.class);
	}

}
