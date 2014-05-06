package com.hczd.download.customer.module;

import org.apache.ibatis.type.Alias;

import com.hczd.download.base.module.HZ_BaseRecordObject;


/**
 * 历史挂靠记录
 * @author linjian 
 * @version 2.0
 * @create_date 2013-7-9 下午2:17:34
 */
@Alias("HZ_Customer_Anchored_History")
public class HZ_Customer_Anchored_History extends HZ_BaseRecordObject implements Cloneable {
	
	/**
	 * 失效
	 */
	public static String STATUS_FAILD = "失效";
	
	/**
	 * 正常
	 */
	public static String STATUS_NORMAL = "正常";
	
	private Integer id;
	private Integer top_customer_id;//挂靠顶级客户编号
	private String top_customer_name;//挂靠顶级客户名称
	private Integer main_customer_id;//挂靠主体客户编号
	private String main_customer_name;//挂靠主体客户名称
	private Integer anchor_customer_id;//挂靠客户编号
	private String anchor_customer_name; //挂靠客户名称
	private String start_time;//开始时间
	private String end_time;//结束时间
	private String status;//挂靠状态
	private String remark;//备注
	
	/**实体完整性验证
	 * @author linjian 
	 * @create_date 2013-7-9 下午5:38:49
	 * @return 验证结果
	 */
	public String validate(){
		return "200";
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTop_customer_id() {
		return top_customer_id;
	}
	public void setTop_customer_id(Integer top_customer_id) {
		this.top_customer_id = top_customer_id;
	}
	public String getTop_customer_name() {
		return top_customer_name;
	}
	public void setTop_customer_name(String top_customer_name) {
		this.top_customer_name = top_customer_name;
	}
	public Integer getMain_customer_id() {
		return main_customer_id;
	}
	public void setMain_customer_id(Integer main_customer_id) {
		this.main_customer_id = main_customer_id;
	}
	public String getMain_customer_name() {
		return main_customer_name;
	}
	public void setMain_customer_name(String main_customer_name) {
		this.main_customer_name = main_customer_name;
	}
	public Integer getAnchor_customer_id() {
		return anchor_customer_id;
	}
	public void setAnchor_customer_id(Integer anchor_customer_id) {
		this.anchor_customer_id = anchor_customer_id;
	}
	public String getAnchor_customer_name() {
		return anchor_customer_name;
	}
	public void setAnchor_customer_name(String anchor_customer_name) {
		this.anchor_customer_name = anchor_customer_name;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public HZ_Customer_Anchored_History clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (HZ_Customer_Anchored_History)super.clone();
	}
	
}
