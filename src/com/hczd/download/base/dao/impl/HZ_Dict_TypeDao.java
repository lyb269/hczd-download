package com.hczd.download.base.dao.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hczd.download.base.dao.IHZ_Dict_TypeDao;
import com.hczd.download.base.mapper.IHZ_Dict_TypeMapper;
import com.hczd.download.base.module.HZ_Dict_Type;

/**
 * 数据字典类型数据层实现
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-29 上午9:54:45
 */
@Repository
public class HZ_Dict_TypeDao extends HZ_BaseDao<HZ_Dict_Type, Integer> implements IHZ_Dict_TypeDao {

	@Autowired
	public HZ_Dict_TypeDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setMapperClass(IHZ_Dict_TypeMapper.class);
	}

}
