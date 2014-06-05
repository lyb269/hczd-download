package com.hczd.download.access.dao.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hczd.download.access.dao.IHZ_Access_Card_HistoryDao;
import com.hczd.download.access.mapper.IHZ_Access_Card_HistoryMapper;
import com.hczd.download.access.module.HZ_Access_Card_History;
import com.hczd.download.base.dao.impl.HZ_BaseDao;

/**
 * 类描述：通行卡历史记录数据层实现类
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-1-3 时间：下午1:58:49
 */
@Repository
public class HZ_Access_Card_HistoryDao extends HZ_BaseDao<HZ_Access_Card_History, Integer> implements IHZ_Access_Card_HistoryDao {

	@Autowired
	public HZ_Access_Card_HistoryDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setMapperClass(IHZ_Access_Card_HistoryMapper.class);
	}

}
