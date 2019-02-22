package cn.itcast.web.formdatetype.converter;

import org.apache.commons.beanutils.Converter;

import cn.itcast.bean.book.OrderState;

public class OrderStateConverter implements Converter{

	@SuppressWarnings("unchecked")
	public Object convert(Class clazz, Object value) {
		if(value==null || "".equals((String)value)) return null;
		if(value instanceof OrderState) return value;
		try{
			return OrderState.valueOf((String) value);
		}catch (Exception e) {}
		return null;
	}

}
