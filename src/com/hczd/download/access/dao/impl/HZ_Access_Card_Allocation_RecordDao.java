package com.hczd.download.access.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hczd.download.access.dao.IHZ_Access_Card_Allocation_RecordDao;
import com.hczd.download.access.mapper.IHZ_Access_Card_Allocation_RecordMapper;
import com.hczd.download.access.module.HZ_Access_Card_Allocation_Record;
import com.hczd.download.base.dao.impl.HZ_BaseDao;

/**
 * 类描述：通行卡分配记录数据层实现类
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-5-15 时间：下午4:42:33
 */
@Repository
public class HZ_Access_Card_Allocation_RecordDao extends HZ_BaseDao<HZ_Access_Card_Allocation_Record, Integer> implements IHZ_Access_Card_Allocation_RecordDao {

	@Autowired
	public HZ_Access_Card_Allocation_RecordDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setMapperClass(IHZ_Access_Card_Allocation_RecordMapper.class);
	}
	
	public IHZ_Access_Card_Allocation_RecordMapper getMapper() {
		// TODO Auto-generated method stub
		return super.getMapper(IHZ_Access_Card_Allocation_RecordMapper.class);
	}
	
	/**获取一段时间内的该主卡的所有卡分配记录
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-20 时间：下午5:16:26
	 * @param map
	 * @return
	 */
	public List<HZ_Access_Card_Allocation_Record> getListByMainNo(Map<String, Object> map){
		return getMapper().getListByMainNo(map);
	}
	
	/**获取一段时间内的该主卡的所有卡分配记录数
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-20 时间：下午5:08:28
	 * @param map
	 * @return
	 */
	public int countListByMainNo(Map<String, Object> map){
		return getMapper().countListByMainNo(map);
	}

}
