package com.hczd.download.access.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hczd.download.access.module.HZ_Access_Card;
import com.hczd.download.access.module.HZ_Access_Card_Consumption_Log;
import com.hczd.download.access.module.HZ_Access_Card_History;
import com.hczd.download.access.service.IHZ_Access_CardService;
import com.hczd.download.access.service.IHZ_Access_Card_Allocation_RecordService;
import com.hczd.download.access.service.IHZ_Access_Card_Consumption_LogService;
import com.hczd.download.access.service.IHZ_Access_Card_HistoryService;
import com.hczd.download.card.util.HZ_Gas_Card_Excel_Util;
import com.hczd.download.common.util.HZ_DateFormateUtil;
import com.hczd.download.customer.module.HZ_Customer_Anchored_History;
import com.hczd.download.customer.service.IHZ_CustomerService;
import com.hczd.download.customer.service.IHZ_Customer_Anchored_HistoryService;
/**
 * 类描述：上传通行卡消费数据
 * @author: chenfm
 * @version 2.0
 * @date： 日期：2014-1-9 时间：上午10:24:36
 */
@Component
public class HZ_Access_Card_Consumption_LogUtil implements Runnable {
	public static HttpSession session;
	public static String con_info="";
	
	//步骤
	public static String step = "1";
	//下载进度
	public static int f_count = 0;
	public static int s_count = 0;
	public static int d_total = 0;
	//合并进度
	public static int m_s_count = 0;
	public static int m_f_count = 0;
	public static String r_msg = "初始化。。。";
	public static String t_msg = "初始化。。。<br>";
	//当前是否完成合并
	public  static boolean is_exit = true;
	
	public String date;
	/**
	 * 记录当前线程下下载消费数据的用户编号
	 */
	public static Integer current_download_user_id;
	public static String current_download_user_name;
	private List<Object> list_cardNo;
	private String data_root;
	private String action = "下载";
	private Integer main_id;
	private String start_time;
	private String end_time;
	
	//通行卡业务逻辑类
	@Autowired
	private IHZ_Access_CardService hz_access_cardService;
	//通行卡历史归属记录逻辑类
	@Autowired
	private IHZ_Access_Card_HistoryService hz_access_card_historyService;
	//通行卡消费日志
	@Autowired
	private IHZ_Access_Card_Consumption_LogService hz_access_card_consumption_logService;
	//客户逻辑类
	@Autowired
	private IHZ_CustomerService hz_customerService;
	//挂靠管理
	@Autowired
	private IHZ_Customer_Anchored_HistoryService hz_customer_anchored_historyService;
	@Autowired
	private HZ_AccessUtil hz_accessUtil;
	
	public HZ_Access_Card_Consumption_LogUtil(){}
	/**
	 * 构造方法
	 * @author linjian
	 * @create_date 2013-5-3 上午9:45:42
	 * @param startDate
	 * @param endDate
	 * @param main_card
	 * @param list_cardNo
	 * @param data_root
	 */
	public HZ_Access_Card_Consumption_LogUtil(String action,String date,
			List<Object> list_cardNo,String data_root,
			IHZ_Access_CardService hz_access_cardService,
			IHZ_Access_Card_HistoryService hz_access_card_historyService,
			IHZ_Access_Card_Consumption_LogService hz_access_card_consumption_logService,
			IHZ_CustomerService hz_customerService,
			IHZ_Access_Card_Allocation_RecordService hz_access_card_allocation_recordService,
			IHZ_Customer_Anchored_HistoryService hz_customer_anchored_historyService,
			Integer main_id,String start_time,String end_time){
		
		this.action = action;
		this.list_cardNo = list_cardNo;
		this.data_root  = data_root;
		this.date = date;
		
		this.hz_customerService = hz_customerService;
		this.hz_access_cardService = hz_access_cardService;
		this.hz_access_card_historyService = hz_access_card_historyService;
		this.hz_access_card_consumption_logService = hz_access_card_consumption_logService;
		this.hz_customer_anchored_historyService = hz_customer_anchored_historyService;
	}
	
