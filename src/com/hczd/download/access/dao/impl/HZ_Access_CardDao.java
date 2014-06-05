package com.hczd.download.access.dao.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hczd.download.access.dao.IHZ_Access_CardDao;
import com.hczd.download.access.mapper.IHZ_Access_CardMapper;
import com.hczd.download.access.module.HZ_Access_Card;
import com.hczd.download.base.dao.impl.HZ_BaseDao;

/**
 * 类描述：通行卡数据层实现类
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-1-3 时间：下午1:57:55
 */
@Repository
public class HZ_Access_CardDao extends HZ_BaseDao<HZ_Access_Card, Integer> implements IHZ_Access_CardDao {

	@Autowired
	public HZ_Access_CardDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setMapperClass(IHZ_Access_CardMapper.class);
	}

}
