package cn.itcast.service.product.impl;

import javax.annotation.Resource;

import org.compass.core.Compass;
import org.compass.spring.CompassDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bean.QueryResult;
import cn.itcast.bean.product.ProductInfo;
import cn.itcast.service.product.ProductSearchService;

@Service @Transactional
public class ProductSearchServiceBean extends CompassDaoSupport implements ProductSearchService {
	
	@Resource
	public void setSessionFactory(Compass compass){
		super.setCompass(compass);
	}
	
	public QueryResult<ProductInfo> search(String key, int startIndex, int maxResult) {
		return this.getCompassTemplate().execute(new ProductResultCallback(key, startIndex, maxResult));
	}

}
