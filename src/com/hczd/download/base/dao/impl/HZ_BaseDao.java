package com.hczd.download.base.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import com.hczd.download.base.dao.IHZ_BaseDao;
import com.hczd.download.base.mapper.IHZ_BaseMapper;
import com.hczd.download.base.module.HZ_PageData;
import com.hczd.download.base.util.HZ_SQL_ParamsUtil;


/**
 * 公共接口实现类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-22 上午10:04:19
 * @param <T> 实体类泛型
 * @param <K> 主键泛型
 */
public class HZ_BaseDao<T,K> extends SqlSessionTemplate implements IHZ_BaseDao<T,K> {
//	@Autowired
//	protected SqlSession sqlSession;
	
	private Class<? extends IHZ_BaseMapper<T, K>> mapperClass; 
	
	private IHZ_BaseMapper<T, K> getMapper(){
		return this.getMapper(mapperClass);
	}
	
	public void setMapperClass(Class<? extends IHZ_BaseMapper<T, K>> mapperClass) {
		this.mapperClass = mapperClass;
	}

	public HZ_BaseDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		// TODO Auto-generated constructor stub
	}

	public T save(T entity) {
		// TODO Auto-generated method stub
		getMapper().insert(entity);
		return entity;
	}
	
	@Override
	public int saveList(List<T> list) {
		// TODO Auto-generated method stub
		return getMapper().insertList(list);
	}

	public void delete(K id) {
		// TODO Auto-generated method stub
		getMapper().delete(id);
	}
	
	@Override
	public void deleteList(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
		if(!params.containsKey("where")){
			//添加sql条件
			params.put("where", " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params));
		}
		getMapper().deleteList(params);
	}

	public T update(T entity) {
		// TODO Auto-generated method stub
		getMapper().update(entity);
		return null;
	}
	
	@Override
	public T merge(T entity) {
		getMapper().insert(entity);
		return null;
	}

	public T get(K id) {
		return getMapper().get(id);
	}

	public List<T> list() {
		// TODO Auto-generated method stub
		return getMapper().select();
	}
	
	@Override
	public List<T> list(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
		if(!params.containsKey("where")){
			//添加sql条件
			params.put("where", " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params));
		}
		return getMapper().selectByParams(params);
	}

	@Override
	public List<T> listByPage(HZ_PageData pageData) {
		if(pageData == null)pageData = new HZ_PageData();
		pageData.setTotalSize(count(null));
		return getMapper().selectByPage(pageData);
	}
	
	@Override
	public List<T> listPageByParams(Map<String, Object> params, HZ_PageData pageData) {
		if(pageData == null)pageData = new HZ_PageData();
		if(params == null)params = new HashMap<String, Object>();
	
		//添加sql条件
		String listWhere = " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params);
		params.remove("order_by");
		String countWhere = " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params);
		params.put("where", countWhere);
		pageData.setTotalSize(count(params));
		//添加分页功能
		params.put("where", listWhere);
		params.put("pageData", pageData);
		return getMapper().selectPageByParams(params);
	}

	@Override
	public Integer count(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
		if(!params.containsKey("where")){
			//添加sql条件
			params.put("where", " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params));
		}
		return getMapper().count(params);
	}
	
	@Override
	public Double sum(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
		//添加sql条件
		params.put("where", " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params));
		Double sum = getMapper().sum(params);
		return sum==null?0d:sum;
	}

	@Override
	public void updateField(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
			//添加更新sql条件
		String set_sql = HZ_SQL_ParamsUtil.tran2updateSQL(params);
		if(StringUtils.isNotBlank(set_sql)){
			params.put("set_sql", set_sql);
		}
		getMapper().updateField(params);
		
	}

	@Override
	public Object getScale(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
		if(!params.containsKey("where")){
			//添加sql条件
			params.put("where", " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params));
		}
		return getMapper().getScale(params);
	}

	@Override
	public List<Map<String, Object>> getScaleList(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
		if(!params.containsKey("where")){
			//添加sql条件
			params.put("where", " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params));
		}
		return getMapper().getScaleList(params);
	}
	
	@Override
	public T get(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
		if(!params.containsKey("where")){
			//添加sql条件
			params.put("where", " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params));
		}
		List<T> list = getMapper().selectByParams(params);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<T> listCustom(Map<String, Object> params,HZ_PageData pageData) {
		if(pageData == null)pageData = new HZ_PageData();
		if(params == null)params = new HashMap<String, Object>();
		//数据权限的条件约束
		String check_where = "";
		if(params.containsKey("check_where")){
			check_where = params.get("check_where").toString();
			params.remove("check_where");
		}
		//添加sql条件
		String listWhere = " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params);
		params.remove("order_by");
		String countWhere = " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params);
		
		params.put("where", countWhere + check_where);
		pageData.setTotalSize(getMapper().countCustom(params));
		//添加分页功能
		params.put("where", listWhere + check_where);
		params.put("pageData", pageData);
		return getMapper().listCustom(params);
	}

	@Override
	public List<T> listCustom(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
		//数据权限的条件约束
		String check_where = "";
		if(params.containsKey("check_where")){
			check_where = params.get("check_where").toString();
			params.remove("check_where");
		}
		//添加sql条件
		params.put("where", " where 1=1 " + HZ_SQL_ParamsUtil.tran2sql(params) + " " + check_where);
		//添加分页功能
		return getMapper().listCustom(params);
	}
	
}
