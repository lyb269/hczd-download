package com.hczd.download.customer.module;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import com.hczd.download.base.module.HZ_BaseRecordObject;
import com.hczd.download.common.util.HZ_ValidateUtil;

/**
 * 客户实体类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-29 下午3:19:02
 */
@Alias("HZ_Customer")
public class HZ_Customer extends HZ_BaseRecordObject {
	/** 企业客户 */
	public static final int CUSTOMER_TYPE_BUS = 1;
	/** 运营车队 */
	public static final int CUSTOMER_TYPE_VEHICLE = 2;
	/** 个人客户 */
	public static final int CUSTOMER_TYPE_PERSONAL = 0;
	
	/** 特惠积分 */
	public static final int INTEGRAL_RECHARGE = 1;
	/** 月结积分 */
	public static final int INTEGRAL_MONTH = 0;
	
	/** 发送短信 */
	public static final int SEND_SMS_TRUE = 0 ;
	/** 不发送短信 */
	public static final int SEND_SMS_FALSE = 1 ;
	
	/** 有效         */
	public static final int STATUS_EFFECTIVE = 2;
	/** 申请禁用 */
	public static final int STATUS_APPLY_DISABLE = 1;
	/** 禁用        */
	public static final int STATUS_DISABLE = 0;
	/** 申请解禁 */
	public static final int STATUS_APPLY_EFFECTIVE = 3;
	/** 待激活    */
	public static final int STATUS_ACTIVATE = 4;
	
	/** 自动还款：是 */
	public static final String AUTO_REPAYMENT_TRUE = "是";
	/** 自动还款：否 */
	public static final String AUTO_REPAYMENT_FALSE = "否";
	
	/** 申请发票：是 */
	public static final String AUTO_BILL_TRUE = "是";
	/** 申请发票：否 */
	public static final String AUTO_BILL_FALSE = "否";
	
	private Integer id;
	private String  name; 				// 客户名称
	private String  customer_code; 		// 客户编码
	private Integer type; 				// 客户类型
	private Integer discount_type;		// 优惠方式
	private Integer grade;				// 客户级别
	private Integer admin; 				// 管理员
	private String  identity_no; 		// 身份证号
	private String  opening_time; 		// 开户时间
	private String  password; 			// 账户密码
	private String  simple_name;		// 简称
	private String  extension;			// 企业扩展如：@ycar365.com
	
	private String  tel; 				// 电话
	private String  fax; 				// 传真
	private Integer issend_sms ; 		// 是否发送短信
	private String  mobile; 			// 手机(短信收发号码)
	private String  email; 				// 电子邮箱
	private String  zip; 				// 邮编
	private String  addr; 				// 详细地址
	private String  area; 				// 区域
	private String  area_code; 			// 区域编号
	
	private Integer status; 			// 客户状态
	private Integer source;	 			// 客户来源（数据字典里读取来源）
	private Integer sales_man; 			// 业务代表
	private String  sales_man_name; 	// 业务代表名称
	
	private String  pinyin;				// 名字拼音
	private String  pinyin_abr;			// 名字缩写
	private String  remarks; 			// 描述
	
	private HZ_Company_Info hz_company_info; // 企业信息
	
	private HZ_Client_Login_Info login_info; // 登录信息
	
	private List<HZ_Linkman> listLinkman;	 // 联系人
	
	private String  insurance;				 // 判断是否为保险添加客户
	
	//2014-01-13 linhui
	private String auto_repayment;           // 自动还款
	//2014-01-22 chenfm
	private String auto_bill;				 // 申请开票
	
