package com.hczd.download.access.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hczd.download.access.module.HZ_Access_Card_Consumption_Log;
@Component
public class HZ_AccessUtil {
	/**闽通卡登录类型，Id登录*/
	public static final String TYPE_LOGIN = "0";
	/**闽通卡下载方式：单张*/
	public static final String DOWNLOAD_TYPE_SINGLE = "72";
	/**闽通卡下载方式：全部*/
	public static final String DOWNLOAD_TYPE_All = "71";
	/**闽通卡域名*/
	public static final String HOST_URL = "http://www.fjetc.com";
	/**下载保存路径*/
	public static final String DOWNLOAD_SAVE_URL = "D:/etc_download/";
	/**下载目录*/
	public static final String PATH = "etc_download";
	@Autowired
	private HZ_Access_Card_Consumption_LogUtil hz_access_card_consumption_logUtil;
	private StringBuilder sb = new StringBuilder("");  //实时信息
	public static final int NORMAL = 0; //正常状态
	public static final int RUNNING = 1; //下载中
	public static final int DELETE_REPEAT = 2; //去除重复数据中
	public static final int FAIL = 3; //下载失败
	
	/**
	 * 模拟登录闽通卡网站
	 * @author linyb
	 * @create_date 2014-5-28下午5:05:49
	 * @return   登录成功cookie信息
	 */
	public Cookie[] login(String name){
		sb = new StringBuilder("");  //登录前 情况实时信息
		
		String username = "";
		String password = "";
		if("福建泉州－主帐户".equals(name.trim())){
			username = "510100320140528104907";
			password = "888888";
		}else if("福建厦门-主账户".equals(name.trim())){
			username = "200300320130918131610";
			password = "365365";
		}else{
			print_err_msg(sb, "登录失败，请验证账户是否存在！");
			return null;
		}
		PostMethod postMethod = null;
		GetMethod getMethod  = null;
		Cookie[] cs = null;
		try {
			print_msg(sb, "正在登录..");
			String login_url = HOST_URL +"/SelfService.aspx";  //登录url
			Document doc = Jsoup.connect(login_url).get();
			Element e = doc.getElementById("H_yzm");
			Element e1 = doc.getElementById("__VIEWSTATE");
			Element e2 = doc.getElementById("__EVENTVALIDATION");
			String yzm = e.attr("value"); //验证码的值
			String __VIEWSTATE = e1.attr("value");
			String __EVENTVALIDATION = e2.attr("value");
			HttpClient httpClient = new HttpClient();
			postMethod = new PostMethod(login_url);  //post请求
			//参数数组
			NameValuePair[] data = {
										new NameValuePair("ctl00$MainContent$logintype", TYPE_LOGIN),
										new NameValuePair("ctl00$MainContent$txtNumber", username),
										new NameValuePair("ctl00$MainContent$txtpassWord", password),
										new NameValuePair("ctl00$MainContent$u_yzm", yzm),
										new NameValuePair("__EVENTTARGET", ""),
										new NameValuePair("__EVENTARGUMENT", ""),
										new NameValuePair("__VIEWSTATE", __VIEWSTATE),
										new NameValuePair("__EVENTVALIDATION", __EVENTVALIDATION),
										new NameValuePair("ctl00$MainContent$StID", "")
									};
			postMethod.setRequestBody(data); // 参数set
			int statusCode = httpClient.executeMethod(postMethod);  //执行请求
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || /*301*/ statusCode == HttpStatus.SC_MOVED_TEMPORARILY /*302*/) {
				Header locationHeader = postMethod.getResponseHeader("location");	 // 从头中取出转向的地址
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					getMethod = new GetMethod(HOST_URL+location);
					int statusCode_302 = httpClient.executeMethod(getMethod);
					if(statusCode_302 ==HttpStatus.SC_OK  /*200*/){
						cs = httpClient.getState().getCookies(); // 获取cookie
					}
				}
			}
		} catch (Exception e) {
			return null;
		}finally{
			postMethod.releaseConnection();
			getMethod.releaseConnection();
		}
		return cs;
	}
	/**
	 * 下载闽通卡消费数据
	 * @author linyb
	 * @create_date 2014-5-29下午2:12:32
	 * @param startTime  开始时间
	 * @param endTime    结束时间
	 * @param card_no    卡号
	 * @param cs    	 登录成功返还的cookie
	 */
	public String download(String base_path ,String startTime,String endTime,String card_no,Cookie[] cs,String type){
		PostMethod postMethod = null;
		GetMethod getMethod = null;
		try {
			//1、把登录后cooike赋给httpclient
			HttpClient client = new HttpClient();
			client.getState().addCookies(cs);
			//2、请求下载信息页面
			String url = HOST_URL + "/Ajax/LSSeekHandler.ashx";
			postMethod = new PostMethod(url);  //post请求
			if(type.equals(HZ_AccessUtil.DOWNLOAD_TYPE_All)){
				card_no = "";
			}
			NameValuePair[] data = {   //参数数组
										new NameValuePair("Stime", startTime),
										new NameValuePair("Etime", endTime),
										new NameValuePair("keyword", card_no),
										new NameValuePair("type", type),
										new NameValuePair("height", "602")
									};
			postMethod.setRequestBody(data); // 参数set
			int statusCode = client.executeMethod(postMethod);  //执行请求
			if(statusCode == HttpStatus.SC_OK){  //200
				print_msg(sb, "进入下载页面..");
				//3、下载页面列表信息
				String show_ls = HOST_URL + "/ShowLS.aspx";  //列表信息
				getMethod = new GetMethod(show_ls);
				int get_s = client.executeMethod(getMethod);
				byte[] bs = getMethod.getResponseBody();
				String html = new String(bs, "utf-8");
				if(get_s == HttpStatus.SC_OK){
					//4、下载页面列表信息加密参数获取 + 请求下载
					Document d = Jsoup.parse(html);
					String __VIEWSTATE_P = d.getElementById("__VIEWSTATE").attr("value");
					String __EVENTVALIDATION_P =null;
					try {
						 __EVENTVALIDATION_P = d.getElementById("__EVENTVALIDATION").attr("value");//__EVENTVALIDATION
					} catch (Exception e) {
						print_err_msg(sb, "该时间段没有通行记录");
						return "none_msg"; //该时间段没有通行记录
					}
					postMethod = new PostMethod(show_ls);
					NameValuePair[] d2 = {
											new NameValuePair("__VIEWSTATE", __VIEWSTATE_P),
											new NameValuePair("__EVENTVALIDATION", __EVENTVALIDATION_P),
											new NameValuePair("SaveFile", "记录流水下载")
										};
					
				   postMethod.setRequestBody(d2); // 参数set
				   int d_s = client.executeMethod(postMethod);
				   if(d_s == HttpStatus.SC_OK){
					   print_msg(sb, "下载中..");
					   //5、 IO 输出下载的数据
					   InputStream in = postMethod.getResponseBodyAsStream(); 
					   File f = new File(base_path+PATH);
					   if(!f.exists()){
						   f.mkdir();
					   }
					   String path  = null;
					   if(DOWNLOAD_TYPE_All.equals(type)){
						   path = base_path+PATH+"/"+System.currentTimeMillis()+".xml";
					   }
					   if(DOWNLOAD_TYPE_SINGLE.equals(type)){
						   path = base_path+PATH+"/"+System.currentTimeMillis()+".xls";
					   }
			           FileOutputStream out = new FileOutputStream(new File(path));  
			           byte[] b = new byte[1024];  
			           int len = 0;  
			           while((len=in.read(b))!= -1){  
			               out.write(b,0,len);  
			               print_msg(sb, "下载输出中..");
			           }  
			           in.close();  
			           out.close();  
			           print_msg(sb, "下载成功..");
			           //下载全部的话 数据导入数据库中
			           if(DOWNLOAD_TYPE_All.equals(type)){
				           //6、解析xml
				           List<HZ_Access_Card_Consumption_Log> logs = parseXml(path);
				           //7、导入到数据库中
				           print_msg(sb, "准备导入数据..");
				           hz_access_card_consumption_logUtil.upload_Consumption(logs,sb);
			           }
				   }
				}
			}
			return "200";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}finally{
			postMethod.releaseConnection();
			getMethod.releaseConnection();
		}
	}
	/**
	 * 解析xml成List
	 * @author linyb
	 * @create_date 2014-6-4下午3:27:55
	 * @return
	 */
	public static List<HZ_Access_Card_Consumption_Log> parseXml(String path){
		List<HZ_Access_Card_Consumption_Log> logs = new ArrayList<HZ_Access_Card_Consumption_Log>();
		SAXReader saxReader = new SAXReader();
		org.dom4j.Document doc = null;
		try {
			doc = saxReader.read(path);
			List<org.dom4j.Element> elements = doc.selectNodes("//*[@ss:Name='Sheet1']/child::*[1]/child::*");
			for (int i = elements.size() -1 ; i >=16; i--) {
				org.dom4j.Element row = elements.get(i);
				List<org.dom4j.Element> cell_e = row.elements("Cell");
				if(!cell_e.isEmpty()){
					HZ_Access_Card_Consumption_Log log = new HZ_Access_Card_Consumption_Log();
					for (int j = cell_e.size()-1; j >= 0; j--) {
						org.dom4j.Element cell = cell_e.get(j);
						org.dom4j.Element data_e = cell.element("Data");
						if(data_e!=null){
							switch (j) {
							case 15:
								log.setSettlement_rovinces(data_e.getText()); break;//结算省份
							case 14:
								log.setCharge_pattern(data_e.getText());  break;//收费轴型
							case 13:
								log.setCharge_all_up(data_e.getText()); break; //收费总重
							case 12:
								log.setCharge_type(data_e.getText());  break;//收费类型
							case 11:
								log.setVehicle_passion(data_e.getText()); break;//车情
							case 10:
								log.setBill_no(data_e.getText());break; //发票号
							case 9:
								log.setVehicle_no(data_e.getText()); break;//车牌号码
							case 8: 
								log.setCard_no(data_e.getText()); break;//  卡片编号
							case 7:
								log.setReturn_money_num(data_e.getText()); break; //补退金额
							case 6:
								log.setMoney_num(data_e.getText()); break;//收费金额
							case 5:
								log.setReceivable_money(data_e.getText()); break;//应收金额
							case 4:
								log.setTransit_section(data_e.getText());break; //通行区间
							case 3:
								log.setClosing_date(data_e.getText()); break;//银行结算日期
							case 2:
								log.setOutlet_transit_time(data_e.getText()); break;//出口日期
							case 1:
								log.setInlet_transit_time(data_e.getText()); break;//入口日期
							default:
								break;
							}
						}
					}
					logs.add(log);
				}
			}
			return logs;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 打印信息
	 * @author linyb
	 * @create_date 2014-6-5下午2:52:27
	 */
	public void print_msg(StringBuilder sb,String msg){
		sb.insert(0,  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+":  "+msg+"<br/>");
	}
	/**
	 * 打印错误信息
	 * @author linyb
	 * @create_date 2014-6-5下午2:52:27
	 */
	public void print_err_msg(StringBuilder sb,String msg){
		sb.insert(0,  "<span style='color:red;'>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+":  "+msg+"</span><br/>");
	}
	
	public StringBuilder getConsole_info() {
		return sb;
	}
}
