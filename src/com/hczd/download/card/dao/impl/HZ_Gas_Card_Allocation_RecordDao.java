package com.hczd.download.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hczd.download.base.dao.impl.HZ_BaseDao;
import com.hczd.download.card.dao.IHZ_Gas_Card_Allocation_RecordDao;
import com.hczd.download.card.mapper.IHZ_Gas_Card_Allocation_RecordMapper;
import com.hczd.download.card.module.HZ_Gas_Card_Allocation_Record;

/**
 * 分配记录数据层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-15 下午2:13:33
 */
@Repository
public class HZ_Gas_Card_Allocation_RecordDao extends HZ_BaseDao<HZ_Gas_Card_Allocation_Record, Integer> implements IHZ_Gas_Card_Allocation_RecordDao {

	@Autowired
	public HZ_Gas_Card_Allocation_RecordDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setMapperClass(IHZ_Gas_Card_Allocation_RecordMapper.class);
	}

	/**获取最后一笔分配记录
	 * @author linjian 
	 * @create_date 2013-7-23 下午2:22:56
	 * @param params
	 * @return 分配记录
	 */
	@Override
	public HZ_Gas_Card_Allocation_Record getLastAllocation(Map<String, Object> params) {
		return getMapper().getLastAllocation(params);
	}
	
	/**分配记录mapper
	 * @author linjian 
	 * @create_date 2013-7-23 下午2:39:07
	 * @return
	 */
	public IHZ_Gas_Card_Allocation_RecordMapper getMapper() {
		// TODO Auto-generated method stub
		return super.getMapper(IHZ_Gas_Card_Allocation_RecordMapper.class);
	}
	
	/**
	 * 获取一段时间内的该主卡的所有加油卡分配记录
	 * @author linhui
	 * @create_date 2013-8-19 上午10:20:42
	 * @param mainCardNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<HZ_Gas_Card_Allocation_Record> getListByMainNo(Map<String, Object> map){
		return getMapper().getListByMainNo(map);
	}
	
	/**
	 * 获取一段时间内的该主卡的所有加油卡分配记录数
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

	/**统计可客户端申请中的数量
	 * @author linjian
	 * @create_date 2013-11-25 上午11:31:24
	 * @return 数量
	 */
	@Override
	public int countClientApp() {
		return getMapper().countClientApp();
	}

}
