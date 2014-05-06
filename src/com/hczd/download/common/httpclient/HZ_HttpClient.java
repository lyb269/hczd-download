/**
 * 
 */
package com.hczd.download.common.httpclient;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @author xiaojin
 *
 */
public class HZ_HttpClient {
	
	private HttpClient httpClient;
	
	/**
	 * @throws NullPointerException 
	 * @throws URIException 
	 * 
	 */
	public HZ_HttpClient(String host) throws URIException, NullPointerException {
		this(host,null);
	}
	
	public HZ_HttpClient(String host, Cookie[] cookies) throws URIException, NullPointerException {
		httpClient = new HttpClient();
		setHost(host);
		setCookie(cookies);
	}
	
	/**
	 * 
	 */
	public void setCookie(Cookie[] cookies) { 
		if(cookies!=null){
			httpClient.getState().addCookies(cookies);
		}
	}
	
	/**
	 * 请在request后,再获取cookie
	 */
	public Cookie[] getCookie() {
		return httpClient.getState().getCookies();
	}
	
	/**
	 * @throws NullPointerException 
	 * @throws URIException 
	 * 
	 */
	public void setHost(String host) throws URIException, NullPointerException {
		URI uri = new URI(host, false);
		httpClient.getHostConfiguration().setHost(uri);
	}
	
	private byte[] request(HttpMethod method, Map<String,String> params, Map<String,String> requestHeaders) throws IOException{
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if(params!=null){
			for (String name : params.keySet()) {
				list.add(new NameValuePair(name, params.get(name).toString()));
			}
		}
		
		if(method instanceof PostMethod){
			PostMethod postMethod = (PostMethod)method;
			postMethod.setRequestBody(list.toArray(new NameValuePair[list.size()]));
		}else{
			GetMethod getMethod = (GetMethod)method;
			getMethod.setQueryString(list.toArray(new NameValuePair[list.size()]));
			
		}
		
		if(requestHeaders!=null){
			for (String key : requestHeaders.keySet()) {
				method.setRequestHeader(key, requestHeaders.get(key));
			}
		}
		
		try {
			httpClient.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return method.getResponseBody();
	}

	/**
	 * 如存在编码问题，请设置此参数
	 * requestHeaders.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	 * @param uri
	 * @param params
	 * @param requestHeaders
	 * @return
	 * @throws IOException
	 */
	public byte[] requestPostMethod(String uri,Map<String,String> params, Map<String,String> requestHeaders) throws IOException{
		return request(new PostMethod(uri), params, requestHeaders);
	}
	
	public byte[] requestGetMethod(String uri,Map<String,String> params, Map<String,String> requestHeaders) throws IOException{
		return request(new GetMethod(uri), params, requestHeaders);
	}
	

	
	public static void main(String[] args) throws NullPointerException, IOException {
		HZ_HttpClient client = new HZ_HttpClient("http://localhost:8080/");
		String username="肖金";
		String password = "a123456";
		String view = "login";
		Map<String,String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);
		params.put("view", view);
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		byte[] loginByte = client.requestPostMethod("/hczd-sys/authority/login/login.htm", params, requestHeaders);
        
		System.out.println(new String(loginByte,"UTF-8"));
		
		Map<String,String> params1 = new HashMap<String, String>();
		params1.put("main_card_no", "100011");
		params1.put("password", password);
		params1.put("view", view);
		params1.put("showAssigned", "false");
		params1.put("page", "1");
		params1.put("rows", "15");
		params1.put("sort", "card_no");
		params1.put("order", "asc");
        
		
		byte[] listByte = client.requestGetMethod("/hczd-sys/card/gas_card_allocation_record/ajax_app_list_sinopec.htm", params1, null);
	
		System.out.println(new String(listByte,"UTF-8"));
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
	
	 
}
