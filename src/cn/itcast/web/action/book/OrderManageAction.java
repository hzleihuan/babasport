package cn.itcast.web.action.book;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.stereotype.Controller;

import cn.itcast.bean.book.Message;
import cn.itcast.bean.book.Order;
import cn.itcast.bean.book.OrderContactInfo;
import cn.itcast.bean.book.OrderDeliverInfo;
import cn.itcast.bean.book.OrderItem;
import cn.itcast.service.book.MessageService;
import cn.itcast.service.book.OrderContactInfoService;
import cn.itcast.service.book.OrderDeliverInfoService;
import cn.itcast.service.book.OrderItemService;
import cn.itcast.service.book.OrderService;
import cn.itcast.utils.SiteUrl;
import cn.itcast.utils.WebUtil;
import cn.itcast.web.action.privilege.Permission;
import cn.itcast.web.formbean.book.OrderForm;

@Controller("/control/order/manage")
public class OrderManageAction extends DispatchAction {
	@Resource OrderContactInfoService contactInfoService;
	@Resource OrderDeliverInfoService deliverInfoService;
	@Resource OrderService orderService;
	@Resource OrderItemService itemService;
	@Resource MessageService messageService;
	
	/**
	 * 员工订单留言界面
	 */
	public ActionForward addMessageUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("addMessage");
	}
	/**
	 * 添加员工订单留言
	 */
	public ActionForward addMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		Message msg = new Message();
		msg.setContent(formbean.getMessage());
		msg.setOrder(new Order(formbean.getOrderid()));
		msg.setUsername(WebUtil.getEmployee(request).getUsername());
		messageService.save(msg);
		
		request.setAttribute("message", "留言添加成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.order.view")+"?orderid="+ formbean.getOrderid());
		return mapping.findForward("message");
	}
	
	
	public ActionForward employeeUnlockOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		orderService.unLock(formbean.getOrderid());
		request.setAttribute("directUrl", SiteUrl.readUrl("control.order.list"));
		return mapping.findForward("directUrl");
	}
	/**
	 * 订单订购者联系信息修改界面
	 */
	@Permission(model="order",privilegeValue="modifyContactInfo")
	public ActionForward modifyContactInfoUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		OrderContactInfo contact = contactInfoService.find(formbean.getContactid());
		formbean.setBuyer(contact.getBuyerName());
		formbean.setBuyer_address(contact.getAddress());
		formbean.setBuyer_gender(contact.getGender());
		formbean.setBuyer_mobile(contact.getMobile());
		formbean.setBuyer_postalcode(contact.getPostalcode());
		formbean.setBuyer_tel(contact.getTel());
		formbean.setOrderid(contact.getOrder().getOrderid());
		
		return mapping.findForward("contactInfo");
	}
	/**
	 * 修改订单订购者联系信息
	 */
	@Permission(model="order",privilegeValue="modifyContactInfo")
	public ActionForward modifyContactInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		//1>获取订单联系人信息对象,然后把请求参数赋给他,然后调用业务bean的update()方法进行更新
		OrderContactInfo contact = contactInfoService.find(formbean.getContactid());
		contact.setBuyerName(formbean.getBuyer());
		contact.setAddress(formbean.getBuyer_address());
		contact.setGender(formbean.getBuyer_gender());
		contact.setMobile(formbean.getBuyer_mobile());
		contact.setPostalcode(formbean.getBuyer_postalcode());
		contact.setTel(formbean.getBuyer_tel());
		this.contactInfoService.update(contact);
		
		request.setAttribute("message", "修改成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.order.view")+"?orderid="+ contact.getOrder().getOrderid());
		return mapping.findForward("message");
	}
	
	/**
	 * 收货人联系信息修改界面
	 */
	@Permission(model="order",privilegeValue="modifyDeliverInfo")
	public ActionForward modifyDeliverInfoUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		OrderDeliverInfo info = this.deliverInfoService.find(formbean.getDeliverid());
		formbean.setAddress(info.getAddress());
		formbean.setEmail(info.getEmail());
		formbean.setGender(info.getGender());
		formbean.setMobile(info.getMobile());
		formbean.setTel(info.getTel());
		formbean.setRecipients(info.getRecipients());
		formbean.setPostalcode(info.getPostalcode());
		//1>根据请求参数deliverid调用业务bean deliverInfoService查找收货人信息对象.然后把收货人信息对象的属性值赋给formbean.
		return mapping.findForward("deliverInfo");
	}
	/**
	 * 修改订单收货人联系信息
	 */
	@Permission(model="order",privilegeValue="modifyDeliverInfo")
	public ActionForward modifyDeliverInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		OrderDeliverInfo info = this.deliverInfoService.find(formbean.getDeliverid());
		info.setAddress(formbean.getAddress());
		info.setEmail(formbean.getEmail());
		info.setGender(formbean.getGender());
		info.setMobile(formbean.getMobile());
		info.setTel(formbean.getTel());
		info.setRecipients(formbean.getRecipients());
		info.setPostalcode(formbean.getPostalcode());
		this.deliverInfoService.update(info);
		
		request.setAttribute("message", "修改成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.order.view")+"?orderid="+ info.getOrder().getOrderid());
		return mapping.findForward("message");
	}
	/**
	 * 修改订单支付方式修改界面
	 */
	@Permission(model="order",privilegeValue="modifyPaymentWay")
	public ActionForward modifyPaymentWayUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		Order order = orderService.find(formbean.getOrderid());
		formbean.setPaymentWay(order.getPaymentWay());
		return mapping.findForward("editpaymentWay");
	}
	/**
	 * 修改订单支付方式
	 */
	@Permission(model="order",privilegeValue="modifyPaymentWay")
	public ActionForward modifyPaymentWay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		orderService.updatePaymentWay(formbean.getPaymentWay() ,formbean.getOrderid());		
		request.setAttribute("message", "支付方式修改成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.order.view")+"?orderid="+ formbean.getOrderid());
		return mapping.findForward("message");
	}
	
	/**
	 * 订单配送方式修改界面
	 */
	@Permission(model="order",privilegeValue="modifyDeliverWay")
	public ActionForward modifyDeliverWayUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		Order order = orderService.find(formbean.getOrderid());
		formbean.setDeliverWay(order.getOrderDeliverInfo().getDeliverWay());
		return mapping.findForward("modifyDeliverWay");
	}
	/**
	 * 修改订单配送方式
	 */
	@Permission(model="order",privilegeValue="modifyDeliverWay")
	public ActionForward modifyDeliverWay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		orderService.updateDeliverWay(formbean.getDeliverWay() ,formbean.getOrderid());		
		request.setAttribute("message", "配送方式修改成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.order.view")+"?orderid="+ formbean.getOrderid());
		return mapping.findForward("message");
	}
	/**
	 * 修改产品的购买数量界面
	 */
	@Permission(model="order",privilegeValue="modifyProductAmount")
	public ActionForward modifyProductAmountUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		OrderItem item = itemService.find(formbean.getOrderitemid());
		formbean.setAmount(item.getAmount());
		formbean.setOrderid(item.getOrder().getOrderid());
		return mapping.findForward("modifyAmount");
	}
	/**
	 * 修改产品的购买数量
	 */
	@Permission(model="order",privilegeValue="modifyProductAmount")
	public ActionForward modifyProductAmount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		itemService.updateAmount(formbean.getAmount(), formbean.getOrderitemid());
		request.setAttribute("message", "购买数量修改成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.order.view")+"?orderid="+ formbean.getOrderid());
		return mapping.findForward("message");
	}
	/**
	 * 删除订单项
	 */
	@Permission(model="order",privilegeValue="deleteOrderItem")
	public ActionForward deleteOrderItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		itemService.delete((Serializable)formbean.getOrderitemid());
		request.setAttribute("message", "删除订单项成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.order.view")+"?orderid="+ formbean.getOrderid());
		return mapping.findForward("message");
	}
	/**
	 * 修改配送费
	 */
	@Permission(model="order",privilegeValue="modifyDeliverFee")
	public ActionForward modifyDeliverFeeUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		Order order = orderService.find(formbean.getOrderid());
		formbean.setFee(order.getDeliverFee());
		return mapping.findForward("modifyDeliverFee");
	}
	/**
	 * 修改配送费
	 */
	@Permission(model="order",privilegeValue="modifyDeliverFee")
	public ActionForward modifyDeliverFee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		orderService.updateDeliverFee(formbean.getFee(), formbean.getOrderid());
		
		request.setAttribute("message", "修改配送费成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.order.view")+"?orderid="+ formbean.getOrderid());
		return mapping.findForward("message");
	}
	/**
	 * 打印发货单
	 */
	public ActionForward printOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		Order order = orderService.find(formbean.getOrderid());		
		request.setAttribute("order", order);
		return mapping.findForward("printOrder");
	}
	
	/**
	 * 取消订单
	 */
	@Permission(model="order",privilegeValue="cancelOrder")
	public ActionForward cancelOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		orderService.cancelOrder(formbean.getOrderid());		
		request.setAttribute("message", "订单已成功取消");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.order.list"));
		return mapping.findForward("message");
	}
	/**
	 * 审核通过订单
	 */
	@Permission(model="order",privilegeValue="confirmOrder")
	public ActionForward confirmOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		orderService.confirmOrder(formbean.getOrderid());		
		request.setAttribute("message", "订单已审核通过");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.order.list"));
		return mapping.findForward("message");
	}
	/**
	 * 确认订单已支付
	 */
	@Permission(model="order",privilegeValue="confirmPayment")
	public ActionForward confirmPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		orderService.confirmPayment(formbean.getOrderid());		
		request.setAttribute("message", "订单已确认支付");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.order.view")+"?orderid="+ formbean.getOrderid());
		return mapping.findForward("message");
	}
	/**
	 * 把订单转为等待发货状态
	 */
	@Permission(model="order",privilegeValue="turnWaitdeliver")
	public ActionForward turnWaitdeliver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		orderService.turnWaitdeliver(formbean.getOrderid());		
		request.setAttribute("message", "订单已转为等待发货状态");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.order.list"));
		return mapping.findForward("message");
	}
	/**
	 * 把订单转为已发货状态
	 */
	@Permission(model="order",privilegeValue="turnDelivered")
	public ActionForward turnDelivered(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		orderService.turnDelivered(formbean.getOrderid());		
		request.setAttribute("message", "订单转为已发货状态");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.order.list"));
		return mapping.findForward("message");
	}
	/**
	 * 把订单转为已收货状态
	 */
	@Permission(model="order",privilegeValue="turnReceived")
	public ActionForward turnReceived(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		orderService.turnReceived(formbean.getOrderid());		
		request.setAttribute("message", "订单转为已收货状态");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.order.list"));
		return mapping.findForward("message");
	}
	
}
