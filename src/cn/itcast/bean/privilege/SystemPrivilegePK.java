package cn.itcast.bean.privilege;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SystemPrivilegePK implements Serializable{
	private static final long serialVersionUID = 2668481712963710879L;
	/* 模块名 */
	private String model;
	/* 权限值 */
	private String privilegeValue;
	
	public SystemPrivilegePK(){}
	
	public SystemPrivilegePK(String model, String privilegeValue) {
		this.model =model;
		this.privilegeValue= privilegeValue;
	}
	
	@Column(length=25, name="model")
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	@Column(length=25, name="privilegeValue")
	public String getPrivilegeValue() {
		return privilegeValue;
	}
	public void setPrivilegeValue(String privilegeValue) {
		this.privilegeValue = privilegeValue;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result
				+ ((privilegeValue == null) ? 0 : privilegeValue.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SystemPrivilegePK other = (SystemPrivilegePK) obj;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (privilegeValue == null) {
			if (other.privilegeValue != null)
				return false;
		} else if (!privilegeValue.equals(other.privilegeValue))
			return false;
		return true;
	}
	
}
