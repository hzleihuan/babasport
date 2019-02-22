package cn.itcast.service.product.impl;

import java.util.ArrayList;
import java.util.List;

import org.compass.core.CompassCallback;
import org.compass.core.CompassException;
import org.compass.core.CompassHits;
import org.compass.core.CompassSession;

import cn.itcast.bean.QueryResult;
import cn.itcast.bean.product.ProductInfo;

public class ProductResultCallback implements CompassCallback<QueryResult<ProductInfo>> {
	private String key;
	private int startIndex;
	private int maxResult;

	public ProductResultCallback(String key, int startIndex, int maxResult) {
		this.key = key;
		this.startIndex = startIndex;
		this.maxResult = maxResult;
	}

	public QueryResult<ProductInfo> doInCompass(CompassSession session) throws CompassException {
		List<ProductInfo> products = new ArrayList<ProductInfo>();
		CompassHits hits = session.find(key);
		int lastIndex = startIndex + maxResult - 1;
		if(lastIndex>(hits.length()-1)) lastIndex = hits.length()-1;
		for(int i=startIndex; i<=lastIndex; i++){
			ProductInfo product = (ProductInfo) hits.data(i);
			if(hits.highlighter(i).fragment("productName")!=null){//处理高亮显示
				product.setName(hits.highlighter(i).fragment("productName"));
			}
			products.add(product);
		}
		QueryResult<ProductInfo> qr = new QueryResult<ProductInfo>();
		qr.setResultlist(products);
		qr.setTotalrecord(hits.length());//设置查询到的总记录数
		return qr;
	}

}
