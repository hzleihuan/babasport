package cn.itcast.web.action.shopping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.itcast.bean.BuyCart;
import cn.itcast.bean.user.Buyer;
import cn.itcast.bean.user.Gender;
import cn.itcast.utils.WebUtil;
import cn.itcast.web.formbean.shopping.DeliverForm;

@Controller("/customer/shopping/deliver")
public class DeliverInfoAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DeliverForm formbean = (DeliverForm) form;
		formbean.setGender(Gender.MAN);
		formbean.setBuyer_gender(Gender.MAN);
		formbean.setBuyerIsrecipients(true);
		BuyCart cart = WebUtil.getBuyCart(request);
		if(cart.getDeliverInfo()!=null){
			formbean.setRecipients(cart.getDeliverInfo().getRecipients());
			formbean.setAddress(cart.getDeliverInfo().getAddress());
			formbean.setEmail(cart.getDeliverInfo().getEmail());
			formbean.setGender(cart.getDeliverInfo().getGender());
			formbean.setMobile(cart.getDeliverInfo().getMobile());
			formbean.setPostalcode(cart.getDeliverInfo().getPostalcode());
			formbean.setTel(cart.getDeliverInfo().getTel());
			formbean.setBuyerIsrecipients(cart.getBuyerIsrecipients());
		}else{
			//当购物车里没有购买者的联系信息时,将用户的个人联系信息作为默认数据显示在收货人信息输入字段上
			Buyer buyer = WebUtil.getBuyer(request);
			if(buyer.getContactInfo()!=null){
				formbean.setRecipients(buyer.getRealname());
				formbean.setAddress(buyer.getContactInfo().getAddress());
				formbean.setEmail(buyer.getEmail());
				formbean.setGender(buyer.getGender());
				formbean.setMobile(buyer.getContactInfo().getMobile());
				formbean.setPostalcode(buyer.getContactInfo().getPostalcode());
				formbean.setTel(buyer.getContactInfo().getPhone());
				formbean.setBuyerIsrecipients(true);
			}
		}
		if(cart.getContactInfo()!=null){
			formbean.setBuyer(cart.getContactInfo().getBuyerName());
			formbean.setBuyer_address(cart.getContactInfo().getAddress());
			formbean.setBuyer_gender(cart.getContactInfo().getGender());
			formbean.setBuyer_mobile(cart.getContactInfo().getMobile());
			formbean.setBuyer_postalcode(cart.getContactInfo().getPostalcode());
			formbean.setBuyer_tel(cart.getContactInfo().getTel());
		}else{
			//当购物车里没有购买者的联系信息时,将用户的个人联系信息作为默认数据显示在购买人信息输入字段上
			Buyer buyer = WebUtil.getBuyer(request);
			if(buyer.getContactInfo()!=null){
				formbean.setBuyer(buyer.getRealname());
				formbean.setBuyer_address(buyer.getContactInfo().getAddress());
				formbean.setBuyer_gender(buyer.getGender());
				formbean.setBuyer_mobile(buyer.getContactInfo().getMobile());
				formbean.setBuyer_postalcode(buyer.getContactInfo().getPostalcode());
				formbean.setBuyer_tel(buyer.getContactInfo().getPhone());
			}
		}
		return mapping.findForward("deliverInfo");
	}

}
