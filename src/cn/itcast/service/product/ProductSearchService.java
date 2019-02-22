package cn.itcast.service.product;

import cn.itcast.bean.QueryResult;
import cn.itcast.bean.product.ProductInfo;
/**
 * 产品全文搜索
 *
 */
public interface ProductSearchService {
	public QueryResult<ProductInfo> search(String key, int startIndex, int maxResult);
}
