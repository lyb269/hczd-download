package com.hczd.download.access.module;

import org.apache.ibatis.type.Alias;

import com.hczd.download.base.module.HZ_BaseRecordObject;

/**
 * 类描述：通行卡消费记录
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-1-3 时间：上午10:04:24
 */
@Alias(value = "HZ_Access_Card_Consumption_Log")
public class HZ_Access_Card_Consumption_Log extends HZ_BaseRecordObject{
	private Integer id;
	private String  inlet_transit_time;		// 入口通行时间
	private String  outlet_transit_time;	// 出口通行时间
	private String  closing_date;			// 结算日期
	private String  transit_section;		// 通行区间
	private String  receivable_money;		// 应收金额
	private String  money_num;				// 收费金额
	private String  return_money_num;		// 补退金额
	private String  bill_no;				// 发票号
	private String  vehicle_passion;		// 车情
	private String  charge_type;			// 收费类型
	private String  charge_all_up;			// 收费总重(公斤)
	private String  charge_pattern;			// 收费轴型
	private String  settlement_rovinces;	// 结算省份
	private String  account_balance;		// 账户余额
	private String  import_time;			// 消费数据导入时间
	
	private Integer card_id;				// 通行卡id
	private String  card_no;				// 通行卡卡号
	
	private Integer main_id;				// 主账户id
	private String  main_name;				// 主账户名称
	
	private Integer anchor_customer_id;		// 挂靠客户编号
	private String  anchor_customer_name;	// 挂靠客户名称
	
	private Integer vehicle_id;				// 车辆id
	private String  vehicle_no;				// 车牌号
	
	private Integer customer_id;			// 客户id
	private String  customer_name;			// 客户名称
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInlet_transit_time() {
		return inlet_transit_time;
	}
	public void setInlet_transit_time(String inlet_transit_time) {
		this.inlet_transit_time = inlet_transit_time;
	}
	public String getOutlet_transit_time() {
		return outlet_transit_time;
	}
	public void setOutlet_transit_time(String outlet_transit_time) {
		this.outlet_transit_time = outlet_transit_time;
	}
	public String getClosing_date() {
		return closing_date;
	}
	public void setClosing_date(String closing_date) {
		this.closing_date = closing_date;
	}
	public String getTransit_section() {
		return transit_section;
	}
	public void setTransit_section(String transit_section) {
		this.transit_section = transit_section;
	}
	public String getReceivable_money() {
		return receivable_money;
	}
	public void setReceivable_money(String receivable_money) {
		this.receivable_money = receivable_money;
	}
	public String getMoney_num() {
		return money_num;
	}
	public void setMoney_num(String money_num) {
		this.money_num = money_num;
	}
	public String getReturn_money_num() {
		return return_money_num;
	}
	public void setReturn_money_num(String return_money_num) {
		this.return_money_num = return_money_num;
	}
	public String getBill_no() {
		return bill_no;
	}
	public void setBill_no(String bill_no) {
		this.bill_no = bill_no;
	}
	public String getVehicle_passion() {
		return vehicle_passion;
	}
	public void setVehicle_passion(String vehicle_passion) {
		this.vehicle_passion = vehicle_passion;
	}
	public String getCharge_type() {
		return charge_type;
	}
	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
	}
	public String getCharge_all_up() {
		return charge_all_up;
	}
	public void setCharge_all_up(String charge_all_up) {
		this.charge_all_up = charge_all_up;
	}
	public String getCharge_pattern() {
		return charge_pattern;
	}
	public void setCharge_pattern(String charge_pattern) {
		this.charge_pattern = charge_pattern;
	}
	public String getSettlement_rovinces() {
		return settlement_rovinces;
	}
	public void setSettlement_rovinces(String settlement_rovinces) {
		this.settlement_rovinces = settlement_rovinces;
	}
	public String getAccount_balance() {
		return account_balance;
	}
	public void setAccount_balance(String account_balance) {
		this.account_balance = account_balance;
	}
	public String getImport_time() {
		return import_time;
	}
	public void setImport_time(String import_time) {
		this.import_time = import_time;
	}
	public Integer getCard_id() {
		return card_id;
	}
	public void setCard_id(Integer card_id) {
		this.card_id = card_id;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
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
	public Integer getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(Integer vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	public String getVehicle_no() {
		return vehicle_no;
	}
	public void setVehicle_no(String vehicle_no) {
		this.vehicle_no = vehicle_no;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public Integer getMain_id() {
		return main_id;
	}
	public void setMain_id(Integer main_id) {
		this.main_id = main_id;
	}
	public String getMain_name() {
		return main_name;
	}
	public void setMain_name(String main_name) {
		this.main_name = main_name;
	}
	@Override
	public String toString() {
		return "HZ_Access_Card_Consumption_Log [id=" + id
				+ ", inlet_transit_time=" + inlet_transit_time
				+ ", outlet_transit_time=" + outlet_transit_time
				+ ", closing_date=" + closing_date + ", transit_section="
				+ transit_section + ", receivable_money=" + receivable_money
				+ ", money_num=" + money_num + ", return_money_num="
				+ return_money_num + ", bill_no=" + bill_no
				+ ", vehicle_passion=" + vehicle_passion + ", charge_type="
				+ charge_type + ", charge_all_up=" + charge_all_up
				+ ", charge_pattern=" + charge_pattern
				+ ", settlement_rovinces=" + settlement_rovinces
				+ ", account_balance=" + account_balance + ", import_time="
				+ import_time + ", card_id=" + card_id + ", card_no=" + card_no
				+ ", main_id=" + main_id + ", main_name=" + main_name
				+ ", anchor_customer_id=" + anchor_customer_id
				+ ", anchor_customer_name=" + anchor_customer_name
				+ ", vehicle_id=" + vehicle_id + ", vehicle_no=" + vehicle_no
				+ ", customer_id=" + customer_id + ", customer_name="
				+ customer_name + "]";
	}
}
