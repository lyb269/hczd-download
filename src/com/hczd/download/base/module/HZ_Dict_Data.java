package com.hczd.download.base.module;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

/**
 * 数据字典数据类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-29 上午9:26:34
 */
@Alias("HZ_Dict_Data")
public class HZ_Dict_Data {
	public static final String DICT_CHANGE_TYPE = "dict_change_type";
	/** 油品类型 */
	public static final String DICT_OIL_TYPE = "dict_oil_type";
	public static final String DICT_CAR_TYPE = "dict_car_type";
	public static final String DICT_CAR_NATURE = "dict_car_nature";
	public static final String DICT_CONTENT_TYPE = "dict_content_type";
	public static final String DICT_SEX = "dict_sex";
	public static final String DICT_DRIVER_TYPE = "dict_driver_type";
	public static final String DICT_EXPAND= "dict_expand";
	public static final String DICT_VISIABLE= "dict_visiable";
	public static final String DICT_ICON= "dict_icon";
	public static final String DICT_MODULE= "dict_module";
	/** 客户来源 */
	public static final String DICT_CUSTOMER_SOURCE= "dict_customer_source";
	/** 开票方式 */
	public static final String DICT_OPEN_BILL_TYPE= "dict_open_bill_type";
	/** 开票品名 */
	public static final String DICT_BILL_PRODUCT_NAME= "dict_bill_product_name";
	/** 开票规格型号 */
	public static final String DICT_OPEN_BILL_MODE= "dict_open_bill_mode";
	public static final String DICT_MAIN_RISKS= "dict_main_risks";
	public static final String DICT_ADDITIONAL_RISK= "dict_additional_risk";
	public static final String DICT_SUPERCDW= "dict_SuperCDW";
	/** 上传文件名称 */
	public static final String PAPERS_NAME = "papers_name";
	/** 通行卡供应商名称 */
	public static final String ACCESS_SUPPLIER = "ACCESS_SUPPLIER";
	
	/**
	 * 支付方式
	 */
	public static final String DICT_PAY_TYPE = "PAY_TYPE";

	private Integer id ;
	private Integer dict_type_id;	// 对应类型
	private Integer data_id;		// 数据编号
	private String  name;			// 名称
	private String  checked;		// 是否为默认
	private String  remarks;		// 备注
	private Integer create_user_id;	// 创建用户
	private String  create_time;	// 创建时间
	private Integer update_user_id;	// 修改用户
	private String  update_time;	// 修改时间
	
	/**验证实体完整性
	 * @author linjian 
	 * @create_date 2013-5-29 下午1:44:26
	 * @return 验证结果
	 */
	public String validate(){
		String error_msg = "";
		if(StringUtils.isBlank(name)){
			error_msg = "名称不能为空！";
		}
		return error_msg;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDict_type_id() {
		return dict_type_id;
	}
	public void setDict_type_id(Integer dict_type_id) {
		this.dict_type_id = dict_type_id;
	}
	public Integer getData_id() {
		return data_id;
	}
	public void setData_id(Integer data_id) {
		this.data_id = data_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(Integer create_user_id) {
		this.create_user_id = create_user_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Integer getUpdate_user_id() {
		return update_user_id;
	}
	public void setUpdate_user_id(Integer update_user_id) {
		this.update_user_id = update_user_id;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
}
