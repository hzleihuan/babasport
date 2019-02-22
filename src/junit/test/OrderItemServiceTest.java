package junit.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.service.book.OrderItemService;
import cn.itcast.service.book.OrderService;
import cn.itcast.service.product.ProductStyleService;

public class OrderItemServiceTest {
	private static OrderItemService itemService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			ApplicationContext cxt = new ClassPathXmlApplicationContext("beans.xml");
			itemService = (OrderItemService)cxt.getBean("orderItemServiceBean");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateAmount() {
		//itemService.updateAmount(2, 1);
	}
	@Test
	public void delete() {
		itemService.delete(7);
	}
}
