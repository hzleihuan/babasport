package cn.itcast.web.action.shopping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.stereotype.Controller;

import cn.itcast.bean.BuyCart;
import cn.itcast.bean.BuyItem;
import cn.itcast.bean.product.ProductInfo;
import cn.itcast.bean.product.ProductStyle;
import cn.itcast.utils.WebUtil;
import cn.itcast.web.formbean.shopping.BuyCartForm;

@Controller("/shopping/cart/manage")
public class BuyCartManageAction extends DispatchAction {
	
	/**
	 * 删除指定购物项
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BuyCart cart = (BuyCart)WebUtil.getBuyCart(request);
		BuyCartForm formbean = (BuyCartForm) form;
		if(formbean.getProductid()!=null && formbean.getProductid()>0
				&& formbean.getStyleid()!=null && formbean.getStyleid()>0){
			ProductInfo product = new ProductInfo(formbean.getProductid());
			product.addProductStyle(new ProductStyle(formbean.getStyleid()));
			BuyItem item = new BuyItem(product);
			cart.delete(item);
		}
		request.setAttribute("directUrl", "/shopping/cart.do");
		return mapping.findForward("directUrl");
	}
	/**
	 * 清空购物车
	 */
	public ActionForward deleteAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BuyCart cart = (BuyCart)WebUtil.getBuyCart(request);
		cart.deleteAll();
		request.setAttribute("directUrl", "/shopping/cart.do");
		return mapping.findForward("directUrl");
	}
	/**
	 * 修改购买数量
	 */
	public ActionForward updateAmount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		modifyBuyAmount(request);
		request.setAttribute("directUrl", "/shopping/cart.do");
		return mapping.findForward("directUrl");
	}
	
	private void modifyBuyAmount(HttpServletRequest request) {
		BuyCart cart = (BuyCart)WebUtil.getBuyCart(request);
		for(BuyItem item : cart.getItems()){
			StringBuilder sb = new StringBuilder("amount_");
			sb.append(item.getProduct().getId()).append("_");
			sb.append(item.getProduct().getStyles().iterator().next().getId());
			String amount = request.getParameter(sb.toString());
			if(amount!=null && !"".equals(amount.trim())){
				item.setAmount(new Integer(amount.trim()));
			}
		}
	}
	/**
	 * 结算
	 */
	public ActionForward settleAccounts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		modifyBuyAmount(request);
		BuyCartForm formbean = (BuyCartForm) form;
		String url = "/customer/shopping/deliver.do";
		if(formbean.getDirectUrl()!=null && !"".equals(formbean.getDirectUrl())) url = formbean.getDirectUrl();
		request.setAttribute("directUrl", url);
		return mapping.findForward("directUrl");
	}
	
	
}
