package com.hczd.download.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hczd.download.base.service.impl.HZ_BaseService;
import com.hczd.download.customer.dao.impl.HZ_CustomerDao;
import com.hczd.download.customer.module.HZ_Customer;
import com.hczd.download.customer.service.IHZ_CustomerService;

/**
 * 客户逻辑层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-29 下午4:03:09
 */
@Service
public class HZ_CustomerService extends HZ_BaseService<HZ_Customer, Integer> implements IHZ_CustomerService {
	//@Autowired
	//private IHZ_CustomerDao hz_customerDao;
	
	@Autowired
	public HZ_CustomerService(HZ_CustomerDao dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}

}