	/**实体完整性验证
	 * @author linjian 
	 * @create_date 2013-5-29 下午3:23:49
	 * @return 验证结果
	 */
	public String validate(){
		if(StringUtils.isBlank(name)){
			return "客户名称不能为空";
		}else if(HZ_ValidateUtil.ChineseSign(name)){
			return "客户名称不能包含中文标点";
		}
		//身份证验证
		if(type == 0){
			if(!StringUtils.isBlank(identity_no)){
				if(!HZ_ValidateUtil.checkNID(identity_no)){
					return "请输入15位或者18位正确的身份证号码";
				}
			}else{
				return "身份证号码不能为空";
			}
		}

		//验证邮箱
		if(!StringUtils.isBlank(email)){
			if(!HZ_ValidateUtil.checkEmail(email)){
				return "请输入合法的邮箱格式";
			}
		}
		if(!"true".equals(insurance) && id == null && type != 0 && StringUtils.isBlank(extension)){
			return "请输入登录域名";
		}
//		else{
//			return "电子邮箱不能为空";
//		}
		//业务代表
		if(sales_man == null || sales_man == 0){
			return "请选择业务代表";
		}
		//所属区域
		if(StringUtils.isBlank(area)){
			return "所属区域不能为空";
		}
		//联系人
		if(listLinkman != null && listLinkman.size()!=0){
			if(StringUtils.isBlank(listLinkman.get(0).getName())){
				return "联系人不能为空";
			}
		}else{
			return "联系人不能为空";
		}
		//联系电话
		if(listLinkman != null && listLinkman.size()!=0){
			if(StringUtils.isBlank(listLinkman.get(0).getTel())){
				return "联系电话不能为空";
			}else{
				if(!HZ_ValidateUtil.isTelephone(listLinkman.get(0).getTel()) && !HZ_ValidateUtil.checkMoblie(listLinkman.get(0).getTel())){
					return "联系电话格式错误";
				}
			}
		}else{
			return "联系电话不能为空";
		}
		
		//联系地址
		if(listLinkman != null && listLinkman.size()!=0){
			if(StringUtils.isBlank(listLinkman.get(0).getAddr())){
				return "联系地址不能为空";
			}else if(listLinkman.get(0).getAddr().split(" ").length<2){
				return "联系地址市或区不能为空！";
			}
		}else{
			return "联系地址不能为空";
		}
		
		//短信接收号码
		if(issend_sms != null && issend_sms == 0){
			if(StringUtils.isBlank(mobile)){
				return "短信接收号码不能为空";
			}
			if(!HZ_ValidateUtil.checkMoblie(mobile)){
				return "短信接收号码格式错误";
			}
		}
		
		//组织机构代码
		if(type != 0 && StringUtils.isBlank(hz_company_info.getOrg_code())){
			return "组织机构代码不能为空";
		}
		
		//营业执照
		if(!"true".equals(insurance)&&type != 0 && StringUtils.isBlank(hz_company_info.getLicense_no())){
			return "营业执照号不能为空";
		}
		
		//税务登记号
		if(!"true".equals(insurance)&&type != 0 && StringUtils.isBlank(hz_company_info.getTax_no())){
			return "税务登记号不能为空";
		}
		
		//企业法人
		if(type != 0 && StringUtils.isBlank(hz_company_info.getChieftain())){
			return "企业法人不能为空";
		}
		//简称
		if(!"true".equals(insurance)&&id == null && type != 0 && StringUtils.isBlank(simple_name)){
			return "请输入简称";
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
	public String getCustomer_code() {
		return customer_code;
	}
	public void setCustomer_code(String customer_code) {
		this.customer_code = customer_code;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
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
	public String getIdentity_no() {
		return identity_no;
	}
	public void setIdentity_no(String identity_no) {
		this.identity_no = identity_no;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getSales_man() {
		return sales_man;
	}
	public void setSales_man(Integer sales_man) {
		this.sales_man = sales_man;
	}
	public Integer getAdmin() {
		return admin;
	}
	public void setAdmin(Integer admin) {
		this.admin = admin;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Integer getDiscount_type() {
		return discount_type;
	}
	public void setDiscount_type(Integer discount_type) {
		this.discount_type = discount_type;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getIssend_sms() {
		return issend_sms;
	}
	public void setIssend_sms(Integer issend_sms) {
		this.issend_sms = issend_sms;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getPinyin_abr() {
		return pinyin_abr;
	}

	public void setPinyin_abr(String pinyin_abr) {
		this.pinyin_abr = pinyin_abr;
	}

	public String getOpening_time() {
		return opening_time;
	}

	public void setOpening_time(String opening_time) {
		this.opening_time = opening_time;
	}

	public List<HZ_Linkman> getListLinkman() {
		return listLinkman;
	}

	public void setListLinkman(List<HZ_Linkman> listLinkman) {
		this.listLinkman = listLinkman;
	}

	public HZ_Company_Info getHz_company_info() {
		return hz_company_info;
	}

	public void setHz_company_info(HZ_Company_Info hz_company_info) {
		this.hz_company_info = hz_company_info;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getSales_man_name() {
		return sales_man_name;
	}

	public void setSales_man_name(String sales_man_name) {
		this.sales_man_name = sales_man_name;
	}

	public String getSimple_name() {
		return simple_name;
	}

	public void setSimple_name(String simple_name) {
		this.simple_name = simple_name;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public HZ_Client_Login_Info getLogin_info() {
		return login_info;
	}

	public void setLogin_info(HZ_Client_Login_Info login_info) {
		this.login_info = login_info;
	}

	public String getAuto_repayment() {
		return auto_repayment;
	}

	public void setAuto_repayment(String auto_repayment) {
		this.auto_repayment = auto_repayment;
	}

	public String getAuto_bill() {
		return auto_bill;
	}

	public void setAuto_bill(String auto_bill) {
		this.auto_bill = auto_bill;
	}
	
}
