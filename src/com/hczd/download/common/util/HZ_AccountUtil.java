package com.hczd.download.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 客户账户帮助类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-14 下午5:48:44
 */
public class HZ_AccountUtil {
	
	/**根据格式四舍五入
	 * @author linjian 
	 * @create_date 2013-7-26 下午5:12:02
	 * @param num
	 * @param format
	 * @return 结果
	 */
	public static String format(String num,String format){
		if(StringUtils.isBlank(num))return "0";
		java.text.DecimalFormat df = new java.text.DecimalFormat(format);
		return df.format(Double.parseDouble(num));
	}
	
	/**
	 * 根据金额加密
	 * @author linjian 
	 * @create_date 2013-6-14 下午5:49:57
	 * @param balance
	 * @return
	 */
	public static String serByBalance(String balance){
		return encrypt(balance);
	}
	
	public static void main(String[] args) {
		System.out.println(sum("1979.4950000000001", "134.2", "#.##"));
	}
	
	/**
	 * 字符串数字相加
	 * @author linjian 
	 * @create_date 2013-6-14 下午6:10:06
	 * @param num1
	 * @param num2
	 * @return 计算结果
	 */
	public static String sum(String num1,String num2,String format){
		if(StringUtils.isBlank(num1))num1 = "0";
		if(StringUtils.isBlank(num2))num2 = "0";
		DecimalFormat df = new DecimalFormat(format);
		Double d1 = new Double(num1);
		Double d2 = new Double(num2);
		return df.format(d1 + d2).toString();
	}
	
	/**
	 * 减法
	 * @author linjian 
	 * @create_date 2013-7-1 上午10:42:29
	 * @param num1
	 * @param num2
	 * @param format
	 * @return 差
	 */
	public static String subtraction(String num1,String num2,String format){
		if(StringUtils.isBlank(num1))num1 = "0";
		if(StringUtils.isBlank(num2))num2 = "0";
		DecimalFormat df = new DecimalFormat(format);
		Double d1 = new Double(num1);
		Double d2 = new Double(num2);
		return df.format(d1 - d2).toString();
	}
	
	/**
	 * 除法
	 * @author linjian 
	 * @create_date 2013-7-1 上午10:25:18
	 * @param num1
	 * @param num2
	 * @param format
	 * @return 商
	 */
	public static String division(String num1,String num2,String format){
		if(StringUtils.isBlank(num1))num1 = "0";
		if(StringUtils.isBlank(num2))num2 = "0";
		DecimalFormat df = new DecimalFormat(format);
		Double d1 = new Double(num1);
		Double d2 = new Double(num2);
		return df.format(d1 / d2).toString();
	}
	
	/**
	 * 乘法
	 * @author linjian 
	 * @create_date 2013-7-1 上午10:41:36
	 * @param num1
	 * @param num2
	 * @param format
	 * @return 积
	 */
	public static String multiplication(String num1,String num2,String format){
		if(StringUtils.isBlank(num1))num1 = "0";
		if(StringUtils.isBlank(num2))num2 = "0";
		DecimalFormat df = new DecimalFormat(format);
		Double d1 = new Double(num1);
		Double d2 = new Double(num2);
		return df.format(d1 * d2).toString();
	}
	
	/**
	 * 加密方法
	 * @author linjian 
	 * @create_date 2013-5-25 下午1:50:35
	 * @param password
	 * @return 加密后的字符串
	 */
	public static String encrypt(String password) {

		MessageDigest md;
		try {

			md = MessageDigest.getInstance("MD5");
			
			int size = password.length()/2;
			md.update((password+(size!=0?password.substring(size-1,size):"")).getBytes());

			return Base64.encode(md.digest());

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}
}
