package cn.itcast.service.book;

import cn.itcast.bean.book.OrderItem;
import cn.itcast.service.base.DAO;

public interface OrderItemService extends DAO<OrderItem> {
	/**
	 * 修改订单项的购买数量
	 * @param amount 购买数量
	 * @param itemid 订单项ID
	 */
	public void updateAmount(Integer amount, Integer itemid);
}