    /***
     * 线程下载
     */
	public void run() {
		is_exit = false;
		try{// TODO Auto-generated method stub
		//上传消费数据
		uploadConsumption();
		//计算消费数据
		//启动一个线程
		//new Thread(new HZ_Auto_Calculate_Consumption_LogUtil(hz_access_cardService, hz_access_card_consumption_logService, hz_access_card_allocation_recordService,main_id,null,start_time,end_time)).start();
		//设置完成
		HZ_Access_Card_Consumption_LogUtil.is_exit = true;
		}catch(Exception e){
			e.printStackTrace();
			put_err("上传消费数据出错！:" + e.getMessage());
		}finally{
			is_exit = true;
		}
	}
	
	/**
	 * 上传到数据库中
	 * @author linyb
	 * @create_date 2014-6-4下午2:42:39
	 */
	public void upload_Consumption(List<HZ_Access_Card_Consumption_Log> logs,StringBuilder sb) {
		//缓存传上来的消费数据
		List<HZ_Access_Card_Consumption_Log> list_temp_consumption = new ArrayList<HZ_Access_Card_Consumption_Log>();
		Integer row = null; //用来标记第几行出现异常
		HZ_Access_Card access_card = null;
		try {
			//第一步：获取消费数据中时间最大值
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("column", "to_char(max( to_timestamp(outlet_transit_time,'YYYY-MM-DD HH24:MI:SS') ),'yyyy-mm-dd hh24:mi:ss') ");
			params.put("card_no_in", "select card_no from HZ_Access_Card ");
			//第二步：获取最近两天内消费数据唯一码有时间和卡号组成
			Set<String> set_unique_var = hz_access_card_consumption_logService.getUnique_Var();
			//第三步：遍历excel的消费数，设置导入时间
			String import_time = new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date());
			int size = logs.size();
			for (int rows =size-1; rows >= 0; rows--) {
				HZ_Access_Card_Consumption_Log log = logs.get(rows);
				row = rows;
				hz_accessUtil.print_msg(sb, "正在导入第 " + (size- rows) + " 条数据..");
				//根据时间判断是否数据库中之前已经存在，如果已经存在则继续下一条
				String d_time = log.getOutlet_transit_time();
				//进行excel数据解析
				if(set_unique_var == null ||
						!set_unique_var.contains(log.getCard_no().trim() + log.getOutlet_transit_time().trim())){
					String card_no = log.getCard_no();
					if (StringUtils.isNotBlank(card_no)) {
						Map<String, Object> cardparams = new HashMap<String, Object>();
						cardparams.put("card_no", card_no);
						cardparams.put("column", "card_no");
						Object obj = hz_access_cardService.getScale(cardparams);
						//通行卡历史记录
						HZ_Access_Card_History access_card_history = null;

						// 导入消费数据时可能原来的卡已经退卡了,或者给另一个客户使用时,这时候
						if (obj != null && StringUtils.isNotBlank(obj.toString().trim())) {
							//获取历史通行卡归属
							params = new HashMap<String, Object>();
							params.put("card_no", card_no);
							params.put("a_sql", " start_time <='"+log.getOutlet_transit_time()+"' and end_time >='"+log.getOutlet_transit_time()+"' ");
							access_card_history = hz_access_card_historyService.get(params);
						}
						//如果有该卡的历史归属记录
						if(access_card_history != null){
							log.setCustomer_id(access_card_history.getCustomer_id());
							log.setCustomer_name(access_card_history.getCustomer_name());
							log.setVehicle_id(access_card_history.getVehicle_id());
							log.setVehicle_no(access_card_history.getVehicle_no());
							log.setMain_id(access_card_history.getMain_id());
							log.setCard_id(access_card_history.getCard_id());
						}else{
							params = new HashMap<String, Object>();
							params.put("card_no", log.getCard_no().trim());
							params.put("status_in", "2,-2,-1,-3,-4,-6,0");
							//查询通行卡信息
							access_card = hz_access_cardService.get(params);
							if(access_card ==  null){
								params = new HashMap<String, Object>();
								params.put("card_no", card_no);
								params.put("a_sql", " start_time <='"+log.getOutlet_transit_time()+"' and end_time >='"+log.getOutlet_transit_time()+"' ");
								HZ_Access_Card_History card_history = hz_access_card_historyService.get(params);
								if(card_history != null){
									access_card = new HZ_Access_Card();
									access_card.setCard_no(log.getCard_no().trim());
									access_card.setCustomer_id(card_history.getCustomer_id());
									access_card.setVehicle_id(card_history.getVehicle_id());
									access_card.setVehicle_no(card_history.getVehicle_no());
									access_card.setMain_card_id(card_history.getMain_id());
									
								}
							}
							if(access_card !=  null){
								params = new HashMap<String, Object>();
								params.put("column", "name");
								params.put("id", access_card.getCustomer_id());
								//获取客户名称
								Object obj_name = hz_customerService.getScale(params);
								//设置消费数据信息
								log.setCustomer_id(access_card.getCustomer_id());
								log.setCustomer_name(obj_name.toString());
								log.setVehicle_id(access_card.getVehicle_id());
								log.setVehicle_no(access_card.getVehicle_no());
								log.setMain_id(access_card.getMain_card_id());
								log.setCard_id(access_card.getId());
							}else{
								hz_accessUtil.print_err_msg(sb, log.getCard_no().trim()+"在系统中不存在,不进行导入");
							}
						}
						
						/**设置挂靠客户名字*/
						HZ_Customer_Anchored_History history = hz_customer_anchored_historyService.getAnchoredByTime(log.getCustomer_id(), log.getOutlet_transit_time());
						if(history != null){
							log.setAnchor_customer_id(history.getTop_customer_id());
							log.setAnchor_customer_name(history.getTop_customer_name());
						}else{
							/**没有挂靠关系则默认为通行卡的拥有客户**/
							log.setAnchor_customer_id(log.getCustomer_id());
							log.setAnchor_customer_name(log.getCustomer_name());
						}
						//设置记录时间
						log.setImport_time(import_time);
						//if(access_card !=  null){
							list_temp_consumption.add(log);
						//}
						if(list_temp_consumption.size()>500){
							//分批次保存，每五百条保存一次
							hz_access_card_consumption_logService.saveList(list_temp_consumption);
							list_temp_consumption = new ArrayList<HZ_Access_Card_Consumption_Log>();
						}
					}//end 卡号不为空
				}else{
					hz_accessUtil.print_err_msg(sb,"在第" + (size-row) + "行，该消费数据时间点：" + d_time + "已经处理过了，不进行重复导入");
				}
			}
			//批量保存
			if(list_temp_consumption.size()!=0){
				hz_access_card_consumption_logService.saveList(list_temp_consumption);
			}
			hz_accessUtil.print_msg(sb, "导入数据成功，已经完成所有导入数据！");
			HZ_Access_Card_Consumption_LogUtil.step = "6";
	    	HZ_Access_Card_Consumption_LogUtil.r_msg = "完成所有操作";
	    	HZ_Access_Card_Consumption_LogUtil.t_msg = HZ_Access_Card_Consumption_LogUtil.t_msg + "数据导入成功！<br>";
		} catch (Exception e) {
			if (row == null) {
				hz_accessUtil.print_err_msg(sb,"在导入中出现严重错误!数据无法导入!");
			} else {
				hz_accessUtil.print_err_msg(sb,"在第" + row + "行中出现错误,导致数据导入失败,请检查数据是否有误!");
			}
			hz_accessUtil.print_err_msg(sb,e.getLocalizedMessage());
			e.printStackTrace();
		}finally{
			hz_accessUtil.print_msg(sb, "去除重复数据中..");
			//去除重复记录
			hz_access_card_consumption_logService.deleteRepeat(); // 删除数据
			hz_accessUtil.print_msg(sb, "完成操作！");
		}
	}
	/**上传到数据库中
	 * @author linjian 
	 * @create_date 2013-5-3 下午5:57:39
	 */
	public void uploadConsumption() {
		//缓存传上来的消费数据
		List<HZ_Access_Card_Consumption_Log> list_temp_consumption = new ArrayList<HZ_Access_Card_Consumption_Log>();
			Integer row = null; //用来标记第几行出现异常
			try {
				HZ_Gas_Card_Excel_Util excel = new HZ_Gas_Card_Excel_Util();
				excel.getWorkBook(data_root + "/" + date + ".xls");
				excel.getSheet(0);
				//第一步：获取消费数据中时间最大值
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("column", "to_char(max( to_timestamp(outlet_transit_time,'YYYY-MM-DD HH24:MI:SS') ),'yyyy-mm-dd hh24:mi:ss') ");
				params.put("card_no_in", "select card_no from HZ_Access_Card ");
				//第二步：获取最近两天内消费数据唯一码有时间和卡号组成
				Set<String> set_unique_var = hz_access_card_consumption_logService.getUnique_Var();
				//第三步：遍历excel的消费数，设置导入时间
				String import_time = new SimpleDateFormat(HZ_DateFormateUtil.FORMATE_TIME).format(new Date());
				for (int rows = 0; rows < excel.getRows(); rows++) {
					row = rows;
					if(rows == 0){
						String card_no = excel.getValue(0, 0);
						String inlet_transit_time = excel.getValue(1, 0);
						String outlet_transit_time = excel.getValue(2, 0);
						String closing_date = excel.getValue(3, 0);
						String transit_section = excel.getValue(4, 0);
						String receivable_money = excel.getValue(5, 0);
						String money_num = excel.getValue(6, 0);
						String return_money_num = excel.getValue(7, 0);
						String bill_no = excel.getValue(8, 0);
						String vehicle_passion = excel.getValue(9, 0);
						String charge_type = excel.getValue(10, 0);
						String charge_all_up = excel.getValue(11, 0);
						String charge_pattern = excel.getValue(12, 0);
						String settlement_rovinces = excel.getValue(13, 0);
						if(StringUtils.isNotBlank(card_no)&&card_no.equals("通行卡号")
								&&StringUtils.isNotBlank(inlet_transit_time)&&inlet_transit_time.equals("入口通行时间")
								&&StringUtils.isNotBlank(outlet_transit_time)&&outlet_transit_time.equals("出口通行时间")
								&&StringUtils.isNotBlank(closing_date)&&closing_date.equals("银行结算日期")
								&&StringUtils.isNotBlank(transit_section)&&transit_section.equals("通行区间")
								&&StringUtils.isNotBlank(receivable_money)&&receivable_money.equals("应收金额(元)")
								&&StringUtils.isNotBlank(money_num)&&money_num.equals("收费金额(元)")
								&&StringUtils.isNotBlank(return_money_num)&&return_money_num.equals("补退款金额(元)")
								&&StringUtils.isNotBlank(bill_no)&&bill_no.equals("发票号")
								&&StringUtils.isNotBlank(vehicle_passion)&&vehicle_passion.equals("车情")
								&&StringUtils.isNotBlank(charge_type)&&charge_type.equals("收费类型")
								&&StringUtils.isNotBlank(charge_all_up)&&charge_all_up.equals("收费总重(公斤)")
								&&StringUtils.isNotBlank(charge_pattern)&&charge_pattern.equals("收费轴型")
								&&StringUtils.isNotBlank(settlement_rovinces)&&settlement_rovinces.equals("结算省份")
						){
						}else{
							put_err("数据导入失败,请按照格式:通行卡号,入口通行时间,出口通行时间,银行结算日期	通行区间,应收金额(元),收费金额(元),补退款金额(元),发票号,车情,收费类型,收费总重(公斤),收费轴型,结算省份;并且数据对应");
							break;
						}
					}else{
						put_msg("正在导入第" + rows + "条数据。。。");
						//根据时间判断是否数据库中之前已经存在，如果已经存在则继续下一条
						String d_time = excel.getValue(2, rows);
						//进行excel数据解析
						if(set_unique_var == null || !set_unique_var.contains(excel.getValue(0, rows).trim() + excel.getValue(2, rows).trim())){
							String card_no = excel.getValue(0, rows);
							if (StringUtils.isNotBlank(card_no)) {
								Map<String, Object> cardparams = new HashMap<String, Object>();
								cardparams.put("card_no", card_no);
								cardparams.put("column", "card_no");
								Object obj = hz_access_cardService.getScale(cardparams);
								//通行卡历史记录
								HZ_Access_Card_History access_card_history = null;
	
								// 导入消费数据时可能原来的卡已经退卡了,或者给另一个客户使用时,这时候
								if (obj != null && StringUtils.isNotBlank(obj.toString().trim())) {
									//获取历史通行卡归属
									params = new HashMap<String, Object>();
									params.put("card_no", card_no);
									params.put("a_sql", " start_time <='"+excel.getValue(2, rows)+"' and end_time >='"+excel.getValue(2, rows)+"' ");
									access_card_history = hz_access_card_historyService.get(params);
								}
								//新建一个消费记录
								HZ_Access_Card_Consumption_Log log = new HZ_Access_Card_Consumption_Log();
								log.setCard_no(excel.getValue(0, rows));				// 通行卡号
								log.setInlet_transit_time(excel.getValue(1, rows));		// 入口通行时间
								log.setOutlet_transit_time(excel.getValue(2, rows));	// 出口通行时间
								log.setClosing_date(excel.getValue(3, rows));			// 银行结算日期
								log.setTransit_section(excel.getValue(4, rows));		// 通行区间
								log.setReceivable_money(excel.getValue(5, rows));		// 应收金额(元)
								log.setMoney_num(excel.getValue(6, rows));				// 收费金额(元)
								log.setReturn_money_num(excel.getValue(7, rows));		// 补退款金额(元)
								log.setBill_no(excel.getValue(8, rows));				// 发票号
								log.setVehicle_passion(excel.getValue(9, rows));		// 车情
								log.setCharge_type(excel.getValue(10, rows));			// 收费类型
								log.setCharge_all_up(excel.getValue(11, rows));			// 收费总重(公斤)
								log.setCharge_pattern(excel.getValue(12, rows));		// 收费轴型
								log.setSettlement_rovinces(excel.getValue(13, rows));	// 结算省份
								
								//如果有该卡的历史归属记录
								if(access_card_history != null){
									log.setCustomer_id(access_card_history.getCustomer_id());
									log.setCustomer_name(access_card_history.getCustomer_name());
									log.setVehicle_id(access_card_history.getVehicle_id());
									log.setVehicle_no(access_card_history.getVehicle_no());
									log.setMain_id(access_card_history.getMain_id());
									log.setCard_id(access_card_history.getCard_id());
								}else{
									params = new HashMap<String, Object>();
									params.put("card_no", excel.getValue(0, rows).trim());
									//查询通行卡信息
									HZ_Access_Card access_card = hz_access_cardService.get(params);
									if(access_card ==  null){
										params = new HashMap<String, Object>();
										params.put("card_no", card_no);
										params.put("a_sql", " start_time <='"+excel.getValue(2, rows)+"' and end_time >='"+excel.getValue(2, rows)+"' ");
										HZ_Access_Card_History card_history = hz_access_card_historyService.get(params);
										if(card_history != null){
											access_card = new HZ_Access_Card();
											access_card.setCard_no(excel.getValue(0, rows).trim());
											access_card.setCustomer_id(card_history.getCustomer_id());
											access_card.setVehicle_id(card_history.getVehicle_id());
											access_card.setVehicle_no(card_history.getVehicle_no());
											access_card.setMain_card_id(card_history.getMain_id());
											
										}
									}
									if(access_card !=  null){
										params = new HashMap<String, Object>();
										params.put("column", "name");
										params.put("id", access_card.getCustomer_id());
										//获取客户名称
										Object obj_name = hz_customerService.getScale(params);
										//设置消费数据信息
										log.setCustomer_id(access_card.getCustomer_id());
										log.setCustomer_name(obj_name.toString());
										log.setVehicle_id(access_card.getVehicle_id());
										log.setVehicle_no(access_card.getVehicle_no());
										log.setMain_id(access_card.getMain_card_id());
										log.setCard_id(access_card.getId());
									}else{
										put_err(excel.getValue(0, rows).trim()+"在系统中不存在");
									}
								}
								
								/**设置挂靠客户名字*/
								HZ_Customer_Anchored_History history = hz_customer_anchored_historyService.getAnchoredByTime(log.getCustomer_id(), log.getOutlet_transit_time());
								if(history != null){
									log.setAnchor_customer_id(history.getTop_customer_id());
									log.setAnchor_customer_name(history.getTop_customer_name());
								}else{
									/**没有挂靠关系则默认为通行卡的拥有客户**/
									log.setAnchor_customer_id(log.getCustomer_id());
									log.setAnchor_customer_name(log.getCustomer_name());
								}
								//设置记录时间
								log.setImport_time(import_time);
								list_temp_consumption.add(log);
								if(list_temp_consumption.size()>500){
									//分批次保存，每五百条保存一次
									hz_access_card_consumption_logService.saveList(list_temp_consumption);
									list_temp_consumption = new ArrayList<HZ_Access_Card_Consumption_Log>();
								}
							}//end 卡号不为空
						}else{
							put_err("在第" + row + "行，该消费数据时间点：" + d_time + "已经处理过了，不进行重复导入");
						}
					}
				}//end for
				//批量保存
				if(list_temp_consumption.size()!=0){
					hz_access_card_consumption_logService.saveList(list_temp_consumption);
				}
				put_msg("导入数据成功，已经完成所有导入数据！");
				HZ_Access_Card_Consumption_LogUtil.step = "6";
		    	HZ_Access_Card_Consumption_LogUtil.r_msg = "完成所有操作";
		    	HZ_Access_Card_Consumption_LogUtil.t_msg = HZ_Access_Card_Consumption_LogUtil.t_msg + "数据导入成功！<br>";
			} catch (Exception e) {
				if (row == null) {
					put_err("在导入中出现严重错误!数据无法导入!");
				} else {
					put_err("在第" + row + "行中出现错误,导致数据导入失败,请检查数据是否有误!");
				}
				put_err(e.getLocalizedMessage());
				e.printStackTrace();
			}finally{
				//去除重复记录
				hz_access_card_consumption_logService.deleteRepeat(); // 删除数据
			}
	}
	
	/**控制台输入错误信息
	 * @author linjian 
	 * @create_date 2013-5-3 下午4:11:52
	 * @param msg
	 */
	public static void put_msg(String msg){
		SimpleDateFormat sf = new SimpleDateFormat("时间：HH:mm:ss");
		con_info = "<span>" + sf.format(new Date()) + "&nbsp;&nbsp;&nbsp;" + msg + "</span><br>" + con_info;
	}
	
	/**清空控制台信息
	 * @author linjian 
	 * @create_date 2013-5-3 下午4:12:45
	 */
	public static void clear_msg(){
		con_info = "";
	}
	
	/**控制台输入错误信息
	 * @author linjian 
	 * @create_date 2013-5-3 下午4:11:41
	 * @param msg
	 */
	public static void put_err(String msg){
		con_info = "<span style=\"color:red;\">" + msg + "</span><br>" + con_info;
	}
	
	//删除指定文件夹下所有文件
	//param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
       boolean flag = false;
       File file = new File(path);
       if (!file.exists()) {
         return flag;
       }
       if (!file.isDirectory()) {
         return flag;
       }
       String[] tempList = file.list();
       File temp = null;
       for (int i = 0; i < tempList.length; i++) {
          if (path.endsWith(File.separator)) {
             temp = new File(path + tempList[i]);
          } else {
              temp = new File(path + File.separator + tempList[i]);
          }
          if (temp.isFile()) {
             temp.delete();
          }
          if (temp.isDirectory()) {
             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
             delFolder(path + "/" + tempList[i]);//再删除空文件夹
             flag = true;
          }
       }
       return flag;
     }
	   
	 /**删除文件夹下的所有文件夹，保留第一级文件夹下的文件，其他是文件夹全部删除
	 * @author linjian
	 * @create_date 2013-12-25 下午4:01:39
	 * @param path 第一级文件夹路径
	 * @return 是否删除
	 */
	public static boolean delAllDrectory(String path) {
       boolean flag = false;
       File file = new File(path);
       if (!file.exists()) {
         return flag;
       }
       if (!file.isDirectory()) {
         return flag;
       }
       String[] tempList = file.list();
       File temp = null;
       for (int i = 0; i < tempList.length; i++) {
          if (path.endsWith(File.separator)) {
             temp = new File(path + tempList[i]);
          } else {
              temp = new File(path + File.separator + tempList[i]);
          }
          //只有是文件夹才做删除操作
          if (temp.isDirectory()) {
             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
             delFolder(path + "/" + tempList[i]);//再删除空文件夹
             flag = true;
          }
       }
       return flag;
     }
	   
	 //删除文件夹
	 //param folderPath 文件夹完整绝对路径
	 public static void delFolder(String folderPath) {
	      try {
	         delAllFile(folderPath); //删除完里面所有内容
	         String filePath = folderPath;
	         filePath = filePath.toString();
	         java.io.File myFilePath = new java.io.File(filePath);
	         myFilePath.delete(); //删除空文件夹
	      } catch (Exception e) {
	        e.printStackTrace(); 
	      }
	 }
	      
	public List<Object> getList_cardNo() {
		return list_cardNo;
	}

	public void setList_cardNo(List<Object> list_cardNo) {
		this.list_cardNo = list_cardNo;
	}

	public String getData_root() {
		return data_root;
	}

	public void setData_root(String data_root) {
		this.data_root = data_root;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getMain_id() {
		return main_id;
	}
	public void setMain_id(Integer main_id) {
		this.main_id = main_id;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
}
