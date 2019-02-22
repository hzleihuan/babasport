package cn.itcast.web.formbean.user;

import org.apache.commons.codec.binary.Base64;

import cn.itcast.web.formbean.BaseForm;

public class BuyerForm extends BaseForm {
	private String username;
	private String password;
	private String email;
	private String[] usernames;
	private String directUrl;
	private String realname;
	private String validateCode;
	
	
	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getDirectUrl() {
		if(directUrl!=null && !"".equals(directUrl.trim())){
			return new String(Base64.decodeBase64(directUrl.trim().getBytes()));
		}
		return null;
	}

	public void setDirectUrl(String directUrl) {
		this.directUrl = directUrl;
	}
	
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	public String[] getUsernames() {
		return usernames;
	}
	public void setUsernames(String[] usernames) {
		this.usernames = usernames;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
