package com.hczd.download.authority.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hczd.download.authority.constant.HZ_LoginConstant;
import com.hczd.download.base.constant.HZ_ProjectConstant;




/**
 * 安全拦截
 * @author linjian 
 * @version 2.0
 * @create_date 2013-5-28 上午10:46:04
 */
public class HZ_AuthorityFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		Object obj = request.getSession().getAttribute(HZ_LoginConstant.SESSION_LOGIN);
		if(path.contains("authority/login/toLogin.htm")||path.contains("login.jsp") || path.contains("authority/login/login.htm")  || obj!= null){
			//拦截用户是否登录
			chain.doFilter(req, resp);
		}else{
			response.sendRedirect("/hczd-download/authority/login/toLogin.htm");
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
