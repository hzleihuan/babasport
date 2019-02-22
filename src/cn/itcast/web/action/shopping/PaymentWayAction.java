package cn.itcast.web.action.shopping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.itcast.bean.BuyCart;
import cn.itcast.bean.book.DeliverWay;
import cn.itcast.bean.book.PaymentWay;
import cn.itcast.utils.WebUtil;
import cn.itcast.web.formbean.shopping.DeliverForm;

@Controller("/customer/shopping/paymentway")
public class PaymentWayAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BuyCart cart = WebUtil.getBuyCart(request);
		if(cart.getDeliverInfo()==null) {
			request.setAttribute("directUrl", "/customer/shopping/deliver.do");
			return mapping.findForward("directUrl");
		}
		DeliverForm formbean = (DeliverForm) form;
		formbean.setPaymentway(PaymentWay.NET);
		formbean.setDeliverway(DeliverWay.EXPRESSDELIVERY);
		if(cart.getDeliverInfo().getDeliverWay()!=null) formbean.setDeliverway(cart.getDeliverInfo().getDeliverWay());
		if(cart.getPaymentWay()!=null) formbean.setPaymentway(cart.getPaymentWay());
		formbean.setRequirement(cart.getDeliverInfo().getRequirement());
		formbean.setDelivernote(cart.getDeliverInfo().getRequirement());
		return mapping.findForward("paymentway");
	}

}
