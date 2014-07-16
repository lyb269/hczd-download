package com.hczd.download.card.module;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import com.hczd.download.base.module.HZ_BaseRecordObject;

/**加油卡实体类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-28 上午11:05:27
 */
@Alias(value = "HZ_Gas_Card")
public class HZ_Gas_Card extends HZ_BaseRecordObject implements Cloneable {
	/** 挂失 */
	public static final Integer STATUS_LOSS = -2;
	/** 申请解挂失 */
	public static final Integer STATUS_UNLOSS = -1;
	/** 申请换卡 */
	public static final Integer STATUS_TRADE = -3;
	/** 申请退卡 */
	public static final Integer STATUS_BACK = -4;
	/** 正常 */
	public static final Integer STATUS_NORMAL = 2;
	/** 禁用 */
	public static final Integer STATUS_DISABLE = 0;
	/** 退卡 */
	public static final Integer STATUS_RETURN = -5;
	
	/** 发送短信 */
	public static final String NOTE_REMIND_YES = "是";
	/** 不发送短信 */
	public static final String NOTE_REMIND_NO = "否";
	
	
	private Integer id;
	private String  card_no;				// 加油卡卡号
	private String  balance = "0";			// 加油卡当前余额
	
	private String  area;					// 所属区域
	private String  perfer_area; 			// 加油卡的优惠区域
	private String  deadline;				// 有效截止日期
	private String  registerdate;			// 注册日期
	private Integer status = 2;				// 状态
	
	private String  haspwd = "是";			// 是否包含密码？
	private String  password; 				// 密码,加密
	private String  note_remind;			// 短信提醒
	private String  receive_tel;			// 接收电话
	private Integer oid;					// 限定油品
	private String  fuel_charge;			// 每次加油量
	private String  fuel_money;				// 每天加油金额
	private String  fuel_number;			// 每天加油次数
	private String  remark;					// 备注说明
	
	private Integer supplier_id; 			// 供应商id
	private Integer driver_id;				// 驾驶员
	
	private Integer vehicle_id;				// 绑定车辆编号
	private String  vehicle_no;				// 绑定车牌号
	
	private String  main_card_no; 			// 主卡id
	private Integer main_card_id;			// 主卡编号
	
	private Integer customer_id;			// 客户编号
	private String  customer_name;			// 客户名称
	private String  customer_balance;		// 客户余额
	
	/**实体完整性验证
	 * @author linjian 
	 * @create_date 2013-5-29 下午6:34:35
	 * @return 验证结果
	 */
	public String validate(){
		if(StringUtils.isBlank(card_no)){
			return "卡号不能为空";
		}else if(supplier_id == null){
			return "供应商不能为空";
		}else if(StringUtils.isBlank(area)){
			return "优惠区域不能为空";
		}
		if(StringUtils.isBlank(main_card_no) || main_card_no.equals("0")){
			return "主卡编号不允许为空";
		}
		return "200";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public HZ_Gas_Card clone() throws CloneNotSupportedException {
		return (HZ_Gas_Card)super.clone();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public Integer getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(Integer vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public Integer getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}
	public String getFuel_charge() {
		return fuel_charge;
	}
	public void setFuel_charge(String fuel_charge) {
		this.fuel_charge = fuel_charge;
	}
	public String getFuel_money() {
		return fuel_money;
	}
	public void setFuel_money(String fuel_money) {
		this.fuel_money = fuel_money;
	}
	public String getFuel_number() {
		return fuel_number;
	}
	public void setFuel_number(String fuel_number) {
		this.fuel_number = fuel_number;
	}
	public String getMain_card_no() {
		return main_card_no;
	}
	public void setMain_card_no(String main_card_no) {
		this.main_card_no = main_card_no;
	}
	public Integer getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getReceive_tel() {
		return receive_tel;
	}
	public void setReceive_tel(String receive_tel) {
		this.receive_tel = receive_tel;
	}
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public String getHaspwd() {
		return haspwd;
	}
	public void setHaspwd(String haspwd) {
		this.haspwd = haspwd;
	}

	public String getVehicle_no() {
		return vehicle_no;
	}

	public void setVehicle_no(String vehicle_no) {
		this.vehicle_no = vehicle_no;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getRegisterdate() {
		return registerdate;
	}

	public void setRegisterdate(String registerdate) {
		this.registerdate = registerdate;
	}

	public String getNote_remind() {
		return note_remind;
	}

	public void setNote_remind(String note_remind) {
		this.note_remind = note_remind;
	}

	public String getPerfer_area() {
		return perfer_area;
	}

	public void setPerfer_area(String perfer_area) {
		this.perfer_area = perfer_area;
	}
	public Integer getMain_card_id() {
		return main_card_id; 
	}

	public void setMain_card_id(Integer main_card_id) {
		this.main_card_id = main_card_id;
	}

	public String getCustomer_balance() {
		return customer_balance;
	}

	public void setCustomer_balance(String customer_balance) {
		this.customer_balance = customer_balance;
	}

	@Override
	public String toString() {
		return "HZ_Gas_Card [id=" + id + ", card_no=" + card_no
				+ ", main_card_no=" + main_card_no + ", main_card_id="
				+ main_card_id + ", customer_id=" + customer_id
				+ ", customer_name=" + customer_name + "]";
	}
	
}
