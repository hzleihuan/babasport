package cn.itcast.service.book.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.TemporalType;

import org.springframework.stereotype.Service;

import cn.itcast.bean.BuyCart;
import cn.itcast.bean.BuyItem;
import cn.itcast.bean.book.DeliverWay;
import cn.itcast.bean.book.Order;
import cn.itcast.bean.book.OrderItem;
import cn.itcast.bean.book.OrderState;
import cn.itcast.bean.book.PaymentWay;
import cn.itcast.bean.product.ProductStyle;
import cn.itcast.bean.user.Buyer;
import cn.itcast.bean.user.ContactInfo;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.book.OrderService;

@Service
public class OrderServiceBean extends DaoSupport<Order> implements OrderService{
	
	public Order getLockOrder(String orderid, String employee){
		em.createQuery("update Order o set o.employee=?1 where o.orderid=?2 and o.employee is null")
			.setParameter(1, employee).setParameter(2, orderid).executeUpdate();
		em.flush();
		return this.find(orderid);
	}
	public void unLock(String orderid){
		em.createQuery("update Order o set o.employee=?1 where o.orderid=?2")
		.setParameter(1, null).setParameter(2, orderid).executeUpdate();
	}
	
	public void cancelOrder(String orderid){
		Order order = this.find(orderid);
		if(!order.getState().equals(OrderState.RECEIVED)){
			order.setState(OrderState.CANCEL);
		}
	}
	
	public void confirmOrder(String orderid){
		Order order = this.find(orderid);
		if(!order.getPaymentWay().equals(PaymentWay.COD) && order.getPaymentstate()==false){
			order.setState(OrderState.WAITPAYMENT);
		}else{
			order.setState(OrderState.ADMEASUREPRODUCT);
		}
	}
	
	public void confirmPayment(String orderid){
		Order order = this.find(orderid);
		if(order.getState().equals(OrderState.WAITPAYMENT)){
			order.setPaymentstate(true);
			order.setState(OrderState.ADMEASUREPRODUCT);
		}
	}
	
	public void turnWaitdeliver(String orderid){
		Order order = this.find(orderid);
		if(order.getState().equals(OrderState.ADMEASUREPRODUCT)){
			order.setState(OrderState.WAITDELIVER);
		}
	}
	
	public void turnDelivered(String orderid){
		Order order = this.find(orderid);
		if(order.getState().equals(OrderState.WAITDELIVER)){
			order.setState(OrderState.DELIVERED);
		}
	}
	
	public void turnReceived(String orderid){
		Order order = this.find(orderid);
		if(order.getState().equals(OrderState.DELIVERED)){
			order.setState(OrderState.RECEIVED);
		}
	}
	
	public void updateDeliverFee(Float deliverFee, String orderid){
		Order order = this.find(orderid);
		order.setDeliverFee(deliverFee);
		order.setTotalPrice(order.getProductTotalPrice()+ order.getDeliverFee());
		order.setPayablefee(order.getTotalPrice());
	}
	
	public void updatePaymentWay(PaymentWay paymentWay, String orderid){
		em.createQuery("update Order o set o.paymentWay=?1 where o.orderid=?2")
			.setParameter(1, paymentWay).setParameter(2, orderid).executeUpdate();
	}
	
	public void updateDeliverWay(DeliverWay deliverWay, String orderid){
		Order order = this.find(orderid);
		order.getOrderDeliverInfo().setDeliverWay(deliverWay);
	}
	
	public Order createOrder(BuyCart cart, String username){
		Order order = new Order();
		order.setBuyer(em.find(Buyer.class, username));
		order.setDeliverFee(cart.getDeliveFee());
		order.setNote(cart.getNote());
		order.setOrderContactInfo(cart.getContactInfo());
		order.setOrderDeliverInfo(cart.getDeliverInfo());
		order.setPayablefee(cart.getOrderTotalPrice());
		order.setTotalPrice(cart.getOrderTotalPrice());
		order.setProductTotalPrice(cart.getTotalSellPrice());
		order.setPaymentWay(cart.getPaymentWay());
		order.setState(OrderState.WAITCONFIRM);
		for(BuyItem item : cart.getItems()){
			ProductStyle style = item.getProduct().getStyles().iterator().next();
			order.addOrderItem(new OrderItem(item.getProduct().getName(),item.getProduct().getSellprice(),
					item.getProduct().getId(), style.getId(),style.getName(),item.getAmount()));
		}
		//如果用户的联系信息为空,那么将订单的联系信息填充用户的个人联系信息
		if(order.getBuyer().getContactInfo()==null){
			order.getBuyer().setContactInfo(new ContactInfo());
			if(order.getBuyer().getRealname()==null) order.getBuyer().setRealname(cart.getContactInfo().getBuyerName());
			if(order.getBuyer().getGender()==null) order.getBuyer().setGender(cart.getContactInfo().getGender());
			order.getBuyer().getContactInfo().setAddress(cart.getContactInfo().getAddress());
			order.getBuyer().getContactInfo().setMobile(cart.getContactInfo().getMobile());
			order.getBuyer().getContactInfo().setPhone(cart.getContactInfo().getTel());
			order.getBuyer().getContactInfo().setPostalcode(cart.getContactInfo().getPostalcode());
		}
		order.setOrderid(buildOrderid(order.getCreateDate()));
		super.save(order);
		return order;
	}
	/**
	 * 生成订单号,订单号的组成 两位年+两位月+两位日+两位小时+当天的订单总数 如:09051411100
	 * @param createDate
	 * @return
	 */
	private String buildOrderid(Date createDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHH");
		StringBuilder out  = new StringBuilder(dateFormat.format(createDate));
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strdate = dateFormat.format(createDate);// 2009-05-14
		Date date = createDate;
		try {
			date = dateFormat.parse(strdate);
		} catch (ParseException e) {e.printStackTrace();}
		long count = (Long)em.createQuery("select count(o) from Order o where o.createDate>=?1")
		.setParameter(1, date, TemporalType.TIMESTAMP).getSingleResult();
		out.append(count+1);
		return out.toString();
	}
}
