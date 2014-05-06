package com.hczd.download.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 实体完整性验证辅助类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-24 上午10:13:33
 */
public class HZ_ValidateUtil {
	
	private static final String onlyNum = "^[0-9]*{1}";
	/** 
	* 验证身份证号码 
	* @param id_number 
	* @return 
	*/  
	public static Boolean checkNID(String id_number){  
		if(id_number.length() != 15 && id_number.length() != 18){  
			return false;  
		}  
		String string = id_number.substring(0, id_number.length() - 1);  
		if(!string.matches(onlyNum)){  
			return false;  
		}  
		return true;  
	}  
	
	/** 
	* 验证邮箱 
	* @param email 
	* @return 
	*/  
	  
	public static Boolean checkEmail(String email) {  
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}{1}";  
		Pattern regex = Pattern.compile(check);  
		Matcher matcher = regex.matcher(email);  
		boolean isMatched = matcher.matches();  
		return isMatched;  
	}
	
	/**手机号码验证
	 * @author linjian 
	 * @create_date 2013-6-27 上午11:02:13
	 * @param mobile
	 * @return 验证结果
	 */
	public static Boolean checkMoblie(String mobile){
		if(StringUtils.isBlank(mobile) || mobile.trim().length() != 11){
			return false;
		}
		return true;
	}
	
	/**电话号码验证
	 * @author linhui
	 * @create_date 2013-7-18 上午11:02:13
	 * @param phonenumber
	 * @return 验证结果
	 */
    public static boolean isTelephone(String phonenumber) {
        String phone = "0\\d{2,3}-\\d{7,8}";
        Pattern p = Pattern.compile(phone);
        Matcher m = p.matcher(phonenumber);
        return m.matches();
    }
    
    /**
     * 验证金额
     * @author linhui
     * @create_date 2013-8-16 下午2:11:20
     * @param moneyNum
     * @return
     */
    public static boolean isMoney(String moneyNum){
    	String money = "(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$";
    	Pattern p = Pattern.compile(money);
    	Matcher m = p.matcher(moneyNum);
    	return m.matches();
    }
    
    /**
     * 验证数字
     * @author linhui
     * @create_date 2014-1-22 上午9:42:11
     * @param number
     * @return
     */
    public static boolean isNumber(String number){
    	String num = "^[1-9]\\d*$";
    	Pattern p = Pattern.compile(num);
    	Matcher m = p.matcher(number);
    	return m.matches();
    }
    
    /**
     * 验证时间	
     * @author linhui
     * @create_date 2013-8-16 下午2:11:20
     * @param time
     * @return
     */
    public static boolean isTime(String time){
    	String t = "^\\d{4}-(0?[1-9]|[1][012])-(0?[1-9]|[12][0-9]|[3][01])[\\s]+([0-1][0-9]|2?[0-3]):([0-5][0-9]):([0-5][0-9])$";
    	Pattern p = Pattern.compile(t);
    	Matcher m = p.matcher(time);
    	return m.matches();
    }
    
    /**检验车牌号
     * @author: chenfm
     * @version 2.0
     * @date： 日期：2014-3-14 时间：下午1:46:09
     * @param vehicle_no
     * @return
     */
    public static boolean isVehicle(String vehicle_no) {  
    	String t = "^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9_\u4e00-\u9fa5]{5}$";
    	Pattern p = Pattern.compile(t);
    	Matcher m = p.matcher(vehicle_no);
    	return m.matches();
    	
    } 
   
    /**  
     * 校验银行卡卡号  
     * @author linjian
     * @create_date 2013-11-1 上午8:57:52
     * @param cardId 银行卡卡号
     * @return 校验结果
     */   
    public static boolean checkBankCard(String cardId) {  
    	char  bit = getBankCardCheckCode(cardId.substring( 0 , cardId.length() -  1 ));  
    	if (bit ==  'N' ){  
    		return   false ;  
    	}  
    	return  cardId.charAt(cardId.length() -  1 ) == bit;  
    } 
    
    /**  
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位  
     * @author linjian
     * @create_date 2013-11-1 上午8:57:52
     * @param nonCheckCodeCardId  
     * @return  校验结果
     */   
    public static char getBankCardCheckCode(String nonCheckCodeCardId){  
        if (nonCheckCodeCardId ==  null  || nonCheckCodeCardId.trim().length() ==  0   
                || !nonCheckCodeCardId.matches("\\d+" )) {  
         //如果传的不是数据返回N   
            return   'N' ;  
        }  
        char [] chs = nonCheckCodeCardId.trim().toCharArray();  
        int  luhmSum =  0 ;  
        for ( int  i = chs.length -  1 , j =  0 ; i >=  0 ; i--, j++) {  
            int  k = chs[i] -  '0' ;  
            if (j %  2  ==  0 ) {  
                k *= 2 ;  
                k = k / 10  + k %  10 ;  
            }  
            luhmSum += k;             
        }  
        return  (luhmSum %  10  ==  0 ) ?  '0'  : ( char )(( 10  - luhmSum %  10 ) +  '0' );  
    }
    
    /** 验证字符串是否有包含中文符号
     * @author: chenfm
     * @version 2.0
     * @date： 日期：2014-4-3 时间：下午1:36:57
     * @param name
     * @return
     */
    public static boolean ChineseSign(String name){
    	String t = "[\u3002\uff1b\uff0c\uff1a\u201c\u201d\uff08\uff09\u3001\uff1f\u300a\u300b]";
    	Pattern p = Pattern.compile(t);
    	Matcher m = p.matcher(name);
    	return m.find();
    }
}