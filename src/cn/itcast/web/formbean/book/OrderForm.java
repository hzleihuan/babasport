package cn.itcast.web.formbean.book;

import cn.itcast.bean.book.DeliverWay;
import cn.itcast.bean.book.OrderState;
import cn.itcast.bean.book.PaymentWay;
import cn.itcast.bean.user.Gender;
import cn.itcast.web.formbean.BaseForm;

public class OrderForm extends BaseForm {
	private OrderState state;
	private String orderid;
	private String username;
	private String buyer;
	private String recipients;
	private Integer contactid;
	private Gender buyer_gender;
	private String buyer_address;
	private String buyer_postalcode;
	private String buyer_tel;
	private String buyer_mobile;
	private Integer deliverid;
	private Gender gender;
	private String address;
	private String postalcode;
	private String email;
	private String tel;
	private String mobile;
	private PaymentWay paymentWay;
	private DeliverWay deliverWay;
	
	private Integer orderitemid;
	private Integer amount;
	private Float fee;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Float getFee() {
		return fee;
	}

	public void setFee(Float fee) {
		this.fee = fee;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getOrderitemid() {
		return orderitemid;
	}

	public void setOrderitemid(Integer orderitemid) {
		this.orderitemid = orderitemid;
	}

	public DeliverWay getDeliverWay() {
		return deliverWay;
	}

	public void setDeliverWay(DeliverWay deliverWay) {
		this.deliverWay = deliverWay;
	}

	public PaymentWay getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(PaymentWay paymentWay) {
		this.paymentWay = paymentWay;
	}

	public Integer getDeliverid() {
		return deliverid;
	}

	public void setDeliverid(Integer deliverid) {
		this.deliverid = deliverid;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Gender getBuyer_gender() {
		return buyer_gender;
	}

	public void setBuyer_gender(Gender buyer_gender) {
		this.buyer_gender = buyer_gender;
	}

	public String getBuyer_address() {
		return buyer_address;
	}

	public void setBuyer_address(String buyer_address) {
		this.buyer_address = buyer_address;
	}

	public String getBuyer_postalcode() {
		return buyer_postalcode;
	}

	public void setBuyer_postalcode(String buyer_postalcode) {
		this.buyer_postalcode = buyer_postalcode;
	}

	public String getBuyer_tel() {
		return buyer_tel;
	}

	public void setBuyer_tel(String buyer_tel) {
		this.buyer_tel = buyer_tel;
	}

	public String getBuyer_mobile() {
		return buyer_mobile;
	}

	public void setBuyer_mobile(String buyer_mobile) {
		this.buyer_mobile = buyer_mobile;
	}

	public Integer getContactid() {
		return contactid;
	}

	public void setContactid(Integer contactid) {
		this.contactid = contactid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}
	
}
