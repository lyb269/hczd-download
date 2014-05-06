package com.hczd.download.base.mapper;

import java.util.List;
import java.util.Map;

import com.hczd.download.base.module.HZ_PageData;


/**
 * 基础类映射
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-22 上午9:48:17
 * @param <T> 实体类泛型
 * @param <K> 主键泛型
 */
public interface IHZ_BaseMapper<T,K> {
	public void insert(T entity);
	public int insertList(List<T> list);
	public void delete(K id);
	public void deleteList(Map<String, Object> params);
	public void update(T entity);
	public List<T> select();
	public List<T> selectByParams(Map<String, Object> parmas);
	public List<T> selectByPage(HZ_PageData pageData);
	public List<T> selectPageByParams(Map<String, Object> parmas);
	public T get(K id);
	public void updateField(Map<String, Object> params);
	public Integer count(Map<String, Object> params);
	public Double sum(Map<String, Object> params);
	public Object getScale(Map<String, Object> params);
	public List<Map<String, Object>> getScaleList(Map<String, Object> params);
	public List<T> listCustom(Map<String, Object> params);
	public Integer countCustom(Map<String, Object> params);
}
