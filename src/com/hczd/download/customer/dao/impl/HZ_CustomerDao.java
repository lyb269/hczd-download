package com.hczd.download.customer.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hczd.download.base.dao.impl.HZ_BaseDao;
import com.hczd.download.base.module.HZ_PageData;
import com.hczd.download.base.util.HZ_SQL_ParamsUtil;
import com.hczd.download.customer.dao.IHZ_CustomerDao;
import com.hczd.download.customer.mapper.IHZ_CustomerMapper;
import com.hczd.download.customer.module.HZ_Customer;

/**
 * 客户信息数据层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-29 下午3:53:22
 */
@Repository
public class HZ_CustomerDao extends HZ_BaseDao<HZ_Customer, Integer> implements IHZ_CustomerDao {

	@Autowired
	public HZ_CustomerDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setMapperClass(IHZ_CustomerMapper.class);
	}
	
	/**
	 * 获取Mapper
	 * @author linhui 
	 * @create_date 2013-7-22 上午9:53:42
	 * @return
	 */
	public IHZ_CustomerMapper getMapper() {
		// TODO Auto-generated method stub
		return super.getMapper(IHZ_CustomerMapper.class);
	}

	/**
	 * 基本账户列表
	 * @author linhui
	 * @create_date 2013-7-22 上午9:28:34
	 * @param params
	 * @param pageData
	 */
	public List<HZ_Customer> baseAccountListPageByParams(Map<String, Object> params,HZ_PageData pageData){
		if(pageData == null)pageData = new HZ_PageData();
		if(params == null)params = new HashMap<String, Object>();
	
		//添加sql条件
		params.put("where", " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params));
		pageData.setTotalSize(count(params));
		//添加分页功能
		params.put("pageData", pageData);
		return getMapper().baseAccountListPageByParams(params);
	}
	
	/**
	 * 积分账户列表
	 * @author linhui
	 * @create_date 2013-7-22 上午10:36:34
	 * @param params
	 * @param pageData
	 */
	public List<HZ_Customer> integralAccountListPageByParams(Map<String, Object> params,HZ_PageData pageData){
		if(pageData == null)pageData = new HZ_PageData();
		if(params == null)params = new HashMap<String, Object>();
	
		//添加sql条件
		params.put("where", " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params));
		pageData.setTotalSize(count(params));
		//添加分页功能
		params.put("pageData", pageData);
		return getMapper().integralAccountListPageByParams(params);
	}
	
	/**
	 * 信用账户列表
	 * @author linhui
	 * @create_date 上午11:09:35
	 * @param params
	 * @param pageData
	 * @return
	 */
	public List<HZ_Customer> creditAccountListPageByParams(Map<String, Object> params,HZ_PageData pageData){
		if(pageData == null)pageData = new HZ_PageData();
		if(params == null)params = new HashMap<String, Object>();
	
		//添加sql条件
		params.put("where", " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params));
		pageData.setTotalSize(count(params));
		//添加分页功能
		params.put("pageData", pageData);
		return getMapper().integralAccountListPageByParams(params);
	}
	
	/**
	 * 获取资金对账统计
	 * @author linhui
	 * @create_date 2013-9-3 下午6:14:19
	 * @return
	 */
	public String customerAccountCheckingAll(Map<String, Object> params){
		return getMapper().customerAccountCheckingAll(params);
	}
	
	/**
	 * 获取大车客户
	 * @author linhui
	 * @create_date 2013-10-21 上午9:15:11
	 * @param params
	 * @return
	 */
	public List<HZ_Customer> companyCarCustomer(Map<String, Object> params,HZ_PageData pageData){
		if(pageData == null)pageData = new HZ_PageData();
		if(params == null)params = new HashMap<String, Object>();
	
		//添加sql条件
		//params.put("where", " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params));
		pageData.setTotalSize(countCompanyCarCustomer(params));
		//添加分页功能
		params.put("pageData", pageData);
		return getMapper().companyCarCustomer(params);
	}
	
	/**
	 * 计算个数
	 * @author linhui
	 * @create_date 2013-10-21 上午9:40:07
	 * @param params
	 * @return
	 */
	public int countCompanyCarCustomer(Map<String, Object> params){
		return getMapper().countCompanyCarCustomer(params);
	}
	
	/**
	 * 获取指定区号的最大客户号
	 * @author linhui
	 * @create_date 2013-11-29 下午1:58:45
	 * @param areaNo
	 * @return
	 */
	public String selectMaxAreaCustomerCode(String areaNo){
		return this.getMapper().selectMaxAreaCustomerCode(areaNo);
	}

	/**
	 * 统计使用监控的客户数
	 * @author  liuyj
	 * @create_date 日期 2014-4-24 时间: 下午2:44:12
	 * @version 1.0
	 * @param params
	 * @return
	 */
	public Integer monitorCount(Map<String, Object> params) {
		return getMapper().monitorCount(params);
	}
}
