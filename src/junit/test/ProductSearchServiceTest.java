package junit.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.bean.QueryResult;
import cn.itcast.bean.product.ProductInfo;
import cn.itcast.service.product.ProductSearchService;

public class ProductSearchServiceTest {
	private static ProductSearchService productSearchService; 
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			AbstractApplicationContext cxt = new ClassPathXmlApplicationContext("beans.xml");
			productSearchService = (ProductSearchService)cxt.getBean("productSearchServiceBean");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearch() {
		QueryResult<ProductInfo> qr = productSearchService.search("µÇÂ½Í§", 0, 2);
		for(ProductInfo p : qr.getResultlist()){
			System.out.println(p.getName());
		}
		System.out.println(qr.getTotalrecord());
	}

}
