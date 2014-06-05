package com.hczd.download.access.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hczd.download.access.dao.impl.HZ_Access_CardDao;
import com.hczd.download.access.module.HZ_Access_Card;
import com.hczd.download.access.service.IHZ_Access_CardService;
import com.hczd.download.base.service.impl.HZ_BaseService;

/**
 * 类描述：通行卡逻辑层实现类
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-1-3 时间：下午2:08:26
 */
@Service
public class HZ_Access_CardService extends HZ_BaseService<HZ_Access_Card, Integer> implements IHZ_Access_CardService {
	
	@Autowired
	public HZ_Access_CardService(HZ_Access_CardDao dao) {
		super(dao);
	}
}
