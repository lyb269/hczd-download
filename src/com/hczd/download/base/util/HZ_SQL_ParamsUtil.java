package com.hczd.download.base.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * sql数据库参数加工类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-23 下午2:47:35
 */
public class HZ_SQL_ParamsUtil {
	/**把Map形式的参数加工成sql条件参数
	 * @author linjian 
	 * @create_date 2013-5-23 下午2:48:42
	 * @param params
	 * @return
	 */
	public static String tran2sql(Map<String, Object> params){
		//数据权限的条件约束
		String check_where = "";
		if(params.containsKey("check_where")){
			check_where = params.get("check_where").toString();
			params.remove("check_where");
		}
		//拼接sql
		String sql = "";
		if(params != null){
			for (String key: params.keySet()) {
				if(params.get(key) != null && StringUtils.isNotBlank(params.get(key).toString()) && !key.equals("order_by") && !key.equals("limit") && !key.equals("column")){
					if(key.indexOf("or_") == 0){
						if(StringUtils.isBlank(sql)){
							sql = sql + " and ";
						}else{
							sql = sql + " or ";
						}
					}//以and_开头的
					else{
						sql = sql + " and ";
					}//end if
					//条件方式
					if(key.indexOf("_like") != -1 && key.indexOf("_like") == (key.length() - 5)){
						sql = sql + key.replace("and_", "").replace("or_", "").replace("_like", "") + " like '%" + params.get(key).toString().trim() + "%'";
					}else if(key.indexOf("_not_in") != -1 && key.indexOf("_not_in") == (key.length() - 7)){
						sql = sql + key.replace("and_", "").replace("or_", "").replace("_not_in", "") + " not in (" + params.get(key).toString().trim() + ")";
					}else if(key.indexOf("_in") != -1 && key.indexOf("_in") == (key.length() - 3)){
						sql = sql + key.replace("and_", "").replace("or_", "").replace("_in", "") + " in (" + params.get(key).toString().trim() + ")";
					}else if(key.indexOf("_sql") != -1){
						sql = sql + params.get(key).toString().trim();
					}else if(key.contains("<>")){
						sql = sql + key.replace("and_", "").replace("or_", "").replace("_<>", "") + " <> '" + params.get(key).toString().trim() + "'";
					}else if(key.contains("<=")){
						if(key.contains("$time")){
							sql = sql +  "to_timestamp(" + key.replace("and_", "").replace("or_", "").replace("_<=", "").replace("$time", "") + ",'YYYY-MM-DD HH24:MI:SS') <= to_timestamp('" + params.get(key).toString().trim() + "','YYYY-MM-DD HH24:MI:SS')";
						}else
							sql = sql + key.replace("and_", "").replace("or_", "").replace("_<=", "") + " <= '" + params.get(key).toString().trim() + "'";
					}else if(key.contains(">=")){
						if(key.contains("$time")){
							sql = sql +  "to_timestamp(" + key.replace("and_", "").replace("or_", "").replace("_>=", "").replace("$time", "") + ",'YYYY-MM-DD HH24:MI:SS') >= to_timestamp('" + params.get(key).toString().trim() + "','YYYY-MM-DD HH24:MI:SS')";
						}else
							sql = sql + key.replace("and_", "").replace("or_", "").replace("_>=", "") + " >= '" + params.get(key).toString().trim() + "'";
					}else if(key.contains("<")){
						if(key.contains("$time")){
							sql = sql +  "to_timestamp(" + key.replace("and_", "").replace("or_", "").replace("_<", "").replace("$time", "") + ",'YYYY-MM-DD HH24:MI:SS') < to_timestamp('" + params.get(key).toString().trim() + "','YYYY-MM-DD HH24:MI:SS')";
						}else
							sql = sql + key.replace("and_", "").replace("or_", "").replace("_<", "") + " < '" + params.get(key).toString().trim() + "'";
					}else if(key.contains(">")){
						if(key.contains("$time")){
							sql = sql +  "to_timestamp(" + key.replace("and_", "").replace("or_", "").replace("_>", "").replace("$time", "") + ",'YYYY-MM-DD HH24:MI:SS') > to_timestamp('" + params.get(key).toString().trim() + "','YYYY-MM-DD HH24:MI:SS')";
						}else
							sql = sql + key.replace("and_", "").replace("or_", "").replace("_>", "") + " > '" + params.get(key).toString().trim() + "'";
					}else if(params.get(key).toString().contains("is_null")){
						sql = sql + key.replace("and_", "").replace("or_", "") + " is null ";
					}else if(params.get(key).toString().contains("is_not_null")){
						sql = sql + key.replace("and_", "").replace("or_", "") + " is not null ";
					}else{
						sql = sql + key.replace("and_", "").replace("or_", "") + " = '" + params.get(key).toString().trim() + "'";
					}
				}//end if
			}//end for
			//数据权限条件
			sql = sql + " " + check_where;
			//排序条件
			if(params.containsKey("order_by")){
				sql = sql + " order by " + params.get("order_by").toString().replace("_desc", " desc").replace("_asc", " asc");
			}
			//分页条件
			if(params.containsKey("limit")){
				sql = sql + " limit " + params.get("limit");
			}
		}
		return sql;
	}
	
	/**
	 * 转换sql更新语句
	 * @author linjian 
	 * @create_date 2013-7-4 下午1:44:22
	 * @param params
	 * @return
	 */
	public static String tran2updateSQL(Map<String, Object> params){
		String sql = "set ";
		Map<String, Object> whereparams = new HashMap<String, Object>();
		for (String key : params.keySet()) {
			if(StringUtils.isNotBlank(key) && key.contains("update_")){
				sql = sql + " " + key.replace("update_", "") + " = '" + params.get(key) + "',";
			}else if(StringUtils.isNotBlank(key) && key.contains("where_")){
				whereparams.put(key.replace("where_", ""), params.get(key));
			}
		}
		if(!sql.equals("set ")){
			//不允许没有条件完全更新
			if(whereparams != null && whereparams.keySet().size() > 0){
				//去掉最后一个逗号
				sql = sql.substring(0, sql.length() - 1) + " where 1=1 " + tran2sql(whereparams);
			}
		}else{
			return "";
		}
		return sql;
	}
	
	
	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("or_mobile", "18620306152");
		params.put("or_name", "zhangjf");
		System.out.println(tran2sql(params));
	}
	
	
	/**将字符串转为in可查询的字符串
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2013-6-28 时间：下午3:13:38
	 * @param val
	 * @return
	 */
	public static String sqlInState(String val){
		StringBuffer hqlparams = new StringBuffer();
		String[] vals = val.replaceAll(" ", "").split(",");
		for (int i = 0; i < vals.length; i++) {
			if(StringUtils.isNotBlank(vals[i])){
				hqlparams.append("'").append(vals[i]).append("'");
				if(i<vals.length-1){
					hqlparams.append(",");
				}
			}else {
				continue;
			}
		}
		return hqlparams.toString();
	}
}
