/**
 * 
 */
package com.hczd.download.card.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
import com.hczd.download.common.httpclient.HZ_HttpClient;
import com.hczd.download.common.util.HZ_AccountUtil;
import com.hczd.download.common.util.HZ_DateFormateUtil;
import com.hczd.download.common.util.HZ_Date_CalacUtil;
import com.hczd.download.customer.module.HZ_Customer_Anchored_History;
import com.hczd.download.customer.service.IHZ_CustomerService;
import com.hczd.download.customer.service.IHZ_Customer_Anchored_HistoryService;

/**
 * @author xiaojin
 * 中石化消费数据下载
 * 单例模式
 */
@Component
@Scope("singleton")
public class HZ_Sinopec_Util{
	
	public static final int NORMAL = 0; //正常状态
	public static final int RUNNING = 1; //下载中
	public static final int MERGE = 2; //合并文件中
	public static final int DELETE_REPEAT = 3; //去除重复数据中
	
	/**
	 * @author xiaojin
	 * @create_date 2014-5-4下午2:28:04
	 */
	
	@Autowired
	private IHZ_Gas_CardService hz_gas_cardService;
	@Autowired
	IHZ_Gas_Card_Consumption_LogService hz_gas_card_consumption_logService;
	@Autowired
	IHZ_Gas_Card_HistoryService hz_gas_card_historyService;
	@Autowired
	IHZ_Dict_DataService hz_dict_dataService;
	@Autowired
	IHZ_Gas_StationService hz_gas_stationService;
	@Autowired
	IHZ_Dict_TypeService hz_dict_typeService;
	@Autowired
	IHZ_CustomerService hz_customerService;
	@Autowired
	IHZ_Customer_Anchored_HistoryService hz_customer_anchored_historyService;
	
	private Map<String,Integer> mainCardState = new HashMap<String, Integer>();
	private Map<String,StringBuffer> console_msg = new HashMap<String, StringBuffer>();
	
	
	/*public static void main(String[] args) throws IOException, InterruptedException {
		ClassPathXmlApplicationContext resource = new ClassPathXmlApplicationContext(
				new String[]{
						"classpath:config/spring/app-db.xml",
						"classpath:config/spring/app-tx.xml"
					}
			);
		BeanFactory factory = resource; 
		HZ_Sinopec_Util h = factory.getBean(HZ_Sinopec_Util.class);
		
		String mainCard = "1000113500002425882";
		String pwd = "851019";
		String startDate = "2014-01-29";
		String endDate = "2014-04-29";
		String root = "E:/card_list/";
		h.main(mainCard, pwd, startDate, endDate, root);
	}*/
	
	public void main(String mainCard,String pwd,String startDate,String endDate,String root) throws IOException{
		StringBuffer sb = new StringBuffer();
		Integer state = mainCardState.get(mainCard);
		if(state==null){
			state=NORMAL;
		}
		put_msg(mainCard ,"检测主卡运行状态",sb);
		if(state == NORMAL){
		synchronized (mainCard) {
				mainCardState.put(mainCard,RUNNING);
				
				put_msg(mainCard ,"创建远程请求",sb);
				HZ_HttpClient httpClient = new HZ_HttpClient("http://www.saclub.com.cn/");
				put_msg(mainCard ,"获取验证码图片",sb);
				
				getImage(httpClient);
				
				put_msg(mainCard ,"自动登录中",sb);
				login(httpClient,mainCard,pwd,sb);
				
				put_msg(mainCard ,"获取中石化客户编号",sb);
				String customerId = getCustomerId(httpClient);
				
				put_msg(mainCard ,"获取子卡信息",sb);
				List<Object> list_cardNo = hz_gas_cardService.getConsumptionCardNoByMainCard(mainCard, startDate + " 00:00:00", endDate + " 23:59:59");
				
				File dir =new File(root);
				if(!dir.exists()){
					dir.mkdir();
				}
				File cardDir = new File(root + mainCard + "/");
				if(!cardDir.exists()){
					cardDir.mkdir();
				}
				
				put_msg(mainCard ,"开始下载消费数据",sb);
				Cookie[] cookies = httpClient.getCookie();
				for (int i = 0; i < list_cardNo.size(); i++) {
					File file =new File(root + mainCard + "/"+ list_cardNo.get(i).toString() +".xls");
					file.createNewFile();
					HZ_HttpClient tempHttpClient = new HZ_HttpClient("http://www.saclub.com.cn/",cookies);
					getConsumptionExcel(mainCard ,tempHttpClient, file,customerId, list_cardNo.get(i).toString(), startDate, endDate,sb);
				}
				
				put_msg(mainCard ,"所有文件下载完成",sb);
				
				put_msg(mainCard ,"开始合并文件...",sb);
				mainCardState.put(mainCard,MERGE);
				mergerExcelFile(mainCard,list_cardNo, root + mainCard,sb);
				put_msg(mainCard ,"合并完成",sb);
				
				put_msg(mainCard ,"开始保存数据...",sb);
				uploadConsumption(root, mainCard, startDate, endDate,sb); 
				put_msg(mainCard ,"保存成功",sb);
				mainCardState.put(mainCard, NORMAL);
		}
		}else if(state == RUNNING){
			put_err("当前主卡正在进行下载...");
		}
	}
	
