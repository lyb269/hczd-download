package com.hczd.download.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hczd.download.base.dao.impl.HZ_Dict_TypeDao;
import com.hczd.download.base.module.HZ_Dict_Type;
import com.hczd.download.base.service.IHZ_Dict_TypeService;

/**
 * 数据字典类型逻辑层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-29 上午10:21:21
 */
@Service
public class HZ_Dict_TypeService extends HZ_BaseService<HZ_Dict_Type, Integer> implements IHZ_Dict_TypeService {

	@Autowired
	public HZ_Dict_TypeService(HZ_Dict_TypeDao dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}
	
}
