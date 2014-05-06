package com.hczd.download.base.mapper;

import java.util.List;

import com.hczd.download.base.module.HZ_Dict_Data;

/**
 * 数据字典数据Mapper
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-29 上午9:46:28
 */
public interface IHZ_Dict_DataMapper extends IHZ_BaseMapper<HZ_Dict_Data, Integer> {
	/**根据扩展名获取数据字典数据
	 * @author linjian 
	 * @create_date 2013-7-19 下午7:48:45
	 * @param extension
	 * @return
	 */
	public List<HZ_Dict_Data> getDataByExtension(String extension);
	
	/**
	 * 根据类型id删除数据
	 * @author linhui
	 * @create_date 2013-10-22 上午9:03:03
	 * @param id
	 */
	public void deleteByTypeId(Integer id);
}
