package com.hczd.download.access.module;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import com.hczd.download.base.module.HZ_BaseRecordObject;


/**
 * 类描述：通行卡实体类
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-1-3 时间：上午9:47:17
 */
@Alias(value = "HZ_Access_Card")
public class HZ_Access_Card extends HZ_BaseRecordObject implements Cloneable {
	/** 换卡 */
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
	/** 欠费 */
	public static Integer STATUS_ARREARS = -6;
	
	/** 发送短信 */
	public static final String NOTE_REMIND_YES = "是";
	/** 不发送短信 */
	public static final String NOTE_REMIND_NO = "否";
	
	
	private Integer id;
	private String  card_no;				// 通行卡卡号
	private String  deadline;				// 有效截止日期
	private String  registerdate;			// 注册日期
	private Integer status = 2;				// 状态
	private String  area; 					// 所属区域
	private String  haspwd = "是";			// 是否包含密码？
	private String  password; 				// 密码,加密
	private String  balance="0";			// 通行卡余额
	private String  remark;					// 备注说明
	
	private Integer driver_id;				// 持卡人
	private Integer supplier_id;			// 供应商id
	
	private Integer vehicle_id;				// 绑定车辆编号
	private String  vehicle_no;				// 绑定车牌号
	
	private String  main_name; 				// 主账户名称
	private Integer main_card_id;			// 主账户编号
	
	private Integer customer_id;			// 客户编号
	private String  customer_name;			// 客户名称
	
	/**实体完整性验证
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-1-3 时间：上午9:47:36
	 * @return
	 */
	public String validate(){
		if(id == null && (main_card_id == null || main_card_id == 0)){
			return "请选择通行卡主账户";
		}else if(id == null && StringUtils.isBlank(card_no)){
			return "请输入通行卡号";
		}else if(id == null && card_no.length()<15){
			return "通行卡卡号不能少于15位";
		}else if(supplier_id == null){
			return "请选择供应商";
		}else if(StringUtils.isBlank(area)){
			return "请选择所属区域";
		}else if(StringUtils.isNotBlank(haspwd) && haspwd.equals("是")){
			if(StringUtils.isBlank(password)){
				return "密码不能为空";
			}
		}
		return "200";
	}
	
	@Override
	public HZ_Access_Card clone() throws CloneNotSupportedException {
		return (HZ_Access_Card)super.clone();
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

	public Integer getMain_card_id() {
		return main_card_id; 
	}

	public void setMain_card_id(Integer main_card_id) {
		this.main_card_id = main_card_id;
	}

	public String getMain_name() {
		return main_name;
	}

	public void setMain_name(String main_name) {
		this.main_name = main_name;
	}

	public Integer getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
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
}
