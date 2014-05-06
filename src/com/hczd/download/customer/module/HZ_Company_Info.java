package com.hczd.download.customer.module;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

/**
 * 企业信息实体类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-29 下午3:26:28
 */
@Alias("HZ_Company_Info")
public class HZ_Company_Info {
	private Integer id;
	private Integer customer_id;
	private String  openning_time;			//开户时间
	private String  openning_bank;			//开户银行
	private String  org_code;				//组织机构代码
	private String  tax_no;					//税务号
	private String  license_no;				// 营业执照号
	private String  manager;				// 总经理
	private String  industry;				// 所属行业(数据字典里读取行业)
	private String  bankroll;				// 注册资金
	private String  chieftain;				//法人
	private String  chieftain_identity_no;	//法人身份證
	private String  company_name;			//公司名称
	private String  website;				//相关网址
	private String  remarks;				//备注
	
	/**
	 * 实体完整性验证
	 * @author linjian 
	 * @create_date 2013-5-29 下午3:33:52
	 * @return 验证结果
	 */
	public String validate(){
		if(StringUtils.isBlank(company_name)){
			return "名称不能为空";
		}
		return "";
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
	public String getOpenning_time() {
		return openning_time;
	}
	public void setOpenning_time(String openning_time) {
		this.openning_time = openning_time;
	}
	public String getOpenning_bank() {
		return openning_bank;
	}
	public void setOpenning_bank(String openning_bank) {
		this.openning_bank = openning_bank;
	}
	public String getOrg_code() {
		return org_code;
	}
	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}
	public String getTax_no() {
		return tax_no;
	}
	public void setTax_no(String tax_no) {
		this.tax_no = tax_no;
	}
	public String getLicense_no() {
		return license_no;
	}
	public void setLicense_no(String license_no) {
		this.license_no = license_no;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getBankroll() {
		return bankroll;
	}
	public void setBankroll(String bankroll) {
		this.bankroll = bankroll;
	}
	public String getChieftain() {
		return chieftain;
	}
	public void setChieftain(String chieftain) {
		this.chieftain = chieftain;
	}
	public String getChieftain_identity_no() {
		return chieftain_identity_no;
	}
	public void setChieftain_identity_no(String chieftain_identity_no) {
		this.chieftain_identity_no = chieftain_identity_no;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
