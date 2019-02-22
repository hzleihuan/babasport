package cn.itcast.service.book.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import cn.itcast.bean.book.Order;
import cn.itcast.bean.book.OrderItem;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.book.OrderItemService;

@Service
public class OrderItemServiceBean extends DaoSupport<OrderItem> implements OrderItemService {
	
	@Override
	public void delete(Serializable... entityids) {
		for(Serializable id : entityids){
			OrderItem item = this.find(id);
			item.getOrder().getItems().remove(item);
			em.remove(item);
			updateFee(item.getOrder());
		}
	}

	public void updateAmount(Integer amount, Integer itemid){
		OrderItem item = this.find(itemid);
		item.setAmount(amount);
		updateFee(item.getOrder());
	}

	public static void updateFee(Order order) {
		float productTotalPrice = 0f;
		for(OrderItem item : order.getItems()){
			productTotalPrice += item.getProductPrice()* item.getAmount();
		}
		order.setProductTotalPrice(productTotalPrice);
		order.setTotalPrice(order.getProductTotalPrice()+ order.getDeliverFee());
		order.setPayablefee(order.getTotalPrice());
	}
}
