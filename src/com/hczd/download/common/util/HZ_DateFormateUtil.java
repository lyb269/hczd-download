package com.hczd.download.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式常量
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-24 上午10:49:16
 */
public class HZ_DateFormateUtil {
	/**年月时分秒**/
	public static String FORMATE_TIME = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 年月日
	 */
	public static String FORMATE_DATE = "yyyy-MM-dd";
	
	/**
	 * 年月
	 */
	public static String FORMATE_MONTH = "yyyy-MM"; 
	
	/**
	 * 日
	 */
	public static String AREA_DAY = "dd";
	/**
	 * 年
	 */
	public static String AREA_YEAR = "yyyy";
	/**
	 * 月
	 */
	public static String AREA_MONTH = "MM";
	
	/**
	 * 小时
	 */
	public static String AREA_HOUR = "HH";
	
	/**
	 * 分钟
	 */
	public static String AREA_MINUTE = "mm";
	/**
	 * 秒
	 */
	public static String AREA_SECOND = "ss";
	
	/**
	 * 格式化日期
	 * @author linjian 
	 * @create_date 2013-6-20 下午3:26:12
	 * @param date
	 * @param format
	 * @return 格式化日期结果
	 */
	public static String getFormatDate(Date date,String format){
		return new SimpleDateFormat(format).format(date);
	}
}
