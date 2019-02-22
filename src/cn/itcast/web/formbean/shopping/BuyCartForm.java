package cn.itcast.web.formbean.shopping;

import org.apache.commons.codec.binary.Base64;

import cn.itcast.web.formbean.BaseForm;

public class BuyCartForm extends BaseForm {
	private Integer productid;
	private Integer styleid;
	private String directUrl;	
	
	public String getDirectUrl() {
		return directUrl;
	}

	public void setDirectUrl(String directUrl) {
		if(directUrl!=null && !"".equals(directUrl)){
			this.directUrl = new String(Base64.decodeBase64(directUrl.getBytes()));
		}
	}

	public void setBuyitemid(String buyitemid) {
		if(buyitemid!=null && !"".equals(buyitemid.trim())){
			String[] ids = buyitemid.split("-");
			if(ids.length==2){
				this.productid = new Integer(ids[0]);
				this.styleid = new Integer(ids[1]);
			}
		}
	}
	
	public Integer getProductid() {
		return productid;
	}
	public void setProductid(Integer productid) {
		this.productid = productid;
	}
	public Integer getStyleid() {
		return styleid;
	}
	public void setStyleid(Integer styleid) {
		this.styleid = styleid;
	}
	
}
