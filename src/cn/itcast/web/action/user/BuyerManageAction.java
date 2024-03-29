package cn.itcast.web.action.user;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.stereotype.Controller;

import cn.itcast.service.user.BuyerService;
import cn.itcast.utils.SiteUrl;
import cn.itcast.web.action.privilege.Permission;
import cn.itcast.web.formbean.user.BuyerForm;

/**
 * 顾客管理
 *
 */
@Controller("/control/user/manage")
public class BuyerManageAction extends DispatchAction {
	@Resource(name="buyerServiceBean") BuyerService buyerService;
	/**
	 * 启用
	 */
	@Permission(model="employee", privilegeValue="enable")
	public ActionForward enable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BuyerForm formbean = (BuyerForm) form;
		buyerService.enable((Serializable[])formbean.getUsernames());
		request.setAttribute("message", "启用成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.user.list"));
		return mapping.findForward("message");
	}
	/**
	 * 禁用
	 */
	@Permission(model="employee", privilegeValue="delete")
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		BuyerForm formbean = (BuyerForm) form;
		buyerService.delete((Serializable[])formbean.getUsernames());
		request.setAttribute("message", "禁用成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.user.list"));
		return mapping.findForward("message");
	}
}
