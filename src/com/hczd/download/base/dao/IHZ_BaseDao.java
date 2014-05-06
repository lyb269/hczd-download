package com.hczd.download.base.dao;

import java.util.List;
import java.util.Map;

import com.hczd.download.base.module.HZ_PageData;

/**
 * 基础数据操作接口
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-22 上午9:54:35
 * @param <T> 实体类泛型
 * @param <K> 实体类主键泛型
 */
public interface IHZ_BaseDao<T,K> {
	public T save(T entity);
	public int saveList(List<T> list);
	public void delete(K id);
	public void deleteList(Map<String, Object> params);
	public T update(T entity);
	public T merge(T entity);
	public T get(K id);
	public T get(Map<String, Object> params);
	public List<T> list();
	public List<T> list(Map<String, Object> params);
	public List<T> listByPage(HZ_PageData pageData);
	public List<T> listPageByParams(Map<String, Object> params, HZ_PageData pageData);
	public void updateField(Map<String, Object> params);
	public Integer count(Map<String, Object> params);
	public Double sum(Map<String, Object> params);
	public Object getScale(Map<String, Object> params);
	public List<Map<String, Object>> getScaleList(Map<String, Object> params);
	public List<T> listCustom(Map<String, Object> params, HZ_PageData pageData);
	public List<T> listCustom(Map<String, Object> params);
}
