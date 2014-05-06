package com.hczd.download.card.module;

import org.apache.ibatis.type.Alias;

/**加油卡消费记录
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-15 下午6:22:25
 */
@Alias(value = "HZ_Gas_Card_Consumption_Log")
public class HZ_Gas_Card_Consumption_Log {
	private Integer id;
	private Integer customer_id;			// 对应客户
	private String  customer_name;			// 客户名称
	private Integer vehicle_id;				// 对应车辆
	private String  vehicle_no;				// 车牌号
	private String  card_no;				// 卡号
	private String  charge_time;			// 时间
	private Integer station_id;				// 加油站
	private String  addr;					// 加油地点
	private String  amount;					// 数量
	private Integer oil; 					// 油型号
	private String  oil_name;				// 油品名称
	private String  money_num;				// 金额
	private String  current_balance;		// 卡余金额
	private Integer total;					// 积分
	private Integer anchor_customer_id;		// 挂靠客户编号
	private String  anchor_customer_name;	// 挂靠客户名称
	//用于加油卡明细，类型：充值、分配
	private String type;
	//消费数据导入时间
	private String import_time;
	
	
	public String getImport_time() {
		return import_time;
	}

	public void setImport_time(String import_time) {
		this.import_time = import_time;
	}

	/**实体完整性验证
	 * @author linjian 
	 * @create_date 2013-6-16 上午9:13:51
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
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public Integer getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(Integer vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getCharge_time() {
		return charge_time;
	}
	public void setCharge_time(String charge_time) {
		this.charge_time = charge_time;
	}
	public Integer getStation_id() {
		return station_id;
	}
	public void setStation_id(Integer station_id) {
		this.station_id = station_id;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Integer getOil() {
		return oil;
	}
	public void setOil(Integer oil) {
		this.oil = oil;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
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

	public String getOil_name() {
		return oil_name;
	}

	public void setOil_name(String oil_name) {
		this.oil_name = oil_name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMoney_num() {
		return money_num;
	}

	public void setMoney_num(String money_num) {
		this.money_num = money_num;
	}

	public String getCurrent_balance() {
		return current_balance;
	}

	public void setCurrent_balance(String current_balance) {
		this.current_balance = current_balance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
