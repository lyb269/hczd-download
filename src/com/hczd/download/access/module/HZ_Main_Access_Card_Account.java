package com.hczd.download.access.module;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import com.hczd.download.base.module.HZ_BaseRecordObject;

/**
 * 类描述：通行证主卡账户
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2013-12-31 时间：上午11:24:36
 */
@Alias(value = "HZ_Main_Access_Card_Account")
public class HZ_Main_Access_Card_Account extends HZ_BaseRecordObject {
	/** 正常 */
	public static String STATUS_NORMAL = "正常";
	/** 禁用 */
	public static String STATUS_DISABLE = "禁用";
	
	private Integer id;
	
	private String  name;				// 名称
	private String  area;				// 所属区域
	private String  balance = "0";		// 余额
	private String  deadline;			// 有效期
	private String  password;			// 密码
	private String  status = "正常";		// 状态
	private String  registerdate;		// 注册日期
	private String  create_time;		// 创建日期
	private String  remark;				// 备注
	
	private Integer supplier_id;		// 供应商编号
	private String  supplier_name;		// 供应商名字
	
	/**
	 * 验证实体完整性
	 * @author linjian 
	 * @create_date 2013-6-15 下午2:40:01
	 * @return 验证结果
	 */
	public String validate(){
		if(StringUtils.isBlank(name)){
			return "账户名称不能为空";
		}
		return "200";
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegisterdate() {
		return registerdate;
	}

	public void setRegisterdate(String registerdate) {
		this.registerdate = registerdate;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
