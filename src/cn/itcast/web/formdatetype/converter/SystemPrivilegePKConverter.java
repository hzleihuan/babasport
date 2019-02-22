package cn.itcast.web.formdatetype.converter;

import org.apache.commons.beanutils.Converter;

import cn.itcast.bean.privilege.SystemPrivilegePK;

public class SystemPrivilegePKConverter implements Converter{

	@SuppressWarnings("unchecked")
	public Object convert(Class clazz, Object value) {
		if(value==null || "".equals((String)value)) return null;
		if(value instanceof SystemPrivilegePK) return value;
		try{
			String param =(String) value;
			String[] arr = param.split(",");
			if(arr.length==2){
				return new SystemPrivilegePK(arr[0], arr[1]);
			}
		}catch (Exception e) {}
		return null;
	}


}
