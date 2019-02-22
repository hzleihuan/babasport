package cn.itcast.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bean.user.Buyer;
import cn.itcast.utils.WebUtil;

public class LogonValidateFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {
		/*
		 * 判断用户是否登录(即判断session中是否存在名为user的对象),如果没有登录,将用户的浏览器重定向到/user/logon.do
		 * 
		 * WebUtil.getBuyer(HttpServletRequest request)
		 */
		HttpServletRequest request = (HttpServletRequest) req;
		Buyer buyer = WebUtil.getBuyer(request);
		if(buyer == null){
			HttpServletResponse response = (HttpServletResponse)res;
			response.sendRedirect("/user/logon.do");
			return;
		}
		filterChain.doFilter(req, res);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
