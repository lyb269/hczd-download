/**
 * 
 */
package test.httpclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import junit.framework.TestCase;
import net.sf.json.JSONObject;

import com.hczd.download.common.httpclient.HZ_HttpClient;

/**
 * @author xiaojin
 *
 */
public class HttpClientDemo1 extends TestCase{
	
	public void testloginSinopec() throws IOException{
		File file =new File("E:/a.jpg");
		
		HZ_HttpClient httpClient = new HZ_HttpClient("http://www.sinopecsales.com/");
		
		byte[] byteImg = httpClient.requestGetMethod("/gas/YanZhengMaServlet?"+Math.random(), null, null);
		FileOutputStream fileOutputStream=new FileOutputStream(file);
		for (int i = 0; i < byteImg.length; i++) {
			fileOutputStream.write(byteImg[i]);
		}
		fileOutputStream.flush();
		fileOutputStream.close();
		
		
		String userName="fjhczd";
		String password = "hczd365365";
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("请输入登录验证码");
		String loginYZM = scanner.next();
		String rememberMe= "off";//暂时设置成off
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("memberAccount", userName);
		params.put("memberPwd", SHA1(password));
		params.put("check", loginYZM);
		params.put("rememberMe", rememberMe);
		
		
		byte[] byteLoginResult = httpClient.requestPostMethod("/gas/html/memberLoginAction_loginActionf.json", params, null);
		JSONObject jsonObject=JSONObject.fromObject(new String(byteLoginResult).replaceAll("\"", "\\\""));
		if(loginValidate(jsonObject)){
			System.out.println("登录成功");
		}else{
			System.out.println("=====登录失败=====");
			return ;
		}
	}
	
	private static boolean loginValidate(JSONObject jsonObject){
		int result = Integer.parseInt(jsonObject.get("success").toString());
		switch (result){
			case 0:{return true;}
			case 1:{
				if(jsonObject.get("LoginFalCount")==null||"".equals(jsonObject.get("LoginFalCount"))){
					System.out.println("用户名/密码错误!");
					break;
				}else{
					int loginFalCount=Integer.parseInt(jsonObject.get("LoginFalCount").toString());
					if(loginFalCount == 6){
						System.out.println("用户名/密码错误!您当天6次尝试次数已使用完，请明天在尝试或联系客服人员!");
						break;
					}else{
						System.out.println("用户名/密码错误!您已输错"+loginFalCount+"次密码，还剩"+(6-loginFalCount)+"次机会!");
						break;
					}
				}
			}
			case 2:{
				System.out.println("请重新获取验证码!");
				break;
			}
			case 3:{
				System.out.println("验证码错误!");
				break;
			}
			case 4:{
				System.out.println("密码六次输入错误，当天不可登录!");
				break;
			} 
		}
		return false;
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
