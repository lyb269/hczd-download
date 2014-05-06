package com.hczd.download.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hczd.download.base.dao.IHZ_Dict_DataDao;
import com.hczd.download.base.dao.impl.HZ_Dict_DataDao;
import com.hczd.download.base.module.HZ_Dict_Data;
import com.hczd.download.base.service.IHZ_Dict_DataService;

/**
 * 数据字典数据逻辑层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-29 上午10:23:41
 */
@Service
public class HZ_Dict_DataService extends HZ_BaseService<HZ_Dict_Data, Integer> implements IHZ_Dict_DataService {
	@Autowired
	private IHZ_Dict_DataDao hz_dict_dataDao;
	
	@Autowired
	public HZ_Dict_DataService(HZ_Dict_DataDao dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}

	/**根据扩展名获取数据字典数据
	 * @author linjian 
	 * @create_date 2013-7-19 下午7:48:45
	 * @param extension
	 * @return
	 */
	@Override
	public List<HZ_Dict_Data> getDataByExtension(String extension) {
		// TODO Auto-generated method stub
		return hz_dict_dataDao.getDataByExtension(extension);
	}
	
	/**
	 * 根据类型id删除数据
	 * @author linhui
	 * @create_date 2013-10-22 上午9:03:03
	 * @param id
	 */
	public void deleteByTypeId(Integer id){
		hz_dict_dataDao.deleteByTypeId(id);
	}

}
