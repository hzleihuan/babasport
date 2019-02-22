package cn.itcast.web.action.shopping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.itcast.bean.book.PaymentWay;
import cn.itcast.web.formbean.shopping.ShoppingFinishForm;
/**
 * 购物结束显示页面
 */
@Controller("/shopping/finish")
public class ShoppingFinishAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forwardName = "postofficeremittance";
		ShoppingFinishForm formbean = (ShoppingFinishForm)form;
		if(PaymentWay.COD.equals(formbean.getPaymentway())){
			forwardName = "cod";
		}else if(PaymentWay.NET.equals(formbean.getPaymentway())){
			forwardName = "net";
		}else if(PaymentWay.BANKREMITTANCE.equals(formbean.getPaymentway())){
			forwardName = "bankremittance";
		}
		return mapping.findForward(forwardName);
	}
}