	/**
	 * @author xiaojin
	 * @create_date 2014-4-30上午10:00:36
	 */
	private void put_msg(String cardNo,String msg ,StringBuffer sb) {
		sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+":  "+msg+"<br/>");
		console_msg.put(cardNo, sb);
	}
	/**
	 * @author linyb
	 * @create_date 2014-5-6下午2:49:00
	 */
	public Map<String,StringBuffer> print_msg(){
		return console_msg;
	}
	/**
	 * @author linyb
	 * @create_date 2014-5-6下午3:45:02
	 */
	public Integer getMainCardState(String mainCard){
		Integer state =  mainCardState.get(mainCard);
		if(state == null){
			state =NORMAL;
		}
		return state;
	}
	/**
	 * @author xiaojin
	 * @create_date 2014-4-30上午11:37:04
	 */
	private void put_err(String msg) {
		System.err.println(msg);
	}

	/**
	 * @author xiaojin
	 * @throws IOException 
	 * @create_date 2014-4-29下午5:18:53
	 */
	private void uploadConsumption(String root,String userName,String startDate,String endDate,StringBuffer sb) throws IOException {
		File file =new File(root + userName +".xls");
		Workbook wb = null;
		FileInputStream inputStream = null;
		if(file.length()>0){
			inputStream=new FileInputStream(file);
			try{
				wb = new HSSFWorkbook(inputStream);
			}catch (Exception e) {
				wb = new XSSFWorkbook(inputStream);
			}
		}else{
			return ;
		}
		Sheet sheet = wb.getSheetAt(0);
		Map<String, Object> result = new HashMap<String, Object>();
		List<HZ_Gas_Card_Consumption_Log> list_temp_consumption = new ArrayList<HZ_Gas_Card_Consumption_Log>();
		//获取前两天内该主卡的消费数据并且加载在内存中，提高消费数据导入性能
		//第一步：获取消费数据中时间最大值
		Map<String, Object> params = new HashMap<String, Object>();
		//第二步：获取最近两个月内主卡的消费数据唯一码有时间和卡号组成
		String t_month = HZ_Date_CalacUtil.getDateStamp(HZ_Date_CalacUtil.getCurrentTime(), HZ_DateFormateUtil.AREA_MONTH, -2, HZ_DateFormateUtil.FORMATE_TIME);
		Set<String> set_unique_var = hz_gas_card_consumption_logService.getUnique_Var(t_month, userName);
		
		//第三步：遍历excel的消费数，设置导入时间
		String import_time = HZ_Date_CalacUtil.getCurrentTime();
		
		
		int rowNum = sheet.getPhysicalNumberOfRows();
		
		try {
			for (int i = 1; i < rowNum; i++) {
				Row row = sheet.getRow(i);
		        String t_card_no = row.getCell(0).getStringCellValue();
	        	String t_charge_time = row.getCell(2).getStringCellValue();
	        	String addr = row.getCell(3).getStringCellValue();
	        	String amount = row.getCell(4).getStringCellValue();
	        	String oil = row.getCell(5).getStringCellValue();
	        	String t_money = row.getCell(6).getStringCellValue();
	        	String total = row.getCell(7).getStringCellValue();
	        	String chan_type = row.getCell(8).getStringCellValue();
	
				put_msg(userName ,"正在导入第" + i + "条数据。。。",sb);
				//根据时间判断是否数据库中之前已经存在，如果已经存在则继续下一条
				String d_time = row.getCell(2).getStringCellValue();
	//			if(HZ_CalendarUtil.compare_time(d_time, HZ_CalendarUtil.getDateByTime(time_pre2_day) + " 00:00:00") > 0 || set_unique_var.contains(excel.getValue(0, rows).trim() + excel.getValue(2, rows).trim())){
				/**2014-01-07 linjian 修改验证唯一性**/
				//String t_card_no = excel.getValue(0, rows).trim();
				//String t_charge_time = excel.getValue(2, rows).trim();
				/**交易类型 linjian 2014-04-03**/
				//String chan_type = excel.getValue(8, 0);
				/**总价 linjian 2014-04-03**/
				//String t_money = excel.getValue(6, 0);
	//			Map<String, Object> t_map = new HashMap<String, Object>();
	//			t_map.put("card_no", t_card_no);
	//			t_map.put("charge_time", t_charge_time);
	//			Integer t_count = hz_gas_card_consumption_logService.count(t_map);
	//			if(t_count == null || t_count <= 0){	
				//判断是否重复添加
				if(!set_unique_var.contains(t_card_no + t_charge_time) && !(StringUtils.isNotBlank(chan_type) && chan_type.trim().equals("逃卡") && StringUtils.isNotBlank(t_money) && t_money.trim().equals("0"))){ // && HZ_CalendarUtil.compare_time(t_month, t_charge_time) < 0
					//进行excel数据解析
					String card_no = row.getCell(0).getStringCellValue();
					if (StringUtils.isNotBlank(card_no)) {
						Map<String, Object> cardparams = new HashMap<String, Object>();
						cardparams.put("card_no", card_no);
						cardparams.put("column", "card_no");
						Object obj = hz_gas_cardService.getScale(cardparams);
						//加油卡历史记录
						HZ_Gas_Card_History gas_card_history = null;
	
						// 林鉴 2013-06-17 紧急 导入消费数据时可能原来的卡已经退卡了,或者给另一个客户使用时,这时候
						if (obj != null && StringUtils.isNotBlank(obj.toString().trim())) {
							//linjian 2013-06-17
							//获取历史加油卡归属
							gas_card_history = hz_gas_card_historyService.getHistoryByCardNoWithTime(card_no, t_charge_time);
						}
	
						//查询原先加油站信息
						Map<String, Object> staparams = new HashMap<String, Object>();
						staparams.put("name", addr);
						HZ_Gas_Station sta = hz_gas_stationService.get(staparams);
						//判断加油站是否存在
						if (sta == null) {
							sta = new HZ_Gas_Station();
							sta.setRemark(t_card_no.contains("100011350000")?"中石化":"中石油");
							sta.setName(addr);
							sta.setStatus(HZ_Gas_Station.STATUS_DISABLE);
							sta.setAddr(addr);
							hz_gas_stationService.save(sta);
						}
	
						//解析油品
						String oiltype = oil;
						if(oiltype.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$") || (oiltype.contains("0号")||oiltype.contains("0#"))&&oiltype.contains("柴油")){
							oiltype = "0#柴油";
						}else if((oiltype.contains("-30号")||oiltype.contains("-30#"))&&oiltype.contains("柴油")){
							oiltype = "-30#柴油";
						}else if((oiltype.contains("97号")||oiltype.contains("97#"))&&oiltype.contains("汽油")){
							oiltype = "97#汽油";
						}else if((oiltype.contains("93号")||oiltype.contains("93#"))&&oiltype.contains("汽油")){
							oiltype = "93#汽油";
						}else{
							//System.out.println(oiltype);
						}
						//查询油品
						Map<String, Object> oilparams = new HashMap<String, Object>();
						oilparams.put("name_like", oiltype);
						List<HZ_Dict_Data> list_ddata = hz_dict_dataService.list(oilparams);
						HZ_Dict_Data oilRecord = null;
						if(list_ddata != null && list_ddata.size() > 0){
							oilRecord = list_ddata.get(0);
						}
						if (oilRecord == null) {
							oilRecord = new HZ_Dict_Data();
							oilRecord.setName(oiltype);
							Map<String, Object> t_params = new HashMap<String, Object>();
							t_params.put("column", "id");
							t_params.put("extension", HZ_Dict_Data.DICT_OIL_TYPE);
							oilRecord.setDict_type_id(Integer.parseInt(hz_dict_typeService.getScale(t_params).toString()));
							hz_dict_dataService.save(oilRecord);
						}
						//新建一个消费记录
						HZ_Gas_Card_Consumption_Log log = new HZ_Gas_Card_Consumption_Log();
						//设置消费记录的地址
						log.setAddr(addr);
						//设置加油量
						log.setAmount(HZ_AccountUtil.sum(amount, "0", "#.##"));
						//设置加油卡卡号
						log.setCard_no(t_card_no);
						//设置消费时间
						log.setCharge_time(t_charge_time.replace("/", "-"));
						//待定 log.setCurrent_balance("");
						log.setMoney_num(HZ_AccountUtil.sum(t_money, "0", "#.##"));
						log.setOil(oilRecord.getId());
						
						//设置加油卡信息，用于后面报表更新 linhui
						//如果以前存过
						if(result.get(t_card_no+"_amount")!=null){
							result.put(t_card_no+"_amount", HZ_AccountUtil.sum(result.get(t_card_no+"_amount").toString(), amount, "#.##"));
							result.put("isNull", "true");
						}else{
							result.put(t_card_no+"_amount", amount);
							result.put("isNull", "true");
						}
						
						if(result.get(t_card_no+"_money")!=null){
							result.put(t_card_no+"_money", HZ_AccountUtil.sum(result.get(t_card_no+"_money").toString(), t_money, "#.##"));
							result.put("isNull", "true");
						}else{
							result.put(t_card_no+"_money", t_money);
							result.put("isNull", "true");
						}
						
						//设置油品名称
						log.setOil_name(oilRecord.getName());
						log.setStation_id(sta.getId());
						log.setTotal(Integer.parseInt(total));
						//如果有该卡的历史归属记录
						if(gas_card_history != null){
							log.setCustomer_id(gas_card_history.getCustomer_id());
							log.setCustomer_name(gas_card_history.getCustomer_name());
							log.setVehicle_id(gas_card_history.getVehicle_id());
							log.setVehicle_no(gas_card_history.getVehicle_no());
						}else{
							params = new HashMap<String, Object>();
							params.put("card_no", t_card_no);
							//查询加油卡信息
							HZ_Gas_Card gas_card = hz_gas_cardService.get(params);
							if(gas_card ==  null){
								String tt_card_no = t_card_no;
								String tt_time = t_charge_time.replace("/", "-");
								HZ_Gas_Card_History card_history = hz_gas_cardService.getHistoryByCardNoWithTime(tt_card_no, tt_time);
								if(card_history != null){
									gas_card = new HZ_Gas_Card();
									gas_card.setCard_no(t_card_no.trim());
									gas_card.setCustomer_id(card_history.getCustomer_id());
									gas_card.setVehicle_id(card_history.getVehicle_id());
									gas_card.setVehicle_no(card_history.getVehicle_no());
								}
							}
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
							//分批次保存，每五千条保存一次
							hz_gas_card_consumption_logService.saveList(list_temp_consumption);
							list_temp_consumption = new ArrayList<HZ_Gas_Card_Consumption_Log>();
						}
					}//end 卡号不为空
				}else{
					//不做任何操作，继续下一条消费数据处理
	//				put_err("在第" + row + "行，该消费数据时间点：" + d_time + "在衔接两天内已经下载过了，不进行重复导入");
					put_err("在第" + row + "行，该消费数据时间点：" + d_time + "已经处理过了，不进行重复导入");
				}//判断是否消费数据两天衔接内
			}
		} catch (Exception e) {
			/*if (row == null) {
				put_err("在导入中出现严重错误!数据无法导入!");
			} else {
				put_err("在第" + row + "行中出现错误,导致数据导入失败,请检查数据是否有误!");
			}*/
			put_err(e.getLocalizedMessage());
			e.printStackTrace();
		}finally{
			//linjian，去除重复记录
			hz_gas_card_consumption_logService.saveList(list_temp_consumption);
			put_msg(userName ,"正在去除重复数据,请稍候...",sb);
			mainCardState.put(userName,DELETE_REPEAT);
			hz_gas_card_consumption_logService.deleteRepeat(startDate + " 00:00:00", endDate + " 23:59:59"); // 删除数据
			inputStream.close();
		}
	}
	
	/**
	 * @author xiaojin
	 * @throws IOException 
	 * @create_date 2014-4-29下午4:04:13
	 */
	private void mergerExcelFile(String mainCard ,List<Object> list_cardNo,String path,StringBuffer sb) throws IOException {
		File mainFile = new File(path+".xls");
		mainFile.createNewFile();
		//FileInputStream fis=new FileInputStream(mainFile);
		Workbook mainWB = new HSSFWorkbook();
		Sheet mainsheet = mainWB.createSheet();
		Row rowHead = mainsheet.createRow(0);
		rowHead.createCell(0).setCellValue("加油卡号");
		rowHead.createCell(1).setCellValue("交易号");
		rowHead.createCell(2).setCellValue("日期");
		rowHead.createCell(3).setCellValue("地点");
		rowHead.createCell(4).setCellValue("数量(升)");
		rowHead.createCell(5).setCellValue("型号");
		rowHead.createCell(6).setCellValue("总价");
		rowHead.createCell(7).setCellValue("积分");
		rowHead.createCell(8).setCellValue("交易类型");
		
		int mianRowIndex = 1;
		
		Workbook wb=null;
		
		for (int i = 0; i < list_cardNo.size(); i++) {
			File file =new File(path + "/"+ list_cardNo.get(i).toString() +".xls");
			
			if(file.length()>0){
				FileInputStream inputStream=new FileInputStream(file);
				try{
					wb = new HSSFWorkbook(inputStream);
				}catch (Exception e) {
					wb = new XSSFWorkbook(inputStream);
				}
				Sheet sheet = wb.getSheetAt(0);
		        
				int rowNum = sheet.getPhysicalNumberOfRows();
				
				for (int j = 1; j < rowNum; j++,mianRowIndex++) {
					Row mainRow = mainsheet.createRow(mianRowIndex);
					
					Row row = sheet.getRow(j);
					int cellNum = row.getPhysicalNumberOfCells();
					for (int k = 0; k < cellNum; k++) {
						Cell cell = row.getCell(k);
						Cell mainCell = mainRow.createCell(k);
						mainCell.setCellValue(getCellValue(cell));
					}
				}
			}
		}
		
		FileOutputStream fileoutputstream = new FileOutputStream(mainFile);
		mainWB.write(fileoutputstream);
		fileoutputstream.close();
		
		//删除临时文件
		File file =new File(path + "/");
		FileUtils.deleteDirectory(file);
		put_msg(mainCard,"删除临时文件",sb);
	}
	
	
	private void getConsumptionExcel(String mainCard ,HZ_HttpClient httpClient,File file,String customerId,String cardId,String startDate,String endDate,StringBuffer  sb) throws IOException{
		Map<String, String> params = new HashMap<String, String>();
		params.put("flag", "gas");
		params.put("startTime", startDate);
		params.put("endTime", endDate);
		params.put("cardId", cardId);
		params.put("customerId", customerId);
		//"350200098712"
		
		//Map<String, String> requestHeaders = new HashMap<String, String>();
		//requestHeaders.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		//Thread.sleep(500);
		byte[] excelByte = httpClient.requestPostMethod("/downloadExcel", params, null);
		FileOutputStream fileOutputStream=new FileOutputStream(file);
		for (int i = 0; i < excelByte.length; i++) {
			fileOutputStream.write(excelByte[i]);
		}
		fileOutputStream.flush();
		fileOutputStream.close();
		
		put_msg(mainCard ,cardId+".xls:下载完成",sb);
		
		
		/*Workbook wb=null;
		ByteArrayInputStream inputStream=new ByteArrayInputStream(excelByte);
		try{
			wb = new HSSFWorkbook(inputStream);
		}catch (Exception e) {
			wb = new XSSFWorkbook(inputStream);
		}
		Sheet sheet = wb.getSheetAt(0);
        
		int rowNum = sheet.getPhysicalNumberOfRows();
		System.out.println("rowNum:" + rowNum);
		
		for (int i = 0; i < rowNum; i++) {
			Row row = sheet.getRow(i);
	        // 标题总列数
	        int colNum = row.getPhysicalNumberOfCells();
	        
	        for (int j = 0; j < colNum; j++) {
	        	Cell cell = row.getCell(j);
				System.out.print(getCellValue(cell));
				System.out.print("\t");
			}
	        System.out.println("colNum:" + colNum);
		}*/
			
			
	}
	
	public final static String DATE_OUTPUT_PATTERNS = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
	
	/**
	 * 获取客户编号
	 * @author linjian 
	 * @create_date 2013-5-2 下午6:02:15
	 * @param method
	 * @return
	 * @throws IOException 
	 */
	public static String getCustomerId(HZ_HttpClient httpClient)
			throws IOException {
		String customerId = null;
		byte[] strByte = httpClient.requestGetMethod("/searchGasPumpHist.do",
				null, null);
		String s = new String(strByte, "UTF-8");
		if (StringUtils.isNotBlank(s)) {
			if (s.contains("<input type=\"hidden\" name=\"customerId\" value=\"")) {
				int i_t = s
						.indexOf("<input type=\"hidden\" name=\"customerId\" value=\"");
				i_t = i_t
						+ "<input type=\"hidden\" name=\"customerId\" value=\""
								.length();
				s = s.substring(i_t);
				i_t = s.indexOf("\">");
				customerId = s.substring(0, i_t);
			}
		}
		return customerId;
	}
	
	
	public String getCellValue(Cell cell) {
		String ret;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			ret = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			ret = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			ret = null;
			break;
		case Cell.CELL_TYPE_FORMULA:
			Workbook wb = cell.getSheet().getWorkbook();
			CreationHelper crateHelper = wb.getCreationHelper();
			FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();
			ret = getCellValue(evaluator.evaluateInCell(cell));
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) { 
				Date theDate = cell.getDateCellValue();
				ret = simpleDateFormat.format(theDate);
			} else { 
				ret = NumberToTextConverter.toText(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_STRING:
			ret = cell.getRichStringCellValue().getString();
			break;
		default:
			ret = null;
		}
		return ret; //有必要自行trim
	}

	
	
	/**
	 * 
	 */
	private void getImage(HZ_HttpClient httpClient) throws IOException{
		//File file =new File("E:/b.jpg");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("ranLen", "1");
		params.put("imageFile", "/images/bg_mask.jpg");
		params.put("x", "12");
		params.put("y", "14");
		params.put("fontColor", "FFFFFF");
		httpClient.requestGetMethod("/imgmerge", params, null);
		//byte[] byteImg1 = httpClient.requestGetMethod("/imgmerge", params, null);
		/*FileOutputStream fileOutputStream=new FileOutputStream(file);
		for (int i = 0; i < byteImg1.length; i++) {
			fileOutputStream.write(byteImg1[i]);
		}
		fileOutputStream.flush();
		fileOutputStream.close();*/

	}
	
	/**
	 * @throws IOException 
	 * @throws InterruptedException 
	 * 
	 */
	private void login(HZ_HttpClient httpClient, String userName, String userPwd,StringBuffer sb) throws IOException {
		Map<String, String> loginParams = new HashMap<String, String>();
		loginParams.put("userName", userName);
		loginParams.put("userPwd", userPwd);
		loginParams.put("loginType", "cardid");
		Cookie[] cookies = httpClient.getCookie();
		Map<String, String> requestHeaders = new HashMap<String, String>();
		for (Integer i = 0; i < 10; i++) {
			loginParams.put("mask", i.toString());
			requestHeaders.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			HZ_HttpClient tempHttpClient = new HZ_HttpClient("http://www.saclub.com.cn/",cookies);
			byte[] byteLoginResult = tempHttpClient.requestPostMethod("/login.do", loginParams, requestHeaders);
			
			//byte[] byteLoginResult = httpClient.requestPostMethod("/login.do", loginParams, requestHeaders);
			String loginHtml = new String(byteLoginResult,"gb2312");
			if(loginHtml.indexOf("popularizeDiv") != -1){
				put_msg(userName,"登录成功...",sb);
				break;
			}else{
				put_msg(userName,"登录失败",sb);
			}
		}

	}
	
}



