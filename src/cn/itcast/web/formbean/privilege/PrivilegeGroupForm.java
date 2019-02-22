package cn.itcast.web.formbean.privilege;

import cn.itcast.bean.privilege.SystemPrivilegePK;
import cn.itcast.web.formbean.BaseForm;

public class PrivilegeGroupForm extends BaseForm {
	private Integer groupid;
	private String name;
	private SystemPrivilegePK[] privileges;
	
	public Integer getGroupid() {
		return groupid;
	}
	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SystemPrivilegePK[] getPrivileges() {
		return privileges;
	}
	public void setPrivileges(SystemPrivilegePK[] privileges) {
		this.privileges = privileges;
	}
	
}
