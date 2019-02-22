package cn.itcast.bean;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.bean.book.OrderContactInfo;
import cn.itcast.bean.book.OrderDeliverInfo;
import cn.itcast.bean.book.PaymentWay;

/**
 * 购物车
 */
public class BuyCart {
	/* 购物项 */
	private List<BuyItem> items = new ArrayList<BuyItem>();
	/* 配送信息 */
	private OrderDeliverInfo deliverInfo;
	/* 购买者联系信息 */
	private OrderContactInfo contactInfo;
	/* 支付方式 */
	private PaymentWay paymentWay;
	/* 购买者与收货人是否相同 */
	private Boolean buyerIsrecipients;
	/* 配送费 */
	private float deliveFee = 10f;
	/* 附言 */
	private String note;
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public float getDeliveFee() {
		return deliveFee;
	}

	public void setDeliveFee(float deliveFee) {
		this.deliveFee = deliveFee;
	}

	public PaymentWay getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(PaymentWay paymentWay) {
		this.paymentWay = paymentWay;
	}

	public Boolean getBuyerIsrecipients() {
		return buyerIsrecipients;
	}

	public void setBuyerIsrecipients(Boolean buyerIsrecipients) {
		this.buyerIsrecipients = buyerIsrecipients;
	}

	public OrderContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(OrderContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public OrderDeliverInfo getDeliverInfo() {
		return deliverInfo;
	}

	public void setDeliverInfo(OrderDeliverInfo deliverInfo) {
		this.deliverInfo = deliverInfo;
	}

	public List<BuyItem> getItems() {
		return items;
	}

	/**
	 * 添加购物项
	 * @param item 购物项
	 */
	public void add(BuyItem item){
		if(this.items.contains(item)){
			for(BuyItem it : this.items){
				if(it.equals(item)){
					it.setAmount(it.getAmount()+1);
					break;
				}
			}
		}else{
			this.items.add(item);
		}
	}
	/**
	 * 删除指定购物项
	 * @param item 购物项
	 */
	public void delete(BuyItem item){
		if(this.items.contains(item)) this.items.remove(item);
	}
	/**
	 * 清空购物项
	 */
	public void deleteAll(){
		this.items.clear();
	}
	
	/**
	 * 计算商品总销售价
	 * @return
	 */
	public float getTotalSellPrice(){
		float totalprice = 0F;
		for(BuyItem item : this.items){
			totalprice += item.getProduct().getSellprice() * item.getAmount();
		}
		return totalprice;
	}
	/**
	 * 计算商品总市场价
	 * @return
	 */
	public float getTotalMarketPrice(){
		float totalprice = 0F;
		for(BuyItem item : this.items){
			totalprice += item.getProduct().getMarketprice() * item.getAmount();
		}
		return totalprice;
	}
	/**
	 * 计算总节省金额
	 * @return
	 */
	public float getTotalSavedPrice(){
		return this.getTotalMarketPrice() - this.getTotalSellPrice();
	}
	/**
	 * 计算订单的总费用
	 * @return
	 */
	public float getOrderTotalPrice(){
		return this.getTotalSellPrice()+ this.deliveFee;
	}
}
