package cn.itcast.web.action.book;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.itcast.bean.book.Order;
import cn.itcast.service.book.OrderService;
import cn.itcast.utils.SiteUrl;
import cn.itcast.utils.WebUtil;
import cn.itcast.web.action.privilege.Permission;
import cn.itcast.web.formbean.book.OrderForm;

@Controller("/control/order/view")
public class OrderViewAction extends Action {
	@Resource OrderService orderService;

	@Override @Permission(model="order",privilegeValue="view")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		String username = WebUtil.getEmployee(request).getUsername();
		Order order = orderService.getLockOrder(formbean.getOrderid(), username);
		if(!order.getEmployee().equals(username)){
			request.setAttribute("message", "该订单已被"+ order.getEmployee() + "锁定");
			request.setAttribute("urladdress", SiteUrl.readUrl("control.order.list"));
			return mapping.findForward("message");	
		}
		request.setAttribute("order", order);
		return mapping.findForward("order");
	}

}
