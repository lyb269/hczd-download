package com.hczd.download.access.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hczd.download.access.dao.impl.HZ_Access_Card_Allocation_RecordDao;
import com.hczd.download.access.module.HZ_Access_Card_Allocation_Record;
import com.hczd.download.access.service.IHZ_Access_Card_Allocation_RecordService;
import com.hczd.download.base.service.impl.HZ_BaseService;

/**
 * 类描述：通行卡分配记录实现类
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-5-15 时间：下午4:44:50
 */
@Service
public class HZ_Access_Card_Allocation_RecordService extends HZ_BaseService<HZ_Access_Card_Allocation_Record, Integer> implements IHZ_Access_Card_Allocation_RecordService {
	
	
	@Autowired
	public HZ_Access_Card_Allocation_RecordService(HZ_Access_Card_Allocation_RecordDao dao) {
		super(dao);
	}
	
}
