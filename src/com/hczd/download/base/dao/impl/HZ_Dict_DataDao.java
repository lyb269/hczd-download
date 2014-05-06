package com.hczd.download.base.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hczd.download.base.dao.IHZ_Dict_DataDao;
import com.hczd.download.base.mapper.IHZ_Dict_DataMapper;
import com.hczd.download.base.module.HZ_Dict_Data;

/**
 * 数据字典数据层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-29 上午9:50:15
 */
@Repository
public class HZ_Dict_DataDao extends HZ_BaseDao<HZ_Dict_Data, Integer> implements IHZ_Dict_DataDao {

	@Autowired
	public HZ_Dict_DataDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setMapperClass(IHZ_Dict_DataMapper.class);
	}

	/**根据扩展名获取数据字典数据
	 * @author linjian 
	 * @create_date 2013-7-19 下午7:48:45
	 * @param extension
	 * @return
	 */
	@Override
	public List<HZ_Dict_Data> getDataByExtension(String extension) {
		// TODO Auto-generated method stub
		return getMapper().getDataByExtension(extension);
	}
	
	/**获取数据字典的Mapper
	 * @author linjian 
	 * @create_date 2013-7-19 下午7:51:30
	 * @return
	 */
	public IHZ_Dict_DataMapper getMapper() {
		// TODO Auto-generated method stub
		return super.getMapper(IHZ_Dict_DataMapper.class);
	}
	
	/**
	 * 根据类型id删除数据
	 * @author linhui
	 * @create_date 2013-10-22 上午9:03:03
	 * @param id
	 */
	public void deleteByTypeId(Integer id){
		getMapper().deleteByTypeId(id);
	}

}
