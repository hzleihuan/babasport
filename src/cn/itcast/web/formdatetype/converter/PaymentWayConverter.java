package cn.itcast.web.formdatetype.converter;

import org.apache.commons.beanutils.Converter;

import cn.itcast.bean.book.PaymentWay;

public class PaymentWayConverter implements Converter{

	@SuppressWarnings("unchecked")
	public Object convert(Class clazz, Object value) {
		if(value==null || "".equals((String)value)) return null;
		if(value instanceof PaymentWay) return value;
		try{
			return PaymentWay.valueOf((String) value);
		}catch (Exception e) {}
		return null;
	}

}
