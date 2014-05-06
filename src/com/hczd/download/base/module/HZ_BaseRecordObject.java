package com.hczd.download.base.module;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

/**
 * 记录用户操作信息父类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-7 上午10:19:28
 */
@Alias("HZ_BaseRecordObject")
public abstract class HZ_BaseRecordObject {
	//系统操作员id
	private String user_id;
	//操作员名字
	private String user_name;
	//操作ip
	private String oper_ip;
	//操作时间
	private String oper_time;
	//参数
	private String params;
	
	/**追加操作记录
	 * @author linjian 
	 * @create_date 2013-6-7 上午11:22:01
	 * @param params 记录参数
	 */
	public void append(HZ_BaseRecordParams params){
		if(params == null)return;
		if(StringUtils.isBlank(this.oper_ip)){
			this.oper_ip = params.getOper_ip();
		}else{
			this.oper_ip = this.oper_ip + "," + params.getOper_ip();
		}
		if(StringUtils.isBlank(this.oper_time)){
			this.oper_time = params.getOper_time();
		}else{
			this.oper_time = this.oper_time + "," + params.getOper_time();
		}
		if(StringUtils.isBlank(this.user_id)){
			this.user_id = params.getUser_id();
		}else{
			this.user_id = this.user_id + "," + params.getUser_id();
		}
		if(StringUtils.isBlank(this.params)){
			this.params = params.getParams();
		}else{
			this.params = this.params + "," + params.getParams();
		}
		if(StringUtils.isBlank(this.user_name)){
			this.user_name = params.getUser_name();
		}else{
			this.user_name = this.user_name + "," + params.getUser_name();
		}
		
	}
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getOper_ip() {
		return oper_ip;
	}
	public void setOper_ip(String oper_ip) {
		this.oper_ip = oper_ip;
	}
	public String getOper_time() {
		return oper_time;
	}
	public void setOper_time(String oper_time) {
		this.oper_time = oper_time;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
