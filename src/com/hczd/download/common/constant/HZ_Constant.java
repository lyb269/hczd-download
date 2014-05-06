package com.hczd.download.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统常量
 * 
 * @author wucc
 * @version 2.0
 */
public final class HZ_Constant {

	/********************* 时间格式化 ********************/
	/**
	 * yyyy-MM-dd'T'HH:mm:ss
	 */
	public static final String DATETIME_FORMAT_T = "yyyy-MM-dd'T'HH:mm:ss"; // 系统全局显示日期+时间格式
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss"; // 系统全局显示日期+时间格式
	/**
	 * yyyy
	 */
	public static final String DATETIME_YYYY = "yyyy"; // 系统全局显示日期格式
	/**
	 * yyyyMM
	 */
	public static final String DATETIME_YYYYMM = "yyyyMM"; // 系统全局显示日期格式
	/**
	 * yyyy-MM-dd
	 */
	public static final String DATETIME_DATE_FORMAT = "yyyy-MM-dd"; // 系统全局显示日期格式
	/**
	 * HH:mm:ss
	 */
	public static final String DATETIME_TIME_FORMAT = "HH:mm:ss"; // 系统全局显示时间格式
	/**
	 * MM-dd
	 */
	public static final String DATETIME_SINGLE_DATE_FORMAT = "MM-dd"; // 系统全局显示无年份日期格式
	/**
	 * yyyy-MM-dd HH:00
	 */
	public static final String DATETIME_DATETIME_HOUR_FORMAT = "yyyy-MM-dd HH:00"; // 系统全局显示日期+小时格式
	/**
	 * yyyy-MM-dd HH:mm
	 */
	public static final String DATETIME_MINUTE_FORMAT = "yyyy-MM-dd HH:mm"; // 系统全局显示日期+小时分钟格式
	/**
	 * yyyyMMddHHmmss
	 */
	public static final String DATETIME_STORE_DATETIME_FORMAT = "yyyyMMddHHmmss"; // 系统全局存储日期+时间格式
	/**
	 * yyyy-MM-dd
	 */
	public static final String DATETIME_STORE_DATE_FORMAT = "yyyy-MM-dd"; // 系统全局存储日期格式
	/**
	 * HHmmss
	 */
	public static final String DATETIME_STORE_TIME_FORMAT = "HHmmss"; // 系统全局存储时间格式
	/**
	 * MMdd
	 */
	public static final String DATETIME_STORE_SINGLE_DATE_FORMAT = "MMdd"; // 系统全局存储无年份日期格式
	/**
	 * yyyyMMddHH
	 */
	public static final String DATETIME_STORE_DATETIME_HOUR_FORMAT = "yyyyMMddHH"; // 系统全局存储日期+小时格式
	/**
	 * yyyyMMddHHmm
	 */
	public static final String DATETIME_STORE_DATETIME_MINUTE_FORMAT = "yyyyMMddHHmm"; // 系统全局存储日期+小时分钟格式
	/**
	 * yyyy-MM
	 */
	public static final String DATETIME_STORE_DATE_MINUTE_FORMAT = "yyyy-MM"; // 系统全局存储日期+小时分钟格式

	/**
	 * action 返回的字符串 show
	 */
	public static final String ACTION_SHOW = "show";
	/**
	 * action 返回的字符串 list
	 */
	public static final String ACTION_LIST = "list";
	/**
	 * action 返回的字符串 edit
	 */
	public static final String ACTION_EDIT = "edit";
	/**
	 * action 返回的字符串 save
	 */
	public static final String ACTION_SAVE = "save";
	/**
	 * action 返回的字符串 savenew
	 */
	public static final String ACTION_SAVE_NEW = "savenew";
	/**
	 * action 返回的字符串 没有权限 notpower
	 */
	public static final String ACTION_NOT_POWER = "notpower";
	/**
	 * action 返回的字符串 saveclose
	 */
	public static final String ACTION_SAVE_CLOSE = "saveclose";
	/**
	 * 系统文件的存放根目录
	 */
	public static final String ROOT_MULTIMEDIA = "gx_data";
	/**
	 * 正则表达式 邮箱
	 */
	public static final String REGULAR_EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	/**
	 * 正则表达式 IP地址
	 */
	public static final String REGULAR_IP = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
	/**
	 * 正则表达式 日期时间，解决润月
	 */
	public static final String REGULAR_DATE = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
	/**
	 * 正则表达式 手机
	 */
	public static final String REGULAR_MODILE_PHONE = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
	/**
	 * 正则表达式 以字母开头,字母数字混合的字符串
	 */
	public static final String REGULAR_LETTER_NUMBER = "^[a-zA-Z][a-zA-Z0-9]+";
	/**
	 * 正则表达式 数字
	 */
	public static final String REGULAR_NUMBER = "^[1-9][0-9]+";
	/**
	 * 后台用户登入信息存储的变量
	 */
	public static final String ACL_BACK = "acl";
	/**
	 * 前台用户登入信息存储的变量
	 */
	public static final String ACL_FRONT = "front_acl";

	/**
	 * 图片存放目录名称
	 */
	public static final String MULTIMEDIA_IMG_DIR = "images";
	/**
	 * 文档存放目录名称
	 */
	public static final String MULTIMEDIA_DOM_DIR = "document";
	/**
	 * 文章附件存放目录名称
	 */
	public static final String MULTIMEDIA_DOM_ACCESSORY_DIR = "accessory";
	/**
	 * 文档存放目录名称
	 */
	public static final String MULTIMEDIA_DOM_DOCUMENT_DIR = "document";
	/**
	 * 流程定义文件存放目录名称
	 */
	public static final String MULTIMEDIA_PROCESS_DIR = "process";

	public static final String SORT_KEY_ORDERING = "ordering";
	public static final String SORT_KEY_TYPE = "type";
	public static final String ORDER_DESC = "desc";
	public static final String ORDER_ASC = "asc";

	public static final Map<String, String> SMS_PARAMS = new HashMap<String, String>();
	static {
		setSMS_Params(); // 初始化文件类型信息
	}

	private static void setSMS_Params() {

		SMS_PARAMS.put("id", "modem.com1");
		SMS_PARAMS.put("comPort", "COM4");
		SMS_PARAMS.put("bandrate", "115200");
		SMS_PARAMS.put("manufacturer", "wavecom");
		SMS_PARAMS.put("model", "17254");
	}
	
}
