package com.hczd.download.card.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hczd.download.base.module.HZ_Dict_Data;
import com.hczd.download.base.service.IHZ_Dict_DataService;
import com.hczd.download.base.service.IHZ_Dict_TypeService;
import com.hczd.download.card.module.HZ_Gas_Card;
import com.hczd.download.card.module.HZ_Gas_Card_Consumption_Log;
import com.hczd.download.card.module.HZ_Gas_Card_History;
import com.hczd.download.card.module.HZ_Gas_Station;
import com.hczd.download.card.service.IHZ_Gas_CardService;
import com.hczd.download.card.service.IHZ_Gas_Card_Consumption_LogService;
import com.hczd.download.card.service.IHZ_Gas_Card_HistoryService;
import com.hczd.download.card.service.IHZ_Gas_StationService;
import com.hczd.download.common.util.HZ_AccountUtil;
import com.hczd.download.common.util.HZ_DateFormateUtil;
import com.hczd.download.common.util.HZ_Date_CalacUtil;
import com.hczd.download.customer.module.HZ_Customer_Anchored_History;
import com.hczd.download.customer.service.IHZ_CustomerService;
import com.hczd.download.customer.service.IHZ_Customer_Anchored_HistoryService;

/**
 * 中石油下载工具类
 * @author linyb
 * @version 1.0 
 * @create_date 2014-7-14下午5:26:40
 */
@Component
@Scope("singleton")
public class HZ_PetroUtil {
	@Autowired
	private IHZ_Gas_Card_Consumption_LogService hz_gas_card_consumption_logService;
	@Autowired
	private IHZ_Gas_CardService hz_gas_cardService;
	@Autowired
	private IHZ_Gas_Card_HistoryService hz_gas_card_historyService;
	@Autowired
	private IHZ_Gas_StationService hz_gas_stationService;
	@Autowired
	private IHZ_Dict_DataService hz_dict_dataService;
	@Autowired
	private IHZ_Dict_TypeService hz_dict_typeService;
	@Autowired
	private IHZ_CustomerService  hz_customerService;
	@Autowired
	private IHZ_Customer_Anchored_HistoryService hz_customer_anchored_historyService;
	
	/**下载状态*/
	private Map<String,Integer> mainCardState = new HashMap<String, Integer>();
	/**实时信息*/
	private Map<String,StringBuffer> console_msg = new HashMap<String, StringBuffer>();
	/**下载时间*/
	private Map<String,String> date_msg = new HashMap<String, String>();
	
	/**
	 * 下载中石油消费数据
	 * @author linyb
	 * @create_date 2014-7-15下午3:11:23
	 * @param main_card
	 * @param code
	 * @param month
	 * @param card_no
	 * @return
	 */
	public Map<String,Object> download_oper(String root,String main_card,String code,String month,String card_no){
		Map<String,Object> download_msg = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		/*
		 * 1、登录   失败返回失败提示，成功返回cookie
		 */
		synchronized (main_card) {
			String current_mill = "_"+System.currentTimeMillis();
			mainCardState.put(main_card, HZ_Sinopec_Util.RUNNING);
			date_msg.put(main_card+current_mill, "当前正在下载："+month+"的"+(StringUtils.isBlank(card_no)?"":card_no)+"的数据");
			put_msg(main_card, "登录中石油系统中..", sb);
			Map<String,Object> map = login(main_card, code, month, card_no);  
			if("login_error".equals(map.get("msg"))){
				download_msg.put("d_msg", "login_error");
				mainCardState.put(main_card, HZ_Sinopec_Util.FAIL);
				put_err(main_card, "登录中石油系统失败..", sb);
				date_msg.put(main_card+current_mill, "上一次下载："+month+"的"+(StringUtils.isBlank(card_no)?"":card_no)+"的数据失败");
				return download_msg;
			}else{
				Cookie[] cs = (Cookie[]) map.get("cookie");
				mainCardState.put(main_card, HZ_Sinopec_Util.NORMAL);
				/*
				 * 2、下载  失败返回失败提示，成功返回文件路径
				 */
				put_msg(main_card, "开始下载..", sb);
				String msg = download(root,cs, month, card_no,main_card,sb,current_mill);  
				if("download_error".equals(msg)){
					download_msg.put("d_msg", "download_error");
					mainCardState.put(main_card, HZ_Sinopec_Util.FAIL);
					date_msg.put(main_card+current_mill, "上一次下载："+month+"的"+(StringUtils.isBlank(card_no)?"":card_no)+"的数据失败");
					return download_msg;
				}else{
					/*
					 * 3、上传消费数据
					 */
					try {
						put_msg(main_card, "开始上传数据..", sb);
						uploadConsumption(msg, main_card, month,sb);
						mainCardState.put(main_card, HZ_Sinopec_Util.NORMAL);
						put_msg(main_card, "数据上传完成..", sb);
						put_msg(main_card, "操作完成！", sb);
						date_msg.put(main_card+current_mill, "上一次下载："+month+"的"+(StringUtils.isBlank(card_no)?"":card_no)+"的数据");
					} catch (Exception e) {
						mainCardState.put(main_card, HZ_Sinopec_Util.FAIL);
						date_msg.put(main_card+current_mill, "上一次下载："+month+"的"+(StringUtils.isBlank(card_no)?"":card_no)+"的数据失败");
						put_err(main_card, "上传数据出错..", sb);
						e.printStackTrace();
					}
				}
			}
		}
		return download_msg;
	}
	
