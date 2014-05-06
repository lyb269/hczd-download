package com.hczd.download.common.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hczd.download.common.constant.HZ_Constant;

/**
 * 日期计算辅助类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-20 下午2:49:06
 */
public class HZ_Date_CalacUtil {
	
	/**获取当前时间
	 * @author linjian
	 * @create_date 2013-12-23 下午5:37:01
	 * @return 当前时间
	 */
	public static String getCurrentTime(){
		return new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date());
	}
	
	/**
	 * 计算指定时间加上指定时间区域数量后的结果
	 * @author linjian 
	 * @create_date 2013-7-1 上午11:27:30
	 * @param date_time 指定时间
	 * @param part 计算部分
	 * @param num 计算数量
	 * @param ret_format 返回结果格式
	 * @return 计算后的时间
	 */
	public static String getDateStamp(String date,String part,int num,String ret_format){
		Calendar cld = Calendar.getInstance();
		try {
			cld.setTime(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).parse(date));
			if(StringUtils.isNotBlank(part)){
				if(part.equals("date") || part.equals("dd"))
					cld.add(Calendar.DATE, num);
				else if(part.equals("year") || part.equals("yyyy"))
					cld.add(Calendar.YEAR, num);
				else if(part.equals("month") || part.equals("MM"))
					cld.add(Calendar.MONTH, num);
				else if(part.equals("second") || part.equals("ss"))
					cld.add(Calendar.SECOND, num);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(StringUtils.isNotBlank(ret_format)){
			return new SimpleDateFormat(ret_format).format(cld.getTime());
		}else{
			return new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(cld.getTime());
		}
	}
	
	/**获取时间相减差距
	 * @author linjian 
	 * @create_date 2013-7-30 下午8:59:05
	 * @param time1
	 * @param time2
	 * @param part
	 * @return 差值
	 */
	public static int getTimeStamp(String time1,String time2,String part){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		try {
			cal1.setTime(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).parse(time1));
			cal2.setTime(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).parse(time2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long s_times = cal1.getTimeInMillis() - cal2.getTimeInMillis();
		if(StringUtils.isNotBlank(part)){
			if(part.equals("date") || part.equals("dd"))
				return (int)(s_times / 1000 / 60 / 60 / 24);
			else if(part.equals("hour") || part.equals("HH"))
				return (int)(s_times / 1000 / 60 / 60);
			else if(part.equals("minute") || part.equals("mm"))
				return (int)(s_times / 1000 / 60);
			else if(part.equals(HZ_DateFormateUtil.AREA_MONTH)){
				return ((Integer.parseInt(time1.substring(0, 4)) - Integer.parseInt(time2.substring(0, 4))) * 12 + (Integer.parseInt(time1.substring(5, 7)) - Integer.parseInt(time2.substring(5, 7))));
			}
		}else{
			throw new RuntimeException("获取时间差部分异常");
		}
		return 0;
	}
	
	
	/**
	 * 获取该月的最后一天
	 * @author linjian 
	 * @create_date 2013-7-2 上午11:46:25
	 * @param date_time 时间种子
	 * @return 该月的最后一天
	 */
	public static String getLastDayOfMonth(String date_time,String rs_format){
		if(StringUtils.isBlank(rs_format)){
			rs_format = HZ_DateFormateUtil.FORMATE_TIME;
		}
		Calendar cld = Calendar.getInstance();
		try {
		cld.setTime(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).parse(date_time));
		cld.set(Calendar.DAY_OF_MONTH, cld.getActualMaximum(Calendar.DAY_OF_MONTH));
		cld.set(Calendar.HOUR_OF_DAY, 23);
		cld.set(Calendar.MINUTE, 59);
		cld.set(Calendar.SECOND, 59);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat(rs_format).format(cld.getTime());
	}
	
	/**
	 * @author linjian 
	 * @create_date 2013-7-30 下午8:55:17
	 * @param date_time1
	 * @param date_time2
	 * @param format
	 * @return
	 */
	public static int compare(String date_time1,String date_time2){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		try {
			cal1.setTime(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).parse(date_time1));
			cal2.setTime(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).parse(date_time2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal1.compareTo(cal2);
	}
	
	/**
	 * 获取该月的第一天
	 * @author linjian 
	 * @create_date 2013-7-2 上午11:46:25
	 * @param date_time 时间种子
	 * @return 该月的最后一天
	 */
	public static String getFirstDayOfMonth(String date_time,String rs_format){
		if(StringUtils.isBlank(rs_format)){
			rs_format = HZ_DateFormateUtil.FORMATE_TIME;
		}
		Calendar cld = Calendar.getInstance();
		try {
			cld.setTime(new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).parse(date_time));
			cld.set(Calendar.DAY_OF_MONTH, cld.getActualMinimum(Calendar.DAY_OF_MONTH));
			cld.set(Calendar.HOUR_OF_DAY, 0);
			cld.set(Calendar.MINUTE, 0);
			cld.set(Calendar.SECOND, 0);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat(rs_format).format(cld.getTime());
	}
	
	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		String dayBefore = "";
		try {
			if(StringUtils.isNotBlank(specifiedDay)){
				SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_DATE_FORMAT);
				Calendar c = Calendar.getInstance();
				Date date = dateFormat.parse(specifiedDay);
	
				c.setTime(date);
				int day = c.get(Calendar.DATE);
				c.set(Calendar.DATE, day - 1);
				int hour = c.get(Calendar.HOUR);
				c.set(Calendar.HOUR, hour);
				dayBefore = dateFormat.format(c.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dayBefore;
	}

	/**
	 * 获得指定日期的前n天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayBefore(String specifiedDay, int n) {
		String dayBefore = "";
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_DATE_FORMAT);
			Calendar c = Calendar.getInstance();
			Date date = dateFormat.parse(specifiedDay);

			c.setTime(date);
			int day = c.get(Calendar.DATE);
			c.set(Calendar.DATE, day - n);
			int hour = c.get(Calendar.HOUR);
			c.set(Calendar.HOUR, hour);

			dayBefore = dateFormat.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		String dayAfter = "";
		try {
			Calendar c = Calendar.getInstance();
			Date date = null;
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);

			c.setTime(date);
			int day = c.get(Calendar.DATE);
			c.set(Calendar.DATE, day + 1);
			dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dayAfter;
	}

	/**
	 * 获得指定日期的后n天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay, int n) {
		String dayAfter = "";
		try {
			Calendar c = Calendar.getInstance();
			Date date = null;

			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);

			c.setTime(date);
			int day = c.get(Calendar.DATE);
			c.set(Calendar.DATE, day + n);

			dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dayAfter;
	}

	public static String SicenToComm(double value) { // 保留小数点后3位（四舍五入），且不按科学计数法输出
		String retValue = null;
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		retValue = df.format(value);
		retValue = retValue.replaceAll(",", "");
		return retValue;
	}

	/**
	 * 获得某年某月的第几天
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getMonthDay(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_DATE_FORMAT);
		return dateFormat.format(c.getTime());
	}

	/**
	 * 获取日期年份
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int getYear(String date) throws ParseException {
		date = parseDate(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_DATE_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		return calendar.get(Calendar.YEAR);
	}

	private static String parseDate(String date) throws ParseException {
		if (StringUtils.isNotBlank(date)) {
			if (date.length() < 8) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_DATE_FORMAT);
				SimpleDateFormat f = new SimpleDateFormat(
						HZ_Constant.DATETIME_STORE_DATE_MINUTE_FORMAT);
				date = dateFormat.format(f.parse(date));
			}
		}
		return date;
	}

	/**
	 * 获取日期月份
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int getMonth(String date) throws ParseException {
		date = parseDate(date);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_DATE_FORMAT);
		calendar.setTime(dateFormat.parse(date));
		return (calendar.get(Calendar.MONTH) + 1);
	}

	/**
	 * 获取日期号
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int getDay(String date) throws ParseException {
		date = parseDate(date);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_DATE_FORMAT);
		calendar.setTime(dateFormat.parse(date));
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取月份起始日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMinMonthDate(String date) throws ParseException {
		date = parseDate(date);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_DATE_FORMAT);
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime()) + " 00:00:00";
	}

	/**
	 * 获取月份最后日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMaxMonthDate(String date) throws ParseException {
		date = parseDate(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_DATE_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime()) + " 23:59:59";
	}

	/**
	 * 根据指定月份获取月份起始日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMinMonthDateByMonth(String date, Integer month)
			throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_DATE_FORMAT);
		date = parseDate(date);
		String m = date.substring(4, 7);
		date = date.replaceFirst(m, "-" + month);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 根据指定月份获取月份最后日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMaxMonthDateByMonth(String date, Integer month)
			throws ParseException {
		date = parseDate(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_DATE_FORMAT);
		String m = date.substring(4, 7);
		date = date.replaceFirst(m, "-" + month);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 获取下个月份最后日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getNextMaxMonthDate(String date) throws ParseException {
		date = parseDate(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_DATE_FORMAT);
		String m = date.substring(5, 7);
		date = date.replaceFirst("-" + m, "-" + (Integer.valueOf(m) + 1));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 获取下个月份起始日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getNextMinMonthDate(String date) throws ParseException {
		date = parseDate(date);
		String m = date.substring(5, 7);
		date = date.replaceFirst("-" + m, "-" + (Integer.valueOf(m) + 1));
		SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_DATE_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 获取上个月份最后日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getLastMaxMonthDate(String date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_DATE_FORMAT);
		date = parseDate(date);
//		String m = date.substring(5, 7);
//		date = date.replaceFirst("-" + m, "-" + (Integer.valueOf(m) - 1));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
//		calendar.set(Calendar.DAY_OF_MONTH,
//				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 获取上个月份起始日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getLastMinMonthDate(String date) throws ParseException {
		date = parseDate(date);
//		String m = date.substring(5, 7);
//		date = date.replaceFirst("-" + m, "-" + (Integer.valueOf(m) - 1));
		SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_DATE_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
//		calendar.set(Calendar.DAY_OF_MONTH,
//				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}
	
	public static int twoDateCalculatingTheNumber(String start, String end) {
		int quot = 0;
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_DATE_FORMAT);
			Date date1 = dateFormat.parse(end);
			Date date2 = dateFormat.parse(start);
			long quot1 = (date1.getTime() - date2.getTime());
			quot1 = (quot1) / 1000 / 60 / 60 / 24;
			quot = (int) quot1;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}
	/**
	 * 获得两个时间段之间的小时差
	 * @param start
	 * @param end
	 * @return
	 */
	public static double twoDateCalculatingToHour(String start, String end) {
		double quot = 0;
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_FORMAT);
			Date date1 = dateFormat.parse(end);
			Date date2 = dateFormat.parse(start);
			long quot1 = (date1.getTime() - date2.getTime());
			quot = (quot1) / 1000.0 / 60.0 / 60.0;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}
	
	/**获取某个时间的前N个小时
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2013-8-22 时间：上午11:01:28
	 * @param time 时间
	 * @param hour 小时
	 * @return
	 */
	public static String getOneHoursAgoTime(String date,Integer hour) {
		String oneHoursAgoTime = "";
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(HZ_Constant.DATETIME_FORMAT);
			long msec = dateFormat.parse(date).getTime() - hour*3600000;
	        oneHoursAgoTime = new SimpleDateFormat(HZ_Constant.DATETIME_FORMAT).format(msec);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return oneHoursAgoTime;
    } 
	
	/**
	 * 转换不同格式的日期
	 * @author linhui
	 * @create_date 2013-10-11 下午3:35:01
	 * @param oldDate
	 * @return
	 * @throws ParseException
	 */
	public static String translateFormat(String oldDate,String oldFormat,String newFormat){
		SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
        Date date;
        String newDate;
		try {
			date = sdf.parse(oldDate);
			newDate = new SimpleDateFormat(newFormat).format(date);
		} catch (ParseException e) {
			newDate = oldDate;
		}
        
        return newDate;
	}
	
	/**获取时间段内的月份列表
	 * @author linjian
	 * @create_date 2013-11-5 上午9:39:13
	 * @param fromDate
	 * @param toDate
	 * @return 时间间隔内的月份列表格式例如：2013-09
	 */
	public static List<String> getMonthsFromTo(String fromDate,String toDate){
		List<String> listStr = null;
		try {
			int fromYear = Integer.parseInt(fromDate.split("-")[0]);
			int toYear = Integer.parseInt(toDate.split("-")[0]);
			int fromMonth = Integer.parseInt(fromDate.split("-")[1]);
			int toMonth = Integer.parseInt(toDate.split("-")[1]);
			//如果开始日期小于结束日期
			if(fromYear == toYear && fromMonth <= toMonth || fromYear < toYear){
				int months = (toYear - fromYear) * 12 + (toMonth - fromMonth);
				listStr = new ArrayList<String>();
				for(int i = fromMonth; i <= (fromMonth + months); i++){
					listStr.add((fromYear + i / 13) + "-" + (((i - 1) % 12 + 1) < 10 ? ("0" + ((i - 1) % 12 + 1)) : ((i - 1) % 12 + 1)));
				}
			}else{
				throw new RuntimeException("结束日期不能小于开始日期");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listStr;
	}
	
	/**获取两个时间的实际天数差（例如：2014-03-21 12:00:00 到 2014-03-23 01:00:00 天数差为3天）
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-3-21 时间：下午3:03:10
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static int actualCalculatingDay(String startTime,String endTime){
		SimpleDateFormat format = new SimpleDateFormat(HZ_Constant.DATETIME_FORMAT);
		int actual_days = 1;
		try {
			if(format.parse(endTime).getTime() > format.parse(startTime).getTime()){
				while (!endTime.substring(0, 10).equals(startTime.substring(0, 10))) {
					actual_days ++;
					startTime = getSpecifiedDayAfter(startTime);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actual_days;
	}
	
	/**比较时间大小(time > time2 返回 true)
	 * @author: chenfm
	 * @version 2.0
	 * @date： 日期：2014-3-22 时间：下午2:44:25
	 * @param time
	 * @param time2
	 * @return
	 */
	public static boolean casinoWar(String time,String time2,String date_format){
		boolean bool = false;
		SimpleDateFormat format = new SimpleDateFormat(date_format);
		try {
			if(format.parse(time).getTime()>=format.parse(time2).getTime()){
				bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}
	
	/**
	 * 测试函数
	 * @author linjian 
	 * @create_date 2013-6-20 下午3:07:38
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		System.out.println(getTimeStamp("2014-03-22 23:46:42", "2014-03-14 22:23:42", HZ_DateFormateUtil.AREA_HOUR));
		System.out.println("2013-10-12 00:00:00".substring(0,4));
	}
	
	/**
	 * 获取两个时间小时差(不足一小时是算一小时)
	 * @author linhui
	 * @create_date 2014-3-27 上午9:19:07
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static int dateDiff(String startTime, String endTime,   
            String format) {   
        // 按照传入的格式生成一个simpledateformate对象   
        SimpleDateFormat sd = new SimpleDateFormat(format);   
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数   
        long nh = 1000 * 60 * 60;// 一小时的毫秒数   
        long nm = 1000 * 60;// 一分钟的毫秒数   
        long diff;   
        long day = 0;   
        long hour = 0;   
        long min = 0;   
        // 获得两个时间的毫秒时间差异   
        try {   
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();   
            day = diff / nd;// 计算差多少天   
            hour = diff % nd / nh + day * 24;// 计算差多少小时   
            min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟   
            //不足一小时以一小时计算
            if(min%60>0){
            	hour = hour +1;
            } 
        } catch (ParseException e) {   
            e.printStackTrace();   
        }   
          return (int)hour;
    }
}
