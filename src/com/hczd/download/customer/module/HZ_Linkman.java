package com.hczd.download.customer.module;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

/**联系人实体类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-3 上午9:27:17
 */
@Alias("HZ_Linkman")
public class HZ_Linkman {
	
	private Integer id;//编号
	private String name; // 联系人名称
	private Integer customer_id; // 客户
	private Integer sex; // 性别
	private String birthdate; // 生日
	private String postion;//类型
	private String tel;
	private String mobile;
	private String email;
	private String qq;
	private String website;
	private String addr;//地址
	private String remarks;//备注
	private String identity_no;//身份证号
	
	/**
	 * 实体完整性验证
	 * @author linjian 
	 * @create_date 2013-6-3 上午10:08:44
	 * @return 验证结果
	 */
	public String validate(){
		if(StringUtils.isBlank(name)){
			return "名字不能为空";
		}
		return "";
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getPostion() {
		return postion;
	}
	public void setPostion(String postion) {
		this.postion = postion;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getIdentity_no() {
		return identity_no;
	}
	public void setIdentity_no(String identity_no) {
		this.identity_no = identity_no;
	}
	
	
}
