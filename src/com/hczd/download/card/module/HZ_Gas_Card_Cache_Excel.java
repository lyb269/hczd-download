package com.hczd.download.card.module;

import java.io.File;

/**
 * 缓存数据实体类
 * @author linyb
 * @version 1.0 
 * @create_date 2014-5-8下午2:16:41
 */
public class HZ_Gas_Card_Cache_Excel {
	private File f;   //缓存的file文件
	private String date_msg; //下载日期信息
	public File getF() {
		return f;
	}
	public void setF(File f) {
		this.f = f;
	}
	public String getDate_msg() {
		return date_msg;
	}
	public void setDate_msg(String date_msg) {
		this.date_msg = date_msg;
	}
	@Override
	public String toString() {
		return "HZ_Gas_Card_Cache_Excel [f=" + f.getName() + ", date_msg=" + date_msg
				+ "]";
	}
}
