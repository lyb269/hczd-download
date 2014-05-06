package com.hczd.download.customer.mapper;

import java.util.List;
import java.util.Map;

import com.hczd.download.base.mapper.IHZ_BaseMapper;
import com.hczd.download.customer.module.HZ_Customer;

/**
 * 客户实体Mapper
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-29 下午3:43:55
 */
public interface IHZ_CustomerMapper extends IHZ_BaseMapper<HZ_Customer, Integer> {

	/**
	 * 基本账户列表
	 * @author linhui
	 * @create_date 2013-7-22 上午9:28:34
	 * @param params
	 * @param pageData
	 */
	public List<HZ_Customer> baseAccountListPageByParams(Map<String, Object> params);
	
	
	/**
	 * 积分账户列表
	 * @author linhui
	 * @create_date 2013-7-22 上午10:36:34
	 * @param params
	 * @param pageData
	 */
	public List<HZ_Customer> integralAccountListPageByParams(Map<String, Object> params);
	
	/**
	 * 信用账户列表
	 * @author linhui
	 * @create_date 上午11:08:47
	 * @param params
	 * @return
	 */
	public List<HZ_Customer> creditAccountListPageByParams(Map<String, Object> params);
	
	/**
	 * 获取资金对账统计
	 * @author linhui
	 * @create_date 2013-9-3 下午6:14:19
	 * @return
	 */
	public String customerAccountCheckingAll(Map<String, Object> params);
	
	
	
	/**
	 * 获取大车客户
	 * @author linhui
	 * @create_date 2013-10-21 上午9:15:11
	 * @param params
	 * @return
	 */
	public List<HZ_Customer> companyCarCustomer(Map<String, Object> params);
	
	/**
	 * 计算个数
	 * @author linhui
	 * @create_date 2013-10-21 上午9:40:41
	 * @param params
	 * @return
	 */
	public int countCompanyCarCustomer(Map<String, Object> params);
	
	/**
	 * 获取指定区号的最大客户号
	 * @author linhui
	 * @create_date 2013-11-29 下午1:58:45
	 * @param areaNo
	 * @return
	 */
	public String selectMaxAreaCustomerCode(String areaNo);
	
	/**
	 * 统计使用监控的客户数
	 * @author  liuyj
	 * @create_date 日期 2014-4-24 时间: 下午2:44:12
	 * @version 1.0
	 * @param params
	 * @return
	 */
	public Integer monitorCount(Map<String, Object> params) ;
}
