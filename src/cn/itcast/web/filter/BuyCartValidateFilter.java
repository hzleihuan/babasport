package cn.itcast.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.itcast.bean.BuyCart;
import cn.itcast.utils.WebUtil;
/**
 * 校验购物车中是否存在商品
 */
public class BuyCartValidateFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		BuyCart cart = WebUtil.getBuyCart(request);
		if(cart==null || cart.getItems().isEmpty()){
			request.setAttribute("message", "购物车没有商品,请购买商品后再执行该操作");
			request.setAttribute("urladdress", "/");
			//将请求转发至/WEB-INF/page/share/message.jsp
			request.getRequestDispatcher("/WEB-INF/page/share/message.jsp").forward(req, res);
			return;
		}		
		filterChain.doFilter(req, res);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
