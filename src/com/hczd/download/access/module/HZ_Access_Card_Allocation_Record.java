package com.hczd.download.access.module;

import org.apache.ibatis.type.Alias;

import com.hczd.download.base.module.HZ_BaseRecordObject;
import com.hczd.download.common.util.HZ_ValidateUtil;

/**
 * 类描述：分配记录实体
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-5-15 时间：下午4:29:02
 */
@Alias(value = "HZ_Access_Card_Allocation_Record")
public class HZ_Access_Card_Allocation_Record extends HZ_BaseRecordObject {
	
	/** 未支付状态 */
	public static String STATUS_NO_PAY = "未支付";
	/** 申请状态 */
	public static String STATUS_APP = "申请中";
	/**  分配成功 */
	public static String STATUS_SUCCESS = "完成";
	/** 作废 */
	public static String STATUS_INVALID = "作废";
	
	/** 正常 */
	public static String SUPP_PROPER = "正常";
	/** 补充 */
	public static String SUPP_EMBODY = "补充";
	/** 垫资 */
	public static String SUPP_MAT = "垫资";
	
	private Integer id;
	
	private String  app_num;				// 分配金额
	private String  mat_num = "0";			// 垫资金额
	private String  current_balance;		// 当前余额
	private String  source = "手工录入";		// 来源
	private String  status = "未支付";		// 状态
	private String  supplementary = "正常";	// 是否补充录入
	private String  supplementary_time;		// 补充分配时间
	private String  app_time;				// 申请时间
	private String  com_time;				// 结束时间
	private String  remark;					// 备注说明
	private String  export;					// 是否导出
	private String  export_time;			// 导出时间
	
	private String  vehicle_no;				// 车牌号
	private Integer vehicle_id;				// 车牌号id
	
	private Integer card_id;				// 通行卡id
	private String  card_no;				// 通行卡卡号
	
	private String  main_name; 				// 主账户名称
	private Integer main_card_id;			// 主账户编号
	
	private String  customer_name;			// 客户名称
	private Integer customer_id;			// 客户编号
	
	private Integer pay_id;					// 账户支付ID

	/**验证实体完整性
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-5-15 时间：下午5:52:41
	 * @return
	 */
	public String validate(){
		if(card_id == null){
			return "通行卡卡号不能为空";
		}
		if(!"补充".equals(supplementary)&&!HZ_ValidateUtil.isMoney(app_num)){
			return "金额格式有误，请重新输入";
		}
		return "200";
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public String getVehicle_no() {
		return vehicle_no;
	}
	public void setVehicle_no(String vehicle_no) {
		this.vehicle_no = vehicle_no;
	}
	public String getSupplementary() {
		return supplementary;
	}
	public void setSupplementary(String supplementary) {
		this.supplementary = supplementary;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getApp_time() {
		return app_time;
	}
	public void setApp_time(String app_time) {
		this.app_time = app_time;
	}
	public String getCom_time() {
		return com_time;
	}
	public void setCom_time(String com_time) {
		this.com_time = com_time;
	}
	public String getCurrent_balance() {
		return current_balance;
	}
	public void setCurrent_balance(String current_balance) {
		this.current_balance = current_balance;
	}
	public String getApp_num() {
		return app_num;
	}
	public void setApp_num(String app_num) {
		this.app_num = app_num;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSupplementary_time() {
		return supplementary_time;
	}
	public void setSupplementary_time(String supplementary_time) {
		this.supplementary_time = supplementary_time;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMat_num() {
		return mat_num;
	}

	public void setMat_num(String mat_num) {
		this.mat_num = mat_num;
	}

	public Integer getPay_id() {
		return pay_id;
	}

	public void setPay_id(Integer pay_id) {
		this.pay_id = pay_id;
	}

	public String getExport() {
		return export;
	}

	public void setExport(String export) {
		this.export = export;
	}

	public String getExport_time() {
		return export_time;
	}

	public void setExport_time(String export_time) {
		this.export_time = export_time;
	}

	public Integer getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(Integer vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	public Integer getCard_id() {
		return card_id;
	}

	public void setCard_id(Integer card_id) {
		this.card_id = card_id;
	}

	public String getMain_name() {
		return main_name;
	}

	public void setMain_name(String main_name) {
		this.main_name = main_name;
	}

	public Integer getMain_card_id() {
		return main_card_id;
	}

	public void setMain_card_id(Integer main_card_id) {
		this.main_card_id = main_card_id;
	}
	
}
