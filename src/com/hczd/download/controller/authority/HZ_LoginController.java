package com.hczd.download.controller.authority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hczd.download.authority.constant.HZ_LoginConstant;

/**
 * 登录拦截器
 * @author linyb
 * @version 1.0 
 * @create_date 2014-6-13上午9:56:27
 */
@Controller
@RequestMapping(value = "/authority/login/*")
public class HZ_LoginController {
	/**
	 * 登录页
	 * @author linyb
	 * @create_date 2014-6-13上午10:03:57
	 */
	@RequestMapping(value="toLogin.htm")
	public String toLogin(){
		return "authority/login";
	}
	/**
	 * 登录
	 * @author linyb
	 * @create_date 2014-6-13上午10:06:28
	 * @param username 用户名
	 * @param password 密码
	 * @return 
	 */
	@RequestMapping(value="login.htm")
	public String login(String username,String password,HttpSession session,HttpServletRequest request){
		if(username.equals("365365") && password.equals("365365")){
			session.setAttribute(HZ_LoginConstant.SESSION_LOGIN, "success");
			return "authority/index";
		}else{
			return "authority/error";
		}
	}
}
