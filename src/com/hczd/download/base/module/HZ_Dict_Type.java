package com.hczd.download.base.module;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

/**
 * 数据字典类型实体类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-29 上午9:22:49
 */
@Alias("HZ_Dict_Type")
public class HZ_Dict_Type {
	/**
	 * 可用状态
	 */
	public static Integer STATUS_NORMAL = 2;
	
	/**
	 * 禁用状态
	 */
	public static Integer STATUS_UNUSE = -2;
	private Integer id;
	private String name;//名字
	private String extension;//后缀
	private String remarks;//备注
	private Integer status = 2;//状态
	
	/**
	 * 实体完整性验证
	 * @author linjian 
	 * @create_date 2013-5-29 下午12:01:53
	 * @return
	 */
	public String validate(){
		String error_msg = "";
		if(StringUtils.isBlank(name)){
			error_msg = "数据字典类型名字不能为空";
		}
		if(StringUtils.isBlank(extension)){
			error_msg = "数据字典类型标识不能为空";
		}
		return error_msg;
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
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
