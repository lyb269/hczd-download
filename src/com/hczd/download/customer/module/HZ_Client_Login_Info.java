package com.hczd.download.customer.module;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import com.hczd.download.base.module.HZ_BaseRecordObject;

/**前台客户端登录信息
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-29 下午3:37:17
 */
@Alias("HZ_Client_Login_Info")
public class HZ_Client_Login_Info extends HZ_BaseRecordObject {
	public static final Integer STATUS_SUCCESS = 2;
	public static final Integer STATUS_FAIL = -2;

	private Integer id;
	private String mobile;// 手机登入
	private String email; // 邮箱登入
	private String username; // 其它方式登入
	private String password = "365365"; // 登入密码
	private Integer customer_id; // 客户id
	private String oid; // 其它对象的id;由type决定是加油卡客户还是其他的
	private Integer status; //状态
	private String type; // 登入类型; 客户;加油卡,车辆,驾驶员等!
	private String customer_name; //用户名
	
	public String validate(){
		if(StringUtils.isBlank(password)){
			return "密码不能为空";
		}else if(password.trim().length() < 6){
			return "密码不能小于6位数";
		}
		return "";
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	
}
