package cn.itcast.web.action.shopping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.stereotype.Controller;

import cn.itcast.bean.BuyCart;
import cn.itcast.bean.book.DeliverWay;
import cn.itcast.bean.book.Order;
import cn.itcast.bean.book.OrderContactInfo;
import cn.itcast.bean.book.OrderDeliverInfo;
import cn.itcast.bean.user.Buyer;
import cn.itcast.service.book.OrderService;
import cn.itcast.utils.WebUtil;
import cn.itcast.web.formbean.shopping.DeliverForm;

@Controller("/customer/shopping/manage")
public class ShoppingManageAction extends DispatchAction {
	@Resource OrderService orderService;

	/**
	 * 保存收货人与购买者信息
	 */
	public ActionForward saveDeliverInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DeliverForm formbean = (DeliverForm)form;
		BuyCart cart = WebUtil.getBuyCart(request);
		if(cart.getDeliverInfo()==null) cart.setDeliverInfo(new OrderDeliverInfo());
		cart.getDeliverInfo().setRecipients(formbean.getRecipients());
		cart.getDeliverInfo().setAddress(formbean.getAddress());
		cart.getDeliverInfo().setEmail(formbean.getEmail());
		cart.getDeliverInfo().setGender(formbean.getGender());
		cart.getDeliverInfo().setMobile(formbean.getMobile());
		cart.getDeliverInfo().setPostalcode(formbean.getPostalcode());
		cart.getDeliverInfo().setTel(formbean.getTel());
		cart.setBuyerIsrecipients(formbean.getBuyerIsrecipients());
		//.....
		if(cart.getContactInfo()==null) cart.setContactInfo(new OrderContactInfo());
		if(cart.getBuyerIsrecipients()){
			cart.getContactInfo().setBuyerName(formbean.getRecipients());
			cart.getContactInfo().setAddress(formbean.getAddress());
			cart.getContactInfo().setEmail(formbean.getEmail());
			cart.getContactInfo().setGender(formbean.getGender());
			cart.getContactInfo().setMobile(formbean.getMobile());
			cart.getContactInfo().setPostalcode(formbean.getPostalcode());
			cart.getContactInfo().setTel(formbean.getTel());		
		}else{
			Buyer buyer = WebUtil.getBuyer(request);
			cart.getContactInfo().setBuyerName(formbean.getBuyer());
			cart.getContactInfo().setAddress(formbean.getBuyer_address());
			cart.getContactInfo().setEmail(buyer.getEmail());
			cart.getContactInfo().setGender(formbean.getBuyer_gender());
			cart.getContactInfo().setMobile(formbean.getBuyer_mobile());
			cart.getContactInfo().setPostalcode(formbean.getBuyer_postalcode());
			cart.getContactInfo().setTel(formbean.getBuyer_tel());
		}
		String url = "/customer/shopping/paymentway.do";
		if(formbean.getDirectUrl()!=null && !"".equals(formbean.getDirectUrl())) url = formbean.getDirectUrl();
		request.setAttribute("directUrl", url);
		return mapping.findForward("directUrl");
	}

	/**
	 * 保存配送方式与支付方式
	 */
	public ActionForward savePaymentway(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DeliverForm formbean = (DeliverForm)form;
		BuyCart cart = WebUtil.getBuyCart(request);
		cart.getDeliverInfo().setDeliverWay(formbean.getDeliverway());
		cart.setPaymentWay(formbean.getPaymentway());
		if(DeliverWay.EXPRESSDELIVERY.equals(formbean.getDeliverway()) 
				|| DeliverWay.EXIGENCEEXPRESSDELIVERY.equals(formbean.getDeliverway())){
			if("other".equals(formbean.getRequirement())){
				cart.getDeliverInfo().setRequirement(formbean.getDelivernote());
			}else{
				cart.getDeliverInfo().setRequirement(formbean.getRequirement());
			}
		}else{
			cart.getDeliverInfo().setRequirement(null);
		}
		request.setAttribute("directUrl", "/customer/shopping/orderconfirm.do");
		return mapping.findForward("directUrl");
	}
	
	/**
	 * 保存订单
	 */
	public ActionForward saveorder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DeliverForm formbean = (DeliverForm)form;
		BuyCart cart = WebUtil.getBuyCart(request);
		cart.setNote(formbean.getNote());
		Buyer buyer = WebUtil.getBuyer(request);
		Order order = orderService.createOrder(cart, buyer.getUsername());
		WebUtil.deleteBuyCart(request);
		request.setAttribute("directUrl", "/shopping/finish.do?orderid="+ 
				order.getOrderid()+ "&paymentway="+ order.getPaymentWay()+ "&payablefee="+ order.getPayablefee());
		return mapping.findForward("directUrl");
	}
	
}
