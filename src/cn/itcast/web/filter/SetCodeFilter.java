package cn.itcast.web.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import cn.itcast.bean.book.DeliverWay;
import cn.itcast.bean.book.OrderState;
import cn.itcast.bean.book.PaymentWay;
import cn.itcast.bean.privilege.SystemPrivilegePK;
import cn.itcast.bean.product.Sex;
import cn.itcast.bean.user.Gender;
import cn.itcast.web.formdatetype.converter.DateConverter;
import cn.itcast.web.formdatetype.converter.DeliverWayConverter;
import cn.itcast.web.formdatetype.converter.GenderConverter;
import cn.itcast.web.formdatetype.converter.OrderStateConverter;
import cn.itcast.web.formdatetype.converter.PaymentWayConverter;
import cn.itcast.web.formdatetype.converter.SexConverter;
import cn.itcast.web.formdatetype.converter.SystemPrivilegePKConverter;


public class SetCodeFilter implements Filter {

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterchain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		req.setCharacterEncoding("UTF-8");
		filterchain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		ConvertUtils.register(new DateConverter(), Date.class);
		ConvertUtils.register(new SexConverter(), Sex.class);
		ConvertUtils.register(new GenderConverter(), Gender.class);
		ConvertUtils.register(new PaymentWayConverter(), PaymentWay.class);
		ConvertUtils.register(new DeliverWayConverter(), DeliverWay.class);
		ConvertUtils.register(new OrderStateConverter(), OrderState.class);
		ConvertUtils.register(new SystemPrivilegePKConverter(), SystemPrivilegePK.class);
		try{
			Properties prop = new Properties();
			prop.put("runtime.log", config.getServletContext().getRealPath("/WEB-INF/log/velocity.log"));
			prop.put("file.resource.loader.path", config.getServletContext().getRealPath("/WEB-INF/vm"));
			prop.put("input.encoding", "UTF-8");
			prop.put("output.encoding", "UTF-8");
			Velocity.init(prop);
		}catch( Exception e ){
			e.printStackTrace();
		}
	}

}
