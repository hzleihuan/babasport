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

import cn.itcast.bean.privilege.Employee;
import cn.itcast.utils.WebUtil;
/**
 * 员工登录校验,如果员工未登录不允许进入以/control开头的路径
 */
public class EmployeeValidateFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		Employee employee = WebUtil.getEmployee(request);
		if(employee==null){
			HttpServletResponse response = (HttpServletResponse) res; 
			response.sendRedirect("/employee/logon.do");
			return ;
		}
		filterChain.doFilter(req, res);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
