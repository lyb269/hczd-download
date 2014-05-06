package com.hczd.download.card.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hczd.download.base.service.impl.HZ_BaseService;
import com.hczd.download.card.dao.IHZ_Gas_Card_Allocation_RecordDao;
import com.hczd.download.card.dao.impl.HZ_Gas_Card_Allocation_RecordDao;
import com.hczd.download.card.module.HZ_Gas_Card_Allocation_Record;
import com.hczd.download.card.service.IHZ_Gas_Card_Allocation_RecordService;

/**
 * 分配记录逻辑层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-15 下午2:15:56
 */
@Service
public class HZ_Gas_Card_Allocation_RecordService extends HZ_BaseService<HZ_Gas_Card_Allocation_Record, Integer> implements IHZ_Gas_Card_Allocation_RecordService {
	@Autowired
	private IHZ_Gas_Card_Allocation_RecordDao hz_gas_card_allocation_recordDao;
	
	@Autowired
	public HZ_Gas_Card_Allocation_RecordService(HZ_Gas_Card_Allocation_RecordDao dao) {
		super(dao);
	}
	
	/****
	 * 重写保存分配申请
	 */
	@Override
	@Transactional
	public HZ_Gas_Card_Allocation_Record save(HZ_Gas_Card_Allocation_Record entity) {
		
		super.save(entity);
		
		return entity;
	}
	

	

	/**修改分配记录状态
	 * @author linjian 
	 * @create_date 2013-6-15 下午2:21:24
	 * @param status
	 * @param id
	 * @return 修改结果
	 */
	@Override
	public int updateStatus(String status, Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("update_status", status);
		params.put("where_id", id);
		hz_gas_card_allocation_recordDao.updateField(params);
		return 0;
	}


	/**获取最后一笔分配记录
	 * @author linjian 
	 * @create_date 2013-7-23 下午2:22:56
	 * @param card_no
	 * @return 分配记录
	 */
	@Override
	public HZ_Gas_Card_Allocation_Record getLastAllocation(String card_no) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("card_no", card_no);
		return hz_gas_card_allocation_recordDao.getLastAllocation(params);
	}

	/**添加分配记录，无任何业务操作
	 * @author linjian
	 * @create_date 2013-9-10 下午3:05:28
	 * @param record 
	 * @return 分配记录
	 */
	@Override
	public HZ_Gas_Card_Allocation_Record add(
			HZ_Gas_Card_Allocation_Record record) {
		super.save(record);
		return record;
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
		return hz_gas_card_allocation_recordDao.getListByMainNo(map);
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
		return hz_gas_card_allocation_recordDao.countListByMainNo(map);
	}

	/**统计可客户端申请中的数量
	 * @author linjian
	 * @create_date 2013-11-25 上午11:31:24
	 * @return 数量
	 */
	@Override
	public int countClientApp() {
		// TODO Auto-generated method stub
		return hz_gas_card_allocation_recordDao.countClientApp();
	}

	
}
