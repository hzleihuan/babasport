package cn.itcast.web.action.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.itcast.service.user.BuyerService;
import cn.itcast.web.formbean.user.BuyerForm;


@Controller("/user/logon")
public class LogonAction extends Action {
	@Resource(name="buyerServiceBean") BuyerService buyerService;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BuyerForm formbean = (BuyerForm) form;
		if(formbean.getUsername()!=null && !"".equals(formbean.getUsername().trim())
				&& formbean.getPassword()!=null && !"".equals(formbean.getPassword().trim())){
			if(buyerService.checkUser(formbean.getUsername().trim(), formbean.getPassword().trim())){				
				request.getSession().setAttribute("user", buyerService.find(formbean.getUsername().trim()));
				request.setAttribute("directUrl", "/customer/shopping/deliver.do");
				return mapping.findForward("directUrl");
			}
			request.setAttribute("message", "用户名或密码有误");
		}
		return mapping.findForward("logon");
	}
}
