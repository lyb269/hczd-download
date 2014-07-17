package com.hczd.download.access.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hczd.download.access.dao.impl.HZ_Main_Access_Card_AccountDao;
import com.hczd.download.access.module.HZ_Main_Access_Card_Account;
import com.hczd.download.access.service.IHZ_Main_Access_Card_AccountService;
import com.hczd.download.base.service.impl.HZ_BaseService;

/**
 * 类描述：通行卡主卡逻辑层实现类
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2013-12-31 时间：下午2:33:13
 */
@Service
public class HZ_Main_Access_Card_AccountService extends HZ_BaseService<HZ_Main_Access_Card_Account, Integer> implements IHZ_Main_Access_Card_AccountService {
	
	@Autowired
	public HZ_Main_Access_Card_AccountService(HZ_Main_Access_Card_AccountDao dao) {
		super(dao);
	}
	
	
}
