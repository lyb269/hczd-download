package com.hczd.download.card.service;

import java.util.List;
import java.util.Map;

import com.hczd.download.base.service.IHZ_BaseService;
import com.hczd.download.card.module.HZ_Gas_Card_Allocation_Record;

/**
 * 分配记录逻辑层接口
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-15 下午2:14:41
 */
public interface IHZ_Gas_Card_Allocation_RecordService extends IHZ_BaseService<HZ_Gas_Card_Allocation_Record, Integer> {
	
	/**添加分配记录，无任何业务操作
	 * @author linjian
	 * @create_date 2013-9-10 下午3:05:28
	 * @param record 
	 * @return 分配记录
	 */
	public HZ_Gas_Card_Allocation_Record add(HZ_Gas_Card_Allocation_Record record);
	/**修改分配记录状态
	 * @author linjian 
	 * @create_date 2013-6-15 下午2:21:24
	 * @param status
	 * @param id
	 * @return 修改结果
	 */
	public int updateStatus(String status,Integer id);
	
	
	/**获取最后一笔分配记录
	 * @author linjian 
	 * @create_date 2013-7-23 下午2:22:56
	 * @param card_no
	 * @return 分配记录
	 */
	public HZ_Gas_Card_Allocation_Record getLastAllocation(String card_no);
	
	
	/**
	 * 获取一段时间内的该主卡的所有加油卡分配记录
	 * @author linhui
	 * @create_date 2013-8-19 上午10:20:42
	 * @param mainCardNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<HZ_Gas_Card_Allocation_Record> getListByMainNo(Map<String, Object> map);
	
	/**
	 * 获取一段时间内的该主卡的所有加油卡分配记录数
	 * @author linhui
	 * @create_date 2013-8-19 上午10:20:42
	 * @param mainCardNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int countListByMainNo(Map<String, Object> map);
	
	
	/**统计可客户端申请中的数量
	 * @author linjian
	 * @create_date 2013-11-25 上午11:31:24
	 * @return 数量
	 */
	public int countClientApp();
	
}
