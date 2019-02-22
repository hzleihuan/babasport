package junit.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.bean.BuyCart;
import cn.itcast.bean.BuyItem;
import cn.itcast.bean.book.DeliverWay;
import cn.itcast.bean.book.OrderContactInfo;
import cn.itcast.bean.book.OrderDeliverInfo;
import cn.itcast.bean.book.PaymentWay;
import cn.itcast.bean.product.ProductInfo;
import cn.itcast.bean.product.ProductStyle;
import cn.itcast.bean.user.Gender;
import cn.itcast.service.book.OrderService;
import cn.itcast.service.product.ProductInfoService;
import cn.itcast.service.product.ProductStyleService;
import cn.itcast.service.user.BuyerService;

public class OrderServiceTest {
	private static OrderService orderService;
	private static ProductStyleService productStyleService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			ApplicationContext cxt = new ClassPathXmlApplicationContext("beans.xml");
			orderService = (OrderService)cxt.getBean("orderServiceBean");
			productStyleService = (ProductStyleService)cxt.getBean("productStyleServiceBean");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateOrder() {
		BuyCart cart = new BuyCart();
		cart.setPaymentWay(PaymentWay.COD);
		cart.setDeliveFee(10f);
		cart.setContactInfo(new OrderContactInfo());
		cart.getContactInfo().setBuyerName("张三");
		cart.getContactInfo().setAddress("上地");
		cart.getContactInfo().setEmail("test@ss.xn");
		cart.getContactInfo().setGender(Gender.MAN);
		cart.getContactInfo().setMobile("13678909876");
		cart.getContactInfo().setPostalcode("100092");
		cart.getContactInfo().setTel("010-93834565");	
		
		cart.setDeliverInfo(new OrderDeliverInfo());
		cart.getDeliverInfo().setRecipients("赵紫阳");
		cart.getDeliverInfo().setAddress("中南海");
		cart.getDeliverInfo().setEmail("sss@sina.cn");
		cart.getDeliverInfo().setDeliverWay(DeliverWay.EMS);
		cart.getDeliverInfo().setGender(Gender.MAN);
		cart.getDeliverInfo().setMobile("13671323409");
		cart.getDeliverInfo().setPostalcode("100098");
		cart.getDeliverInfo().setTel("010-89873322");
		
		ProductStyle style = productStyleService.find(1);
		Set<ProductStyle> styles = new HashSet<ProductStyle>();
		styles.add(style);
		style.getProduct().setStyles(styles);
		cart.add(new BuyItem(style.getProduct()));
		orderService.createOrder(cart, "lihuoming");
	}

	@Test
	public void updatePaymentWay(){
		orderService.updatePaymentWay(PaymentWay.NET, "090514121");
	}
	
	@Test
	public void updateDeliverWay(){
		orderService.updateDeliverWay(DeliverWay.EXPRESSDELIVERY, "090514121");
	}
}
