package com.hczd.download.card.module;

import org.apache.ibatis.type.Alias;

import com.hczd.download.base.module.HZ_BaseRecordObject;

/**主卡实体类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-28 上午11:12:36
 */
@Alias(value = "HZ_Main_Gas_Card")
public class HZ_Main_Gas_Card extends HZ_BaseRecordObject {
	/** 正常 */
	public static final Integer STATUS_NORMAL = 2;
	/** 禁用 */
	public static final Integer STATUS_DISABLE = 0;
	
	private Integer id;
	private String  card_no;			// 主卡卡号
	private String  area;				// 所属区域
	private String  balance = "0";		// 主卡余额
	private String  deadline;			// 有效期
	private String  login_name;			// 登录名称
	private String  password;			// 密码
	private Integer supplier_id;		// 供应商
	private String  supplier_name;		// 供应商名字
	private Integer status = 2;			// 卡状态
	private String  registerdate;		// 注册日期
	private String  create_time;		// 添加日期
	private String  remark;				// 备注
	private Integer card_num; 			// 副卡数
	private String  cardholder;			// 持卡人
	
	private Integer state = 0 ; //主卡消费数据下载状态(不保存数据库)
	
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getCard_num() {
		return card_num;
	}
	public void setCard_num(Integer card_num) {
		this.card_num = card_num;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getCardholder() {
		return cardholder;
	}
	public void setCardholder(String cardholder) {
		this.cardholder = cardholder;
	}
}
