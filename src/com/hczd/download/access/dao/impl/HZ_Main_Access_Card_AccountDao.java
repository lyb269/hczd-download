package com.hczd.download.access.dao.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hczd.download.access.dao.IHZ_Main_Access_Card_AccountDao;
import com.hczd.download.access.mapper.IHZ_Main_Access_Card_AccountMapper;
import com.hczd.download.access.module.HZ_Main_Access_Card_Account;
import com.hczd.download.base.dao.impl.HZ_BaseDao;

/**
 * 类描述：通行卡主卡数据层实现类
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2013-12-31 时间：下午2:24:04
 */
@Repository
public class HZ_Main_Access_Card_AccountDao extends HZ_BaseDao<HZ_Main_Access_Card_Account, Integer> implements IHZ_Main_Access_Card_AccountDao {
	
	@Autowired
	public HZ_Main_Access_Card_AccountDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setMapperClass(IHZ_Main_Access_Card_AccountMapper.class);
	}
}
