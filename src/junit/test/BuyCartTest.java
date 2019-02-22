package junit.test;

import org.junit.Test;

import cn.itcast.bean.BuyItem;
import cn.itcast.bean.product.ProductInfo;
import cn.itcast.bean.product.ProductStyle;

public class BuyCartTest {
	
	@Test
	public void testxx(){
		ProductInfo p = new ProductInfo(1);
		p.addProductStyle(new ProductStyle(2));
		BuyItem item1 = new BuyItem(p);
		ProductInfo p2 = new ProductInfo(1);
		p2.addProductStyle(new ProductStyle(2));
		BuyItem item2 = new BuyItem(p2);
		
		System.out.println(item1.equals(item2));
	}
}
