package cn.itcast.service.book;

import cn.itcast.bean.BuyCart;
import cn.itcast.bean.book.DeliverWay;
import cn.itcast.bean.book.Order;
import cn.itcast.bean.book.PaymentWay;
import cn.itcast.service.base.DAO;

public interface OrderService extends DAO<Order> {
	/**
	 * 解锁订单
	 * @param orderid 订单号
	 */
	public void unLock(String orderid);
	/**
	 * 加锁获取订单
	 * @param orderid 订单号
	 * @param employee 员工账号
	 * @return
	 */
	public Order getLockOrder(String orderid, String employee);
	/**
	 * 取消订单
	 * @param orderid 订单号
	 */
	public void cancelOrder(String orderid);
	/**
	 * 审核通过订单
	 * @param orderid 订单号
	 */
	public void confirmOrder(String orderid);
	/**
	 * 确认订单已支付
	 * @param orderid 订单号
	 */
	public void confirmPayment(String orderid);
	/**
	 * 把订单转为等待发货状态
	 * @param orderid 订单号
	 */
	public void turnWaitdeliver(String orderid);
	/**
	 * 把订单转为已发货状态
	 * @param orderid 订单号
	 */
	public void turnDelivered(String orderid);
	/**
	 * 把订单转为已收货状态
	 * @param orderid 订单号
	 */
	public void turnReceived(String orderid);
	/**
	 * 更新配送费用
	 * @param deliverFee 配送费
	 * @param orderid 订单号
	 */
	public void updateDeliverFee(Float deliverFee, String orderid);
	/**
	 * 创建订单
	 * @param cart 购物车
	 * @param username 顾客用户名
	 */
	public Order createOrder(BuyCart cart, String username);
	/**
	 * 更新支付方式
	 * @param paymentWay 支付方式
	 * @param orderid 订单号
	 */
	public void updatePaymentWay(PaymentWay paymentWay, String orderid);
	/**
	 * 更新配送方式
	 * @param deliverWay 配送方式
	 * @param orderid 订单号
	 */
	public void updateDeliverWay(DeliverWay deliverWay, String orderid);
}