	/**
	 * 模拟登录中石油
	 * @author linyb
	 * @create_date 2014-7-15上午午11:08:27
	 * @param main_card
	 * @param code
	 * @param month
	 * @param card_no
	 * @return
	 */
	public Map<String,Object> login(String main_card,String code,String month,String card_no){
		Map<String,Object> login_m = new HashMap<String, Object>();
		String txtpassword = "";
		String txtcardnum = "";
		if("9130030000148971".equals(main_card)){
			txtcardnum = "fjhczd";
			txtpassword = "ycar365365";
		}else if("9130030000261767".equals(main_card)){
			txtcardnum = "HCZD9130030000261767";
			txtpassword = "ycar365365";
		}else{
			login_m.put("msg", "login_error");
			return login_m;
		} 
		HttpClient client = new HttpClient();
		String login_url ="http://www.95504.net/DefaultLogin.aspx";
		PostMethod post = null;
		GetMethod get = null;
		Cookie[] cs = null;
		try {
			client.getState().addCookies(getCookie());
			post = new PostMethod(login_url);
			NameValuePair[] data = {
					new NameValuePair("txtcardnum", txtcardnum),
					new NameValuePair("txtpassword", txtpassword),
					new NameValuePair("txtcheckcode", code),
					new NameValuePair("indexlogin", "true")
				};
			post.setRequestBody(data); // 参数set
			int statusCode = client.executeMethod(post);  //执行请求
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || /*301*/ statusCode == HttpStatus.SC_MOVED_TEMPORARILY /*302*/) {
				Header locationHeader = post.getResponseHeader("location");	 // 从头中取出转向的地址
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					get= new GetMethod("http://www.95504.net"+location);
					
					int statusCode_302 = client.executeMethod(get);
					//System.out.println(get.getResponseBodyAsString());
					if(statusCode_302 ==HttpStatus.SC_OK  /*200*/){
						cs = client.getState().getCookies(); // 获取cookie
						login_m.put("cookie", cs);
						login_m.put("msg", "login_success");
						//下载操作
						//download(cs, month, card_no);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			login_m.put("msg", "login_error");
		}
		return login_m;
	}
	
	/**
	 * 获取登录验证码图片
	 * @author linyb
	 * @create_date 2014-7-15上午11:00:39
	 */
	public static InputStream getLoginYZMImage()throws IOException{
		HttpClient httpClient = new HttpClient();
		httpClient.getHostConfiguration().setHost("www.95504.net", 80, "http");
        //设置表单数据
        GetMethod method = new GetMethod("/UserControl/Image.aspx?o="+Math.random()); 
        try {
			httpClient.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        setCooike(httpClient.getState().getCookies());
        return method.getResponseBodyAsStream();
	}

	/**
	 * 将cookie保存在session中
	 * @author linyb
	 * @create_date 2014-7-15上午11:00:47
	 * @param cookies
	 */
	private static void setCooike(Cookie[] cookies) {
		//cookie = cookies;
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
        HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();  
        request.getSession().setAttribute("PETRO_COOKIES", cookies);
	}
	
	/**
	 * 从session中获取cookie
	 * @author linyb
	 * @create_date 2014-7-15上午11:00:51
	 * @return
	 */
	private static Cookie[] getCookie() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
        HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();  
        Object obj = request.getSession().getAttribute("PETRO_COOKIES");
        if(obj!=null){
        	return (Cookie[])obj;
        }
        return null;
	}
	
	/**
	 * 下载
	 * @author linyb
	 * @create_date 2014-7-15下午2:19:30
	 * @param cs
	 * @param month
	 * @param card_no
	 * @return
	 */
	public String download(String root,Cookie[] cs,String month ,String card_no,String main_card,StringBuffer sb,String current_mill){
		
		mainCardState.put(main_card, HZ_Sinopec_Util.RUNNING);
		HttpClient client = new HttpClient();
		client.getState().addCookies(cs);
		client.getHostConfiguration().setHost("www.95504.net", 80, "http");
		GetMethod get = null;
		try {
			File f = new File(root);
			if(!f.exists()){
				f.mkdir();
			}
			String download_path = "/User/Consumer_Sale_Operate_Report.aspx?Data_DanWei="+month+"-01&OWNER_ID=&Card_No="+card_no+"&currPage=1&date="+Math.random()+"&date1="+Math.random();
			put_msg(main_card, "下载中..", sb);
			get =new GetMethod(download_path);  
			client.executeMethod(get);
			InputStream in = get.getResponseBodyAsStream(); 
			String path = root+main_card+current_mill+".xls";
            FileOutputStream out = new FileOutputStream(new File(path));  
            byte[] b = new byte[1024];  
            int len = 0;  
            while((len=in.read(b))!= -1){  
               out.write(b,0,len); 
               put_msg(main_card, "下载输出中..", sb);
            }  
            in.close();  
            out.close();  
            put_msg(main_card, "下载完成..", sb);
            return path;
		} catch (Exception e) {
			e.printStackTrace();
			mainCardState.put(main_card, HZ_Sinopec_Util.FAIL);
			put_err(main_card, "下载出错..", sb);
			return "download_error";
		}
	}

	/**
	 * 上传消费数据到数据库
	 * @author linyb
	 * @create_date 2014-7-15下午3:45:43
	 */
	public void uploadConsumption(String path,String main_card,String month,StringBuffer sb) {

		//缓存传上来的消费数据
		List<HZ_Gas_Card_Consumption_Log> list_temp_consumption = new ArrayList<HZ_Gas_Card_Consumption_Log>();
		Integer row = null; //用来标记第几行出现异常
		try {
			HZ_Gas_Card_Excel_Util excel = new HZ_Gas_Card_Excel_Util();
			excel.getWorkBook(path);
			excel.getSheet(0);
			
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Object> result = new HashMap<String, Object>();
			//第二步：获取最近两个月内主卡的消费数据唯一码有时间和卡号组成
			String t_month = HZ_Date_CalacUtil.getDateStamp(HZ_Date_CalacUtil.getCurrentTime(), HZ_DateFormateUtil.AREA_MONTH, -2, HZ_DateFormateUtil.FORMATE_TIME);
			Set<String> set_unique_var = hz_gas_card_consumption_logService.getUnique_Var(t_month, main_card);
			//第三步：遍历excel的消费数，设置导入时间
			String import_time = HZ_Date_CalacUtil.getCurrentTime();
			String non_card_no = "";
			int rs =  excel.getRows();
			for (int rows = 0; rows < rs; rows++) {
				row = rows;
				if(rows == 0){
					String trading_no = excel.getValue(0, 0);
					String card_no = excel.getValue(1, 0);
					String money = excel.getValue(5, 0);
					String date = excel.getValue(9, 0);
					String addr = excel.getValue(10, 0);
					String model = excel.getValue(11, 0);
					String count = excel.getValue(13, 0);
					
					if(StringUtils.isNotBlank(card_no)&&card_no.equals("卡号")
							&&StringUtils.isNotBlank(trading_no)&&trading_no.equals("交易流水号")
							&&StringUtils.isNotBlank(date)&&date.equals("交易时间")
							&&StringUtils.isNotBlank(addr)&&addr.equals("交易地点")
							&&StringUtils.isNotBlank(count)&&count.equals("油量")
							&&StringUtils.isNotBlank(model)&&model.equals("商品类别")
							&&StringUtils.isNotBlank(money)&&money.equals("金额（元）")){
					}else{
						put_err(main_card,"数据导入失败,格式错误,请按照原本下载的格式导入",sb);
						break;
					}
				}else if(StringUtils.isNotBlank(excel.getValue(1, rows))){
					if(rows != 0){
						put_msg(main_card,"正在导入第" + rows + "条数据....",sb);
					}
					//根据时间判断是否数据库中之前已经存在，如果已经存在则继续下一条
					String d_time = excel.getValue(9, rows);
					/**2014-01-07 linjian 修改验证唯一性**/
					String t_card_no = excel.getValue(1, rows).trim();
					String t_charge_time = excel.getValue(9, rows).trim();
					//判断是否重复添加
					if(!set_unique_var.contains(t_card_no + t_charge_time)){ //  && HZ_CalendarUtil.compare_time(t_month, t_charge_time) < 0
						//进行excel数据解析
						String card_no = excel.getValue(1, rows);
						if (StringUtils.isNotBlank(card_no)) {
							Map<String, Object> cardparams = new HashMap<String, Object>();
							cardparams.put("card_no_like", card_no);
							cardparams.put("column", "card_no");
							Object obj = hz_gas_cardService.getScale(cardparams);
							//加油卡历史记录
							HZ_Gas_Card_History gas_card_history = null;

							// 林鉴 2013-06-17 紧急 导入消费数据时可能原来的卡已经退卡了,或者给另一个客户使用时,这时候
							if (obj != null && StringUtils.isNotBlank(obj.toString().trim())) {
								//linjian 2013-06-17
								//获取历史加油卡归属
								gas_card_history = hz_gas_card_historyService.getHistoryByCardNoWithTime(card_no, excel.getValue(9, rows));
								//查询原先加油站信息
								Map<String, Object> staparams = new HashMap<String, Object>();
								staparams.put("name", excel.getValue(10, rows).trim());
								HZ_Gas_Station sta = hz_gas_stationService.get(staparams);
								//判断加油站是否存在
								if (sta == null) {
									sta = new HZ_Gas_Station();
									sta.setRemark(t_card_no.contains("9130030000")?"中石油":"中石化");
									sta.setName(excel.getValue(10, rows));
									sta.setStatus(HZ_Gas_Station.STATUS_DISABLE);
									sta.setAddr(excel.getValue(10, rows));
									hz_gas_stationService.save(sta);
								}

								//解析油品
								String oiltype = excel.getValue(11, rows);
								if(oiltype.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$") || (oiltype.contains("0号")||oiltype.contains("0#"))&&oiltype.contains("柴油")){
									oiltype = "0#柴油";
								}else if((oiltype.contains("-30号")||oiltype.contains("-30#"))&&oiltype.contains("柴油")){
									oiltype = "-30#柴油";
								}else if((oiltype.contains("97号")||oiltype.contains("97#"))&&oiltype.contains("汽油")){
									oiltype = "97#汽油";
								}else if((oiltype.contains("93号")||oiltype.contains("93#"))&&oiltype.contains("汽油")){
									oiltype = "93#汽油";
								}
								//查询油品
								Map<String, Object> oilparams = new HashMap<String, Object>();
								oilparams.put("name_like", oiltype);
								List<HZ_Dict_Data> list_ddata = hz_dict_dataService.list(oilparams);
								HZ_Dict_Data oil = null;
								if(list_ddata != null && list_ddata.size() > 0){
									oil = list_ddata.get(0);
								}
								if (oil == null) {
									oil = new HZ_Dict_Data();
									oil.setName(excel.getValue(11, 0));
									Map<String, Object> t_params = new HashMap<String, Object>();
									t_params.put("column", "id");
									t_params.put("extension", HZ_Dict_Data.DICT_OIL_TYPE);
									oil.setDict_type_id(Integer.parseInt(hz_dict_typeService.getScale(t_params).toString()));
									hz_dict_dataService.save(oil);
								}
								//新建一个消费记录
								HZ_Gas_Card_Consumption_Log log = new HZ_Gas_Card_Consumption_Log();
								//设置消费记录的地址
								log.setAddr(excel.getValue(10, rows));
								//设置加油量
								log.setAmount(HZ_AccountUtil.sum(excel.getValue(13, rows), "0", "#.##"));
								//设置加油卡卡号
								log.setCard_no(excel.getValue(1, rows));
								//设置消费时间
								log.setCharge_time(excel.getValue(9, rows).replace("/", "-"));
								log.setCurrent_balance(HZ_AccountUtil.sum(excel.getValue(6, rows), "0", "#.##"));
								log.setMoney_num(HZ_AccountUtil.sum(excel.getValue(5, rows), "0", "#.##"));
								log.setOil(oil.getId());
								//设置加油卡信息，用于后面报表更新 linhui
								//如果以前存过
								if(result.get(excel.getValue(1, rows)+"_amount")!=null){
									result.put(excel.getValue(1, rows)+"_amount", HZ_AccountUtil.sum(result.get(excel.getValue(1, rows)+"_amount").toString(), excel.getValue(13, rows), "#.##"));
									result.put("isNull", "true");
								}else{
									result.put(excel.getValue(1, rows)+"_amount", excel.getValue(13, rows));
									result.put("isNull", "true");
								}
								
								if(result.get(excel.getValue(1, rows)+"_money")!=null){
									result.put(excel.getValue(1, rows)+"_money", HZ_AccountUtil.sum(result.get(excel.getValue(1, rows)+"_money").toString(), excel.getValue(5, rows), "#.##"));
									result.put("isNull", "true");
								}else{
									result.put(excel.getValue(1, rows)+"_money", excel.getValue(5, rows));
									result.put("isNull", "true");
								}
								
								//设置油品名称
								log.setOil_name(oil.getName());
								log.setStation_id(sta.getId());
								//如果有该卡的历史归属记录
								if(gas_card_history != null){
									log.setCustomer_id(gas_card_history.getCustomer_id());
									log.setCustomer_name(gas_card_history.getCustomer_name());
									log.setVehicle_id(gas_card_history.getVehicle_id());
									log.setVehicle_no(gas_card_history.getVehicle_no());
								}else{
									params = new HashMap<String, Object>();
									params.put("card_no", excel.getValue(1, rows).trim());
									//查询加油卡信息
									HZ_Gas_Card gas_card = hz_gas_cardService.get(params);
									if(gas_card ==  null){
										String tt_card_no = excel.getValue(1, rows).trim();
										String tt_time = excel.getValue(9, rows).replace("/", "-");
										HZ_Gas_Card_History card_history = hz_gas_cardService.getHistoryByCardNoWithTime(tt_card_no, tt_time);
										if(card_history != null){
											gas_card = new HZ_Gas_Card();
											gas_card.setCard_no(excel.getValue(1, rows).trim());
											gas_card.setCustomer_id(card_history.getCustomer_id());
											gas_card.setVehicle_id(card_history.getVehicle_id());
											gas_card.setVehicle_no(card_history.getVehicle_no());
										}
									}
									else{
										params = new HashMap<String, Object>();
										params.put("column", "name");
										params.put("id", gas_card.getCustomer_id());
										//获取客户名称
										Object obj_name = hz_customerService.getScale(params);
										//设置消费数据信息
										log.setCustomer_id(gas_card.getCustomer_id());
										log.setCustomer_name(obj_name.toString());
										log.setVehicle_id(gas_card.getVehicle_id());
										log.setVehicle_no(gas_card.getVehicle_no());
									}
								}
								
								/**设置挂靠客户名字*/
								HZ_Customer_Anchored_History history = hz_customer_anchored_historyService.getAnchoredByTime(log.getCustomer_id(), log.getCharge_time());
								if(history != null){
									log.setAnchor_customer_id(history.getTop_customer_id());
									log.setAnchor_customer_name(history.getTop_customer_name());
								}else{
									/**没有挂靠关系则默认为加油卡的拥有客户**/
									log.setAnchor_customer_id(log.getCustomer_id());
									log.setAnchor_customer_name(log.getCustomer_name());
								}
								//设置记录时间
								log.setImport_time(import_time);
								list_temp_consumption.add(log);
								if(list_temp_consumption.size()>500){
									//分批次保存，每五百条保存一次
									hz_gas_card_consumption_logService.saveList(list_temp_consumption);
									list_temp_consumption = new ArrayList<HZ_Gas_Card_Consumption_Log>();
								}
							}
							// 加油卡在系统中不存在进行提示
							else{
								if(!non_card_no.contains(card_no)){
									if(StringUtils.isBlank(non_card_no)){
										non_card_no = card_no;
									}else{
										non_card_no = non_card_no + "," + card_no;
									};
								}
							}
						}//end 卡号不为空
					}else{
						//不做任何操作，继续下一条消费数据处理
						put_err(main_card,"在第" + row + "行，该消费数据时间点：" + d_time + "已经处理过了，不进行重复导入",sb);
					}//判断是否消费数据两天衔接内
				}//读取excel记录
			}//end for
			//批量保存
			if(list_temp_consumption.size()!=0){
				hz_gas_card_consumption_logService.saveList(list_temp_consumption);
			}
			
			if(StringUtils.isNotBlank(non_card_no)){
				put_err(main_card,"加油卡："+non_card_no+"在系统中不存在",sb);
			}
			//put_msg("导入数据完成！");
			//HZ_Gas_Card_Consumption_LogPetroChinaUtil.step = "6";
	    	//HZ_Gas_Card_Consumption_LogPetroChinaUtil.r_msg = "完成所有操作";
	    	//HZ_Gas_Card_Consumption_LogPetroChinaUtil.t_msg = HZ_Gas_Card_Consumption_LogPetroChinaUtil.t_msg + "数据导入成功！<br>";
		} catch (Exception e) {
			if (row == null) {
				put_err(main_card,"在导入中出现严重错误!数据无法导入!",sb);
			} else {
				put_err(main_card,"在第" + row + "行中出现错误,导致数据导入失败,请检查数据是否有误!",sb);
			}
			//put_err(e.getLocalizedMessage());
			e.printStackTrace();
		}finally{
			mainCardState.put(main_card, HZ_Sinopec_Util.DELETE_REPEAT);
			//去除重复记录
			put_msg(main_card,"正在去除重复数据,请稍候...",sb);
			hz_gas_card_consumption_logService.deleteRepeat(month + "-01 00:00:00", month + "-31 23:59:59"); // 删除数据
			
		}
	}

	/**
	 * 打印输出成功信息
	 * @author linyb
	 * @create_date 2014-7-16下午3:27:28
	 */
	private void put_msg(String cardNo,String msg ,StringBuffer sb) {
		sb.insert(0, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+":  "+msg+"<br/>");
		console_msg.put(cardNo, sb);
	}
	/**
	 * 打印输出错误信息
	 * @author linyb
	 * @create_date 2014-7-16下午3:27:48
	 */
	private void put_err(String cardNo,String msg,StringBuffer sb) {
		sb.insert(0,"<span style='color:red;'>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+":  "+msg+"</span><br/>");
		console_msg.put(cardNo, sb);
	}
	public Map<String,StringBuffer> print_msg(){
		return console_msg;
	}
	/**
	 * 状态
	 * @author linyb
	 * @create_date 2014-7-16下午3:41:08
	 */
	public Integer getMainCardState(String mainCard){
		Integer state =  mainCardState.get(mainCard);
		if(state == null){
			state =HZ_Sinopec_Util.NORMAL;
		}
		return state;
	}
	/**
	 * @author linyb
	 */
	public String getDownLoadDate(String mainCard){
		String msg =  date_msg.get(mainCard);
		if(msg==null){
			msg ="当前无下载信息";
		}
		return msg;
	}
	public Map<String,String> getDateMsg(){
		return date_msg;
	}
}
