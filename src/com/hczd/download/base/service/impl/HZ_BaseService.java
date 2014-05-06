package com.hczd.download.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hczd.download.base.dao.impl.HZ_BaseDao;
import com.hczd.download.base.module.HZ_PageData;
import com.hczd.download.base.service.IHZ_BaseService;

/**
 * 基础逻辑层实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-22 上午9:51:24
 * @param <T> 实体类泛型
 * @param <K> 主键泛型
 */
public class HZ_BaseService<T,K> implements IHZ_BaseService<T, K> {
	
	private HZ_BaseDao<T, K> dao;
	//private HZ_BaseService(){}
	public HZ_BaseService(HZ_BaseDao<T, K> dao){
		this.dao = dao;
	}
	public T save(T entity) {
		// TODO Auto-generated method stub
		return dao.save(entity);
	}

	public void delete(K id) {
		dao.delete(id);
	}
	public void deleteList(Map<String, Object> params){
		dao.deleteList(params);
	}

	public T update(T entity) {
		// TODO Auto-generated method stub
		return dao.update(entity);
	}
	
	@Override
	public T merge(T entity) {
		// TODO Auto-generated method stub
		return dao.merge(entity);
	}

	public T get(K id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}

	public List<T> list() {
		// TODO Auto-generated method stub
		return dao.list();
	}
	@Override
	public List<T> list(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dao.list(params);
	}
	@Override
	public List<T> listByPage(HZ_PageData pageData) {
		// TODO Auto-generated method stub
		return dao.listByPage(pageData);
	}
	@Override
	public Integer count(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dao.count(params);
	}
	@Override
	public Double sum(Map<String, Object> params) {
		// TODO Auto-generated method stub
		Double sum = dao.sum(params);
		return sum==null?0d:sum;
	}
	@Override
	public List<T> listPageByParams(Map<String, Object> params,
			HZ_PageData pageData) {
		// TODO Auto-generated method stub
		return dao.listPageByParams(params, pageData);
	}
	@Override
	public void updateField(Map<String, Object> params) {
		 dao.updateField(params);
	}
	@Override
	public Object getScale(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dao.getScale(params);
	}
	@Override
	public T get(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dao.get(params);
	}
	@Override
	public int saveList(List<T> list) {
		if(list != null && list.size() > 0)
			return dao.saveList(list);
		else
			return 0;
	}
	@Override
	public List<Map<String, Object>> getScaleList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dao.getScaleList(params);
	}
	@Override
	public List<Object> getScaleListOne(Map<String, Object> params) {
		List<Map<String, Object>> list = dao.getScaleList(params);
		List<Object> list_obj = new ArrayList<Object>();
		for (Map<String, Object> map : list) {
			if(map.containsKey("rs"))
				list_obj.add(map.get("rs"));
		}
		return list_obj;
	}
	@Override
	public List<T> listCustom(Map<String, Object> params, HZ_PageData pageData) {
		return dao.listCustom(params, pageData);
	}
	
	@Override
	public List<T> listCustom(Map<String, Object> params) {
		return dao.listCustom(params);
	}

}
