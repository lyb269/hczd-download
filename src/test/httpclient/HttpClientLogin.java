/**
 * 
 */
package test.httpclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;

import com.hczd.download.common.httpclient.HZ_HttpClient;

/**
 * @author xiaojin
 * @create_date 2014-5-4下午4:28:37
 */
public class HttpClientLogin {
	public static void main(String[] args) throws IOException, InterruptedException, CloneNotSupportedException {
		HZ_HttpClient httpClient = new HZ_HttpClient("http://www.saclub.com.cn/");
		getImage(httpClient);
		
		String userName = "1000113500002425882";
		String userPwd = "851019";
		login(httpClient,userName,userPwd);
	}
	
	private static void getImage(HZ_HttpClient httpClient) throws IOException{
		Map<String, String> params = new HashMap<String, String>();
		params.put("ranLen", "1");
		params.put("imageFile", "/images/bg_mask.jpg");
		params.put("x", "12");
		params.put("y", "14");
		params.put("fontColor", "FFFFFF");
		httpClient.requestGetMethod("/imgmerge", params, null);

	}
	
	private static void login(HZ_HttpClient httpClient, String userName, String userPwd) throws IOException, InterruptedException, CloneNotSupportedException {
		Map<String, String> loginParams = new HashMap<String, String>();
		loginParams.put("userName", userName);
		loginParams.put("userPwd", userPwd);
		loginParams.put("loginType", "cardid");
		
		Map<String, String> requestHeaders = new HashMap<String, String>();
		
		Cookie[] cookies = httpClient.getCookie();
		
		for (Integer i = 0; i < 10; i++) {
			loginParams.put("mask", i.toString());
			requestHeaders.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			HZ_HttpClient tempHttpClient = new HZ_HttpClient("http://www.saclub.com.cn/",cookies);
			byte[] byteLoginResult = tempHttpClient.requestPostMethod("/login.do", loginParams, requestHeaders);
			//byte[] byteLoginResult = httpClient.requestPostMethod("/login.do", loginParams, requestHeaders);
			String loginHtml = new String(byteLoginResult,"gb2312");
			if(loginHtml.indexOf("popularizeDiv") != -1){
				System.out.println("登录成功==");
				break;
			}else{
				System.out.println("登录失败");
			}
		}

	}
}
