package com.hczd.download.card.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hczd.download.base.service.impl.HZ_BaseService;
import com.hczd.download.card.dao.impl.HZ_Gas_StationDao;
import com.hczd.download.card.module.HZ_Gas_Station;
import com.hczd.download.card.service.IHZ_Gas_StationService;

/**
 * 加油站逻辑层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-17 上午11:16:17
 */
@Service
public class HZ_Gas_StatsionService extends HZ_BaseService<HZ_Gas_Station, Integer> implements IHZ_Gas_StationService {

	@Autowired
	public HZ_Gas_StatsionService(HZ_Gas_StationDao dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}

}
