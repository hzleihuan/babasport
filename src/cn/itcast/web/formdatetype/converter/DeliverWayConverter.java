package cn.itcast.web.formdatetype.converter;

import org.apache.commons.beanutils.Converter;

import cn.itcast.bean.book.DeliverWay;

public class DeliverWayConverter implements Converter{

	@SuppressWarnings("unchecked")
	public Object convert(Class clazz, Object value) {
		if(value==null || "".equals((String)value)) return null;
		if(value instanceof DeliverWay) return value;
		try{
			return DeliverWay.valueOf((String) value);
		}catch (Exception e) {}
		return null;
	}
}
