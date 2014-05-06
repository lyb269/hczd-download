/**
 * 
 */
package com.hczd.download.common.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @author xiaojin
 *
 */
public class HZ_HttpRequestUtil {
	/**
	 * 请求登录
	 */
	public static String login(String username,String pwd,String valcode,String rememberMe) throws IOException{
		HttpClient httpClient = new HttpClient();
        httpClient.getHostConfiguration().setHost("www.sinopecsales.com", 80, "http");
        //设置表单数据
        PostMethod method = new PostMethod("/gas/html/memberLoginAction_loginActionf.json"); 
        //{'memberAccount': $("#memberAccount").val(),'memberPwd': pwd,'check':$("#check").val(),'rememberMe':r?"on":"off"},
        if(pwd==null || "".equals(pwd)){
        	pwd="hczd365365";
        }
        //密码加密
        pwd = SHA1(pwd);
        
        NameValuePair[] simcard = {
                new NameValuePair("memberAccount", username),  
                new NameValuePair("memberPwd", pwd),  
                new NameValuePair("check", valcode),  
                new NameValuePair("rememberMe", rememberMe),
                };
        method.setRequestBody(simcard);
        //设置表头信息
        method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.2; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");  
        method.setRequestHeader("Host", "www.sinopecsales.com");  
        method.setRequestHeader("Accept", "application/json, text/javascript, */*");  
        method.setRequestHeader("Accept-Language", "zh-cn,zh;q=0.5");  
        method.setRequestHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");  
        method.setRequestHeader("Connection", "keep-alive");  
        method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");  
        method.setRequestHeader("X-Requested-With", "XMLHttpRequest");  
        method.setRequestHeader("Referer", "http://www.sinopecsales.com/gas/default.jsp");  
        //cookie中，必须保存JSESSIONID
        //httpClient.getState().addCookies(getCookie());
        try {
			httpClient.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return method.getResponseBodyAsString();
	}
	
	public static byte[] requset(String host, String url, Cookie[] cookie, String Method, Map<String,Object> params,Map<String,String> requestHeaders) throws NullPointerException, IOException{
		HttpClient httpClient = new HttpClient();
		URI uri = new URI(host, false); 
		httpClient.getHostConfiguration().setHost(uri);
		GetMethod method = new GetMethod(url);
		httpClient.getState().addCookies(cookie);
		try {
			httpClient.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return method.getResponseBody();
	}
	
	public static String SHA1(String inStr) {
        MessageDigest md = null;
        String outStr = null;
        try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(inStr.getBytes());       //返回的是byet[]，要转化为String存储比较方便
			outStr = bytetoString(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        return outStr;
    }
	
	 public static String bytetoString(byte[] digest) {
        String str = "";
        String tempStr = "";
        for (int i = 0; i < digest.length; i++) {
            tempStr = (Integer.toHexString(digest[i] & 0xff));
            if (tempStr.length() == 1) {
                str = str + "0" + tempStr;
            }
            else {
                str = str + tempStr;
            }
        }
        return str.toLowerCase();
    }
	 
	public static void main(String[] args) throws IOException{
		
	}

}




