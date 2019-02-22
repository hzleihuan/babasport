package cn.itcast.web.action.shopping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

@Controller("/customer/shopping/orderconfirm")
public class OrderConfirmAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String url = "/customer/shopping/orderconfirm.do";//原文
		String code = new String(Base64.encodeBase64(url.getBytes()));//编码后的字符串
		request.setAttribute("directUrl", code);
		return mapping.findForward("confirm");
	}

}
