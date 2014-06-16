package test.httpclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hczd.download.access.module.HZ_Access_Card_Consumption_Log;
import com.hczd.download.access.util.HZ_Access_Card_Consumption_LogUtil;
import com.hczd.download.common.httpclient.HZ_HttpClient;

@ContextConfiguration(locations = "classpath:com/hczd/download/config/spring/app-*.xml")
public class MTKDemo1 {
	private Object array;

	@Test
	public void testLogin(){
		try {
			Document doc = Jsoup.connect("http://www.fjetc.com/SelfService.aspx").get();
			Element e = doc.getElementById("H_yzm");
			String yzm = e.attr("value"); //验证码的值
			String username  = "200300320130918131610"; //账户
			String password = "365365";  //密码
			String type = "0";  //登录类型
			System.out.println(yzm);
			
		/*	ctl00$MainContent$logintype:0
			ctl00$MainContent$txtNumber:200300320130918131610
			ctl00$MainContent$txtpassWord:365365
			ctl00$MainContent$u_yzm:7678
			H_yzm:7678*/
			
			HZ_HttpClient client = new HZ_HttpClient("http://www.fjetc.com/");
			Map<String,String> params = new HashMap<String, String>();
			params.put("ctl00$MainContent$logintype", type);
			params.put("ctl00$MainContent$txtNumber", username);
			params.put("ctl00$MainContent$txtpassWord", password);
			params.put("ctl00$MainContent$u_yzm", yzm);
			
			byte[] byteLoginResult = client.requestPostMethod("/SelfServiceOperate.aspx", params, null);
			System.out.println(new String(byteLoginResult, "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Cookie[] testLogin2(){
		PostMethod postMethod = null;
		GetMethod getMethod  = null;
		Cookie[] cs = null;
		try {
			Document doc = Jsoup.connect("http://www.fjetc.com/SelfService.aspx").get();
			Element e = doc.getElementById("H_yzm");
			Element e1 = doc.getElementById("__VIEWSTATE");
			Element e2 = doc.getElementById("__EVENTVALIDATION");
			String yzm = e.attr("value"); //验证码的值
			String __VIEWSTATE = e1.attr("value");
			String __EVENTVALIDATION = e2.attr("value");
			String username  = "200300320130918131610"; //账户
			String password = "365365";  //密码
			String type = "0";  //登录类型
			/*	ctl00$MainContent$logintype:0
			ctl00$MainContent$txtNumber:200300320130918131610
			ctl00$MainContent$txtpassWord:365365
			ctl00$MainContent$u_yzm:7678
			H_yzm:7678
			__EVENTTARGET:
			__EVENTARGUMENT:
			__VIEWSTATE:/wEPDwUKMTQwNTczMjE0OGRkEowWVZbKmfXay06qhuJdbp8i1hrf0yrAjD1UJZtvU98=
			__EVENTVALIDATION:/wEWBgK0iIJbAqWUyzMC2ZveywcCjrD3sgEC286AqwoC8+7Cug76lmEooVK3ksKkSUDAmcSC0tR3hw9QtnKVFkOQMQTQjA==
		*/
			HttpClient httpClient = new HttpClient();
			//httpClient.getHostConfiguration().setHost("http://www.fjetc.com/");
			String url = "http://www.fjetc.com/SelfService.aspx";
			postMethod = new PostMethod(url);  //post请求
			//参数数组
			NameValuePair[] data = {
										new NameValuePair("ctl00$MainContent$logintype", type),
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
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY ||   //301
					statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {  //302
				Header locationHeader = postMethod.getResponseHeader("location");	 // 从头中取出转向的地址
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					url = "http://www.fjetc.com"+location;
					getMethod = new GetMethod(url);
					int statusCode_302 = httpClient.executeMethod(getMethod);  //200
					System.out.println(getMethod.getResponseBodyAsString());
					cs = httpClient.getState().getCookies();
					
					//String str_302 = getMethod.getResponseBodyAsString();
					//System.out.println(str_302);
					
				/*	doc = Jsoup.connect(url).get();
					e = doc.getElementById("__EVENTVALIDATION");
					yzm = e.attr("value"); 
					System.out.println(yzm);*/
				} else {
					System.err.println("302 Location field value is null.");
					return null;
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
	
	
	
	@Test
	public void testLogin3(){
		try {
			Document doc = Jsoup.connect("http://www.fjetc.com/SelfService.aspx").get();
			
			Element e = doc.getElementById("H_yzm");
			String yzm = e.attr("value"); //验证码的值
			String username  = "200300320130918131610"; //账户
			String password = "365365";  //密码
			String type = "0";  //登录类型
			System.out.println(yzm);
			
		/*	ctl00$MainContent$logintype:0
			ctl00$MainContent$txtNumber:200300320130918131610
			ctl00$MainContent$txtpassWord:365365
			ctl00$MainContent$u_yzm:7678
			H_yzm:7678
			__EVENTTARGET:
			__EVENTARGUMENT:
			__VIEWSTATE:/wEPDwUKMTQwNTczMjE0OGRkEowWVZbKmfXay06qhuJdbp8i1hrf0yrAjD1UJZtvU98=
			__EVENTVALIDATION:/wEWBgK0iIJbAqWUyzMC2ZveywcCjrD3sgEC286AqwoC8+7Cug76lmEooVK3ksKkSUDAmcSC0tR3hw9QtnKVFkOQMQTQjA==
		*/
			System.out.println("1%e2%88%9dOWtPWUZab1hzand4alMydlpKVXBSRFFsU1B2eDV4SlVJeTF1VHh3a0NCVERjV2FFNG5pWGRJdENCNnFoZXRxc08rVEVkNVZ4enA5NEd1ZW9heW5QUlFCNzNjRFdUcE9JSmVlSStuQnlEWFRLU0VKbDNMNG9nUT09"
					.equals("1%e2%88%9dOWtPWUZab1hzand4alMydlpKVXBSRFFsU1B2eDV4SlVJeTF1VHh3a0NCVERjV2FFNG5pWGRJdENCNnFoZXRxc08rVEVkNVZ4enA5NEd1ZW9heW5QUlFCNzNjRFdUcE9JSmVlSStuQnlEWFRLU0VKbDNMNG9nUT09")
					);
			
			String param = "1%e2%88%9dOWtPWUZab1hzand4alMydlpKVXBSRFFsU1B2eDV4SlVJeTF1VHh3a0NCVERjV2FFNG5pWGRJdENCNnFoZXRxc08rVEVkNVZ4enA5NEd1ZW9heW5QUlFCNzNjRFdUcE9JSmVlSStuQnlEWFRLU0VKbDNMNG9nUT09";
			HttpClient httpClient = new HttpClient();
			String url = "http://www.fjetc.com/UserChecking.aspx?CheckUrl="+param;
			GetMethod getMethod = new GetMethod(url);
			//getMethod.getParams().setParameter("CheckUrl", param);
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus. SC_OK) {
				System.err.println("Method failed: "+ getMethod.getStatusLine());
			}
			byte[] responseBody = getMethod.getResponseBody();
			System.out.println(new String(responseBody));
			System.out.println(statusCode);
			System.out.println(Arrays.toString(getMethod.getRequestHeaders()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	@Test
	public void testLogin4(){
		try {
			Document doc = Jsoup.connect("http://www.fjetc.com/SelfService.aspx").get();
			Element e = doc.getElementById("H_yzm");
			String yzm = e.attr("value"); //验证码的值
			String username  = "200300320130918131610"; //账户
			String password = "365365";  //密码
			String type = "0";  //登录类型
			
		/*	ctl00$MainContent$logintype:0
			ctl00$MainContent$txtNumber:200300320130918131610
			ctl00$MainContent$txtpassWord:365365
			ctl00$MainContent$u_yzm:7678
			H_yzm:7678*/
			
			HttpClient httpClient = new HttpClient();
			httpClient.getHostConfiguration().setHost("www.fjetc.com", 80, "http");
			
			String url = "http://www.fjetc.com/SelfService.aspx";
			PostMethod postMethod = new PostMethod(url);  //post请求
			//参数数组
			NameValuePair[] data = {
					new NameValuePair("ctl00$MainContent$logintype", type),
					new NameValuePair("ctl00$MainContent$txtNumber", username),
					new NameValuePair("ctl00$MainContent$txtpassWord", password),
					new NameValuePair("ctl00$MainContent$u_yzm", yzm)
									};
			postMethod.setRequestBody(data); // 参数set
			int statusCode = httpClient.executeMethod(postMethod);  //执行请求
			Header[] hs = postMethod.getRequestHeaders();
			Header header = postMethod.getResponseHeader("Set-Cookie"); 
			Cookie[] cs = httpClient.getState().getCookies();
			for (int i = 0; i < cs.length; i++) {
				Cookie c = cs[i];
				System.out.println(c.toString());
			}
			/*System.out.println("statusCode:" +statusCode);
			String str = postMethod.getResponseBodyAsString();
			System.out.println(new String(str));
			System.out.println("================================");
			System.out.println(statusCode);
			System.out.println("================================");
			System.out.println(Arrays.toString(hs));
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY ||   //301
					statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {  //302
				Header locationHeader = postMethod.getResponseHeader("location");	 // 从头中取出转向的地址
				Header cookieHeader = postMethod.getResponseHeader("Cookie");	 // 从头中取出转向的地址
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					GetMethod getMethod = new GetMethod(location);
                    getMethod.setRequestHeader("Cookie",cookieHeader.toString());     
                   // httpClient.excuteMethod(getMethod);     
					System.out.println("The page was redirected to:" + location);
					} else {
					System.err.println("Location field value is null.");
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLogin5(){
		PostMethod postMethod = null;
		 PostMethod postMethod2 = null;
        try { 
        	Document doc = Jsoup.connect("http://www.fjetc.com/SelfService.aspx").get();
    		Element e = doc.getElementById("H_yzm");
    		String yzm = e.attr("value"); //验证码的值
    		String username  = "200300320130918131610"; //账户
    		String password = "365365";  //密码
    		String type = "0";  //登录类型
    		
    		String url = "http://www.fjetc.com/SelfService.aspx";//登陆页面 
           // String url2="http://discuzdemo.c88.53dns.com/post.php?infloat=yes&action=newthread&fid=2&extra=&topicsubmit=yes&inajax=1";//论坛的发贴页面 
            HttpClient httpClient = new HttpClient(); 
            httpClient.getParams().setCookiePolicy( 
                    CookiePolicy.BROWSER_COMPATIBILITY); 
            postMethod = new PostMethod(url); 
           // postMethod2 = new PostMethod(url2); 
        	NameValuePair[] data = {
    				new NameValuePair("ctl00$MainContent$logintype", type),
    				new NameValuePair("ctl00$MainContent$txtNumber", username),
    				new NameValuePair("ctl00$MainContent$txtpassWord", password),
    				new NameValuePair("ctl00$MainContent$u_yzm", yzm)
    								};
            postMethod.setRequestHeader("Referer", 
                    "http://www.fjetc.com/SelfService.aspx"); 
            postMethod.setRequestHeader("Host", "www.fjetc.com"); 
            postMethod 
                    .setRequestHeader( 
                            "User-Agent", 
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36"); 
            postMethod 
                    .setRequestHeader("Accept", 
                            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"); 
            postMethod.setRequestBody(data); 
            httpClient.executeMethod(postMethod); 
            String method1Response = postMethod.getResponseBodyAsString();
            System.out.println(method1Response); 
            
            Cookie[] cookies=httpClient.getState().getCookies();//取出登陆成功后，服务器返回的cookies信息，里面保存了服务器端给的“临时证” 
            for(Cookie c:cookies){ 
                System.out.println(c.toString()); 
            } 
            //ASP.NET_SessionId=01bgqlintyxhcblfjfrqgbvz
           /* NameValuePair[] data2 = { 
                    new NameValuePair("subject", "测试自动发贴"), 
                    new NameValuePair("message", 
                            "能否发贴成功呢？测试一下就知道了"), 
                    new NameValuePair("updateswfattach", "0"), 
                    new NameValuePair("wysiwyg", "0"), 
                    new NameValuePair("checkbox", "0"), 
                    new NameValuePair("handlekey", "newthread"), 
                    new NameValuePair("formhash", "885493ec") }; 
            postMethod2.setRequestHeader("cookie",tmpcookies);//将“临时证明”放入下一次的发贴请求操作中 
            postMethod2.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "gbk");//因为发贴时候有中文，设置一下请求编码 
            postMethod2.setRequestHeader("Referer", 
                    "http://discuzdemo.c88.53dns.com/forumdisplay.php?fid=4"); 
            postMethod2.setRequestHeader("Host", "discuzdemo.c88.53dns.com"); 
         
            postMethod2 
                    .setRequestHeader( 
                            "User-Agent", 
                            "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2"); 
            postMethod2 
                    .setRequestHeader("Accept", */
                          //  "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");//以上操作是模拟浏览器的操作，使用服务器混淆 
             
           /* postMethod2.setRequestBody(data2); 
            httpClient.executeMethod(postMethod2); 
            StringBuffer response1 = new StringBuffer(); 
            BufferedReader reader1 = new BufferedReader(new InputStreamReader( 
                    postMethod2.getResponseBodyAsStream(), "gb2312")); 
            String line1; 
            while ((line1 = reader1.readLine()) != null) { 
                response1.append(line1).append( 
                        System.getProperty("line.separator")); 
            } 
            reader1.close(); */
            //System.out.println(response1); 
        } catch (Exception e1) { 
            System.out.println(e1.getMessage()); 
            // TODO: handle exception 
        } finally { 
            postMethod.releaseConnection(); 
            //postMethod2.releaseConnection(); 
        } 
    } 
	
	public Map<String,Object> testLogin6(){
		Map<String,Object> msg = new HashMap<String, Object>();
		PostMethod postMethod = null;
		GetMethod getMethod  = null;
		Cookie[] cs = null;
		try {
			Document doc = Jsoup.connect("http://www.fjetc.com/SelfService.aspx").get();
			Element e = doc.getElementById("H_yzm");
			Element e1 = doc.getElementById("__VIEWSTATE");
			Element e2 = doc.getElementById("__EVENTVALIDATION");
			String yzm = e.attr("value"); //验证码的值
			String __VIEWSTATE = e1.attr("value");
			String __EVENTVALIDATION = e2.attr("value");
			String username  = "200300320130918131610"; //账户
			String password = "365365";  //密码
			String type = "0";  //登录类型
			/*	ctl00$MainContent$logintype:0
			ctl00$MainContent$txtNumber:200300320130918131610
			ctl00$MainContent$txtpassWord:365365
			ctl00$MainContent$u_yzm:7678
			H_yzm:7678
			__EVENTTARGET:
			__EVENTARGUMENT:
			__VIEWSTATE:/wEPDwUKMTQwNTczMjE0OGRkEowWVZbKmfXay06qhuJdbp8i1hrf0yrAjD1UJZtvU98=
			__EVENTVALIDATION:/wEWBgK0iIJbAqWUyzMC2ZveywcCjrD3sgEC286AqwoC8+7Cug76lmEooVK3ksKkSUDAmcSC0tR3hw9QtnKVFkOQMQTQjA==
		*/
			HttpClient httpClient = new HttpClient();
			//httpClient.getHostConfiguration().setHost("http://www.fjetc.com/");
			String url = "http://www.fjetc.com/SelfService.aspx";
			postMethod = new PostMethod(url);  //post请求
			//参数数组
			NameValuePair[] data = {
										new NameValuePair("ctl00$MainContent$logintype", type),
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
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY ||   //301
					statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {  //302
				Header locationHeader = postMethod.getResponseHeader("location");	 // 从头中取出转向的地址
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					url = "http://www.fjetc.com"+location;
					getMethod = new GetMethod(url);
					int statusCode_302 = httpClient.executeMethod(getMethod);  //200
					cs = httpClient.getState().getCookies();
					msg.put("cookie", cs);
					msg.put("html", getMethod.getResponseBodyAsString());
					return msg;
					
				} else {
					System.err.println("302 Location field value is null.");
				}
			}
		} catch (Exception e) {
			return null;
		}finally{
			postMethod.releaseConnection();
			getMethod.releaseConnection();
		}
		return null;
	}
	
	
	//http://www.fjetc.com/SeekLS.htm?StartTime=2014-05-01&EndTime=2014-05-31&keyword=35019103130700049504&type=72&h=602&
	//http://www.fjetc.com/Ajax/LSSeekHandler.ashx
	@Test
	public void toDownload(){
		Cookie[] cs = testLogin2();  //登录
		PostMethod postMethod = null;
		GetMethod getMthod = null;
		//int statusCode_302 = httpClient.executeMethod(getMethod);  //200
		try {
			//String to_url = "http://www.fjetc.com/SeekLS.htm?StartTime=2014-05-01&EndTime=2014-05-31&keyword=35019303131100032019&type=72&h=602&";
			HttpClient client = new HttpClient();
			client.getState().addCookies(cs);
/*			GetMethod getMethod = new GetMethod(to_url);
			int statusCode= client.executeMethod(getMethod); 
			byte[] bs = getMethod.getResponseBody();
			System.out.println(new String(bs, "utf-8"));
			System.out.println(statusCode);*/
		/*	if(statusCode == HttpStatus.SC_OK){
				String url = "http://www.fjetc.com/ShowLS.aspx";
				getMethod = new GetMethod(url);
				int code = client.executeMethod(getMethod);
				System.out.println(code);
			}*/
			
			//httpClient.getHostConfiguration().setHost("http://www.fjetc.com/");
			String url = "http://www.fjetc.com/Ajax/LSSeekHandler.ashx";
			postMethod = new PostMethod(url);  //post请求
			//参数数组
			NameValuePair[] data = {
										new NameValuePair("Stime", "2014-04-01"),
										new NameValuePair("Etime", "2014-05-31"),
										new NameValuePair("keyword", "35019303131100032022"),
										new NameValuePair("type", "72"),
										new NameValuePair("height", "602")
									};
			postMethod.setRequestBody(data); // 参数set
			int statusCode = client.executeMethod(postMethod);  //执行请求
			byte[] bs = postMethod.getResponseBody();
			System.out.println(new String(bs, "utf-8"));
			System.out.println(statusCode);
			//http://www.fjetc.com/ShowLS.aspx
			
			getMthod = new GetMethod("http://www.fjetc.com/ShowLS.aspx");
			int get_s = client.executeMethod(getMthod);
			System.out.println(get_s);
			bs = getMthod.getResponseBody();
			String html = new String(bs, "utf-8");
			
			Document d = Jsoup.parse(html);
			Element e = d.getElementById("__VIEWSTATE");
			Element e1 = d.getElementById("__EVENTVALIDATION");
			String s = e.attr("value");
			String s1 = e1.attr("value");
			
			postMethod = new PostMethod("http://www.fjetc.com/ShowLS.aspx");
			NameValuePair[] d2 = {
									new NameValuePair("__VIEWSTATE", s),
									new NameValuePair("__EVENTVALIDATION", s1),
									new NameValuePair("SaveFile", "记录流水下载")
								};
			
		   postMethod.setRequestBody(d2); // 参数set
		   int d_s = client.executeMethod(postMethod);
		   System.out.println(d_s);
		   
		   InputStream in = postMethod.getResponseBodyAsStream();  
           
           FileOutputStream out = new FileOutputStream(new File("D:/download/"+System.currentTimeMillis()+".xls"));  
            
           byte[] b = new byte[1024];  
           int len = 0;  
           while((len=in.read(b))!= -1){  
               out.write(b,0,len);  
           }  
           in.close();  
           out.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			postMethod.releaseConnection();
		}
	}
	@Test
	public void toDownload_all(){
		Cookie[] cs = testLogin2();  //登录
		PostMethod postMethod = null;
		GetMethod getMthod = null;
		//int statusCode_302 = httpClient.executeMethod(getMethod);  //200
		try {
			//String to_url = "http://www.fjetc.com/SeekLS.htm?StartTime=2014-05-01&EndTime=2014-05-31&keyword=35019303131100032019&type=72&h=602&";
			HttpClient client = new HttpClient();
			client.getState().addCookies(cs);
/*			GetMethod getMethod = new GetMethod(to_url);
			int statusCode= client.executeMethod(getMethod); 
			byte[] bs = getMethod.getResponseBody();
			System.out.println(new String(bs, "utf-8"));
			System.out.println(statusCode);*/
		/*	if(statusCode == HttpStatus.SC_OK){
				String url = "http://www.fjetc.com/ShowLS.aspx";
				getMethod = new GetMethod(url);
				int code = client.executeMethod(getMethod);
				System.out.println(code);
			}*/
			
			//httpClient.getHostConfiguration().setHost("http://www.fjetc.com/");
			String url = "http://www.fjetc.com/Ajax/LSSeekHandler.ashx";
			postMethod = new PostMethod(url);  //post请求
			//参数数组
			NameValuePair[] data = {
										new NameValuePair("Stime", "2014-05-25"),
										new NameValuePair("Etime", "2014-05-31"),
										new NameValuePair("keyword", ""),
										new NameValuePair("type", "71"),
										new NameValuePair("height", "602")
									};
			postMethod.setRequestBody(data); // 参数set
			int statusCode = client.executeMethod(postMethod);  //执行请求
			byte[] bs = postMethod.getResponseBody();
			System.out.println(new String(bs, "utf-8"));
			System.out.println(statusCode);
			//http://www.fjetc.com/ShowLS.aspx
			
			getMthod = new GetMethod("http://www.fjetc.com/ShowLS.aspx");
			int get_s = client.executeMethod(getMthod);
			System.out.println(get_s);
			bs = getMthod.getResponseBody();
			String html = new String(bs, "utf-8");
			System.out.println(html);
			Document d = Jsoup.parse(html);
			Element e = d.getElementById("__VIEWSTATE");
			Element e1 = d.getElementById("__EVENTVALIDATION");
			String s = e.attr("value");
			String s1 = e1.attr("value");
			
			postMethod = new PostMethod("http://www.fjetc.com/ShowLS.aspx");
			NameValuePair[] d2 = {
									new NameValuePair("__VIEWSTATE", s),
									new NameValuePair("__EVENTVALIDATION", s1),
									new NameValuePair("SaveFile", "记录流水下载")
								};
			
		   postMethod.setRequestBody(d2); // 参数set
		   int d_s = client.executeMethod(postMethod);
		   System.out.println(d_s);
		   
		   InputStream in = postMethod.getResponseBodyAsStream();  
		   File f = new File("D:/download");
		   if(!f.exists()){
			   f.mkdir();
		   }
		   String file_name = "D:/download/"+System.currentTimeMillis()+".xml";
           FileOutputStream out = new FileOutputStream(new File(file_name));  
            
           byte[] b = new byte[1024];  
           int len = 0;  
           while((len=in.read(b))!= -1){  
               out.write(b,0,len);  
           }  
           
           in.close();  
           out.close();  
           
           SAXReader saxReader = new SAXReader();
           saxReader.read(file_name);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			postMethod.releaseConnection();
		}
	}
	
	@Test
	public void card_detail(){
		Map<String,Object> msg = testLogin6();  //登录
		PostMethod postMethod = null;
		try {
			HttpClient client = new HttpClient();
			Cookie[] cs = (Cookie[]) msg.get("cookie");
			client.getState().addCookies(cs);
			String html = (String) msg.get("html");
			Document d = Jsoup.parse(html);
			Element e = d.getElementById("__VIEWSTATE");
			Element e1 = d.getElementById("__EVENTVALIDATION");
			String s = e.attr("value");
			String s1 = e1.attr("value");
			postMethod = new PostMethod("http://www.fjetc.com/SelfServiceOperate.aspx");
			//参数数组
			NameValuePair[] data = {
										new NameValuePair("__EVENTTARGET", ""),
										new NameValuePair("__EVENTARGUMENT", ""),
										new NameValuePair("__VIEWSTATE", s),
										new NameValuePair("ctl00$MainContent$DDLZD", "0"),
										new NameValuePair("ctl00$MainContent$DDLYear", "2014"),
										new NameValuePair("ctl00$MainContent$DDLMonth", "1"),
										//new NameValuePair("keyword", "黄闽DB5867"),
										new NameValuePair("keyword", "请选择一车辆："),
										new NameValuePair("drpservicearea", ""),
										//new NameValuePair("ctl00$MainContent$resTx", "35019303131100032023"),
										new NameValuePair("ctl00$MainContent$resTx", ""),
										new NameValuePair("ctl00$MainContent$DDLCardNum", "-1"),
										//new NameValuePair("ctl00$MainContent$DDLCardNum", "35019303131100032023"),
										new NameValuePair("text", ""),
										new NameValuePair("text2", ""),
										new NameValuePair("ctl00$MainContent$pwdtype", "-1"),
										new NameValuePair("OldPassWord", ""),
										new NameValuePair("NewPassWord", ""),
										new NameValuePair("__CALLBACKID", "__Page"),
										new NameValuePair("__CALLBACKPARAM", "CXYE"),
										new NameValuePair("__EVENTVALIDATION", s1)
									};
			postMethod.setRequestBody(data); // 参数set
			int d_s = client.executeMethod(postMethod);
			System.out.println(postMethod.getResponseBodyAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testExcel() throws IOException{
		//File file =new File("D:/download/1401762050868.xlsx");
		File file =new File("D:/download/aa.xlsx");
		Workbook wb = null;
		FileInputStream fis = null;
		if(file.length()>0){
			fis = new FileInputStream(file);
			try {
				wb = MTKDemo1.create(fis);
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
		}else{
			return ;
		}
		Sheet sheet = wb.getSheetAt(0);
		int rows = sheet.getPhysicalNumberOfRows();
		Row row = sheet.getRow(1);
		String l_1 = row.getCell(1).getStringCellValue();
		System.out.println(l_1);
		fis.close();
	}
	
	public static Workbook create(InputStream inp) throws IOException,InvalidFormatException {
	    if (!inp.markSupported()) {
	        inp = new PushbackInputStream(inp, 8);
	    }
	    if (POIFSFileSystem.hasPOIFSHeader(inp)) {
	        return new HSSFWorkbook(inp);
	    }
	    if (POIXMLDocument.hasOOXMLHeader(inp)) {
	        return new XSSFWorkbook(OPCPackage.open(inp));
	    }
	    throw new IllegalArgumentException("你的excel版本目前poi解析不了");
	}
	
	@Test
	public void test_xml(){
		List<HZ_Access_Card_Consumption_Log> logs = new ArrayList<HZ_Access_Card_Consumption_Log>();
		int count = 0;
		String file_name = "D:/download/1401767826683.xml";
		SAXReader saxReader = new SAXReader();
		org.dom4j.Document doc = null;
		try {
			doc = saxReader.read(file_name);
			//List<org.dom4j.Element> elements = doc.selectNodes("/*[name()='Workbook'/*[name()='Worksheet']");
			//List<org.dom4j.Element> elements = doc.selectNodes("/Workbook/Worksheet[@ss:Name=Sheet1]/Table/Row");
			List<org.dom4j.Element> elements = doc.selectNodes("//*[@ss:Name='Sheet1']/child::*[1]/child::*");
			for (int i = elements.size() -1 ; i >=16; i--) {
				count++;
				org.dom4j.Element row = elements.get(i);
				List<org.dom4j.Element> cell_e = row.elements("Cell");
				if(!cell_e.isEmpty()){
					HZ_Access_Card_Consumption_Log log = new HZ_Access_Card_Consumption_Log();
					for (int j = cell_e.size()-1; j >= 0; j--) {
						org.dom4j.Element cell = cell_e.get(j);
						org.dom4j.Element data_e = cell.element("Data");
						if(data_e!=null){
							//sb.append(data_e.getText()+" || ");
							//System.out.println(data_e.getText()+"--->"+j);
							switch (j) {
							case 15:
								log.setSettlement_rovinces(data_e.getText()); //结算省份
								break;
							case 14:
								log.setCharge_pattern(data_e.getText());  //收费轴型
								break;
							case 13:
								log.setCharge_all_up(data_e.getText());  //收费总重
								break;
							case 12:
								log.setCharge_type(data_e.getText());  //收费类型
								break;
							case 11:
								log.setVehicle_passion(data_e.getText()); //车情
								break;
							case 10:
								log.setBill_no(data_e.getText()); //发票号
								break;
							case 9:
								log.setVehicle_no(data_e.getText()); //车牌号码
								break;
							case 8: 
								log.setCard_no(data_e.getText()); //  卡片编号
								break;
							case 7:
								log.setReturn_money_num(data_e.getText());  //补退金额
								break;
							case 6:
								log.setMoney_num(data_e.getText()); //收费金额
								break;
							case 5:
								log.setReceivable_money(data_e.getText()); //应收金额
								break;
							case 4:
								log.setTransit_section(data_e.getText()); //通行区间
								break;
							case 3:
								log.setClosing_date(data_e.getText()); //银行结算日期
								break;
							case 2:
								log.setOutlet_transit_time(data_e.getText()); //出口日期
								break;
							case 1:
								log.setInlet_transit_time(data_e.getText()); //入口日期
								break;
							default:
								break;
							}
						}
					}
					logs.add(log);
					//System.out.println(sb.toString());	
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}  
	
	@Test
	public void test_login_sub(){
		HttpClient httpClient = new HttpClient();
		String url = "http://10.1.1.85:8080/hczd-client/authority/login/switching_login.htm?id=1" ;
		GetMethod getMethod = new GetMethod(url);
		try {
			int login_status = httpClient.executeMethod(getMethod);
			String str = getMethod.getResponseBodyAsString();
			if(StringUtils.isNotBlank(str)){
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();
				Map<String,Object> result_map = gson.fromJson(str, new TypeToken<Map<String,Object>>(){}.getType());
				System.out.println(result_map.get("login_info"));
				System.out.println(result_map.get("customer_user"));
				
			}else{
				//没登录
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
