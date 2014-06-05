package com.hczd.download.access.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hczd.download.access.dao.IHZ_Access_Card_Consumption_LogDao;
import com.hczd.download.access.mapper.IHZ_Access_Card_Consumption_LogMapper;
import com.hczd.download.access.module.HZ_Access_Card_Consumption_Log;
import com.hczd.download.base.dao.impl.HZ_BaseDao;
import com.hczd.download.base.module.HZ_PageData;
import com.hczd.download.base.util.HZ_SQL_ParamsUtil;

/**
 * 类描述：通行卡消费数据数据层实现类
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-1-3 时间：下午2:00:35
 */
@Repository
public class HZ_Access_Card_Consumption_LogDao extends HZ_BaseDao<HZ_Access_Card_Consumption_Log, Integer> implements IHZ_Access_Card_Consumption_LogDao {

	@Autowired
	public HZ_Access_Card_Consumption_LogDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setMapperClass(IHZ_Access_Card_Consumption_LogMapper.class);
	}

	/**重写获取Mapper
	 * @return
	 */
	public IHZ_Access_Card_Consumption_LogMapper getMapper() {
		return getMapper(IHZ_Access_Card_Consumption_LogMapper.class);
	}
	
	/**
	 * 去除时间段内重复的消费记录
	 * @return 重复数量
	 */
	@Override
	public void deleteRepeat() {
		this.getMapper().deleteRepeat();
	}
	
	/**获取通行卡明细
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-19 时间：上午9:37:18
	 * @param params
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryDetailInfo(Map<String, Object> params,HZ_PageData pageData){
		if(pageData == null)pageData = new HZ_PageData();
		if(params == null)params = new HashMap<String, Object>();
		//数据权限的条件约束
		String check_where = "";
		if(params.containsKey("check_where")){
			check_where = params.get("check_where").toString();
			params.remove("check_where");
		}
		//添加sql条件
		String listWhere = " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params);
		params.remove("order_by");
		String countWhere = " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params);
		
		params.put("where", countWhere + check_where);
		pageData.setTotalSize(getMapper().countDetailInfo(params));
		//添加分页功能
		params.put("where", listWhere + check_where);
		params.put("pageData", pageData);
		return getMapper().queryDetailInfo(params);
	}
	
	/**获取一段时间内的该主卡的所有卡消费记录数
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-20 时间：下午3:40:18
	 * @param map
	 * @return
	 */
	public int countListByMainNo(Map<String, Object> map){
		return getMapper().countListByMainNo(map);
	}
	
	/**获取一段时间内的该主卡的所有卡消费记录
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-20 时间：下午4:36:13
	 * @param map
	 * @return
	 */
	public List<HZ_Access_Card_Consumption_Log> getListByMainNo(Map<String, Object> map){
		return getMapper().getListByMainNo(map);
	}
}
