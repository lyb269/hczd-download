package com.hczd.download.access.module;

import org.apache.ibatis.type.Alias;

import com.hczd.download.base.module.HZ_BaseRecordObject;

/**
 * 类描述：通行卡历史记录实体类
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-1-3 时间：上午9:47:59
 */
@Alias(value = "HZ_Access_Card_History")
public class HZ_Access_Card_History extends HZ_BaseRecordObject implements Cloneable {

	private Integer id;
	private String  start_time;		// 开始时间
	private String  end_time;		// 结束时间
	private String  remark;			// 备注
	
	private String  card_no;		// 通行卡号
	private Integer card_id;		// 通行id
	
	private Integer main_id;		// 主账户id
	
	private Integer customer_id;	// 所属客户id
	private String  customer_name;	// 客户名称
	
	private String vehicle_no;		// 车牌号
	private Integer vehicle_id;		// 车辆id
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
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
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getVehicle_no() {
		return vehicle_no;
	}
	public void setVehicle_no(String vehicle_no) {
		this.vehicle_no = vehicle_no;
	}
	public Integer getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(Integer vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	
	public HZ_Access_Card_History clone() throws CloneNotSupportedException {
		return (HZ_Access_Card_History)super.clone();
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getCard_id() {
		return card_id;
	}
	public void setCard_id(Integer card_id) {
		this.card_id = card_id;
	}
	public Integer getMain_id() {
		return main_id;
	}
	public void setMain_id(Integer main_id) {
		this.main_id = main_id;
	}

}
