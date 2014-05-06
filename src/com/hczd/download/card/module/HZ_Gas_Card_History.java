package com.hczd.download.card.module;

import org.apache.ibatis.type.Alias;

import com.hczd.download.base.module.HZ_BaseRecordObject;



/**
 * 加油卡历史记录实体
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-6 下午6:10:38
 */
@Alias("HZ_Gas_Card_History")
public class HZ_Gas_Card_History extends HZ_BaseRecordObject implements Cloneable {
	/**
	 * 发卡
	 */
	public static Integer TYPE_ADD = 1;
	/**
	 * 退卡
	 */
	public static Integer TYPE_EXIT = -1;
	private Integer id;
	//所属客户id
	private Integer customer_id;
	//客户名称
	private String customer_name;
	//开始时间
	private String start_time;
	//结束时间
	private String end_time;
	//加油卡卡号
	private String card_no;
	//加油卡的优惠区域
	private String perfer_area;
	/**
	 * 车牌号
	 */
	private String vehicle_no;
	/**
	 * 车辆id
	 */
	private Integer vehicle_id;
	
	
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
	public String getPerfer_area() {
		return perfer_area;
	}
	public void setPerfer_area(String perfer_area) {
		this.perfer_area = perfer_area;
	}
	
	public HZ_Gas_Card_History clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (HZ_Gas_Card_History)super.clone();
	}

}
