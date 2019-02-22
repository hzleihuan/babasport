package cn.itcast.bean;

import cn.itcast.bean.product.ProductInfo;

/**
 * 购物项
 */
public class BuyItem {
	private ProductInfo product;
	private Integer amount = 1;
	
	public BuyItem(){}
	
	public BuyItem(ProductInfo product) {
		this.product = product;
	}
	public ProductInfo getProduct() {
		return product;
	}
	public void setProduct(ProductInfo product) {
		this.product = product;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	@Override
	public int hashCode() {
		String result = product.getId().toString();
		if(!product.getStyles().isEmpty()) result +="-"+ product.getStyles().iterator().next().getId();
		return result.hashCode();
	}
	//购物车里的产品,最多只可能存在一个样式
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BuyItem other = (BuyItem) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		
		if(product.getStyles().size()!=other.product.getStyles().size()){
			return false;
		}
		Integer style = product.getStyles().iterator().next().getId();
		Integer othersytle = other.product.getStyles().iterator().next().getId();
		if(!style.equals(othersytle)) return false;
		return true;
	}
	
}
