package com.hczd.download.card.module;

import org.apache.ibatis.type.Alias;

/**
 * 加油站实体类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-17 上午10:51:56
 */
@Alias("HZ_Gas_Station")
public class HZ_Gas_Station {
	/**未审核*/
	public static final Integer STATUS_DISABLE = 1;
	/**有效*/
	public static final Integer STATUS_EFFECTIVE = 0;
	
	private Integer id;
	private String name;//加油站名称
	private String code; // 加油站编码
	private String addr;//地址
	private String area;//所属区域
	private String gas_tel;//加油站电话
	private String oil ;//油品
	private Integer supplier_id;//供应商编号
	private String supplier_name;//供应商名称
	private Integer status = 1;//状态，默认为未审核
	private String fax;//传真
	private String gas_master;// 站长
	private String master_tel;// 站长电话
	private String district_manager_tel; // 片区经理电话
	private String map; // 地图
	private String remark;//备注
	
	/**实体完整性验证
	 * @author linjian 
	 * @create_date 2013-6-17 上午11:38:43
	 * @return 验证结果
	 */
	public String validate(){
		return "200";
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getGas_tel() {
		return gas_tel;
	}
	public void setGas_tel(String gas_tel) {
		this.gas_tel = gas_tel;
	}
	public String getOil() {
		return oil;
	}
	public void setOil(String oil) {
		this.oil = oil;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getGas_master() {
		return gas_master;
	}
	public void setGas_master(String gas_master) {
		this.gas_master = gas_master;
	}
	public String getMaster_tel() {
		return master_tel;
	}
	public void setMaster_tel(String master_tel) {
		this.master_tel = master_tel;
	}
	public String getDistrict_manager_tel() {
		return district_manager_tel;
	}
	public void setDistrict_manager_tel(String district_manager_tel) {
		this.district_manager_tel = district_manager_tel;
	}
	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
