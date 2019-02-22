package cn.itcast.web.action.book;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.itcast.bean.PageView;
import cn.itcast.bean.book.Order;
import cn.itcast.bean.book.OrderState;
import cn.itcast.service.book.OrderService;
import cn.itcast.web.action.privilege.Permission;
import cn.itcast.web.formbean.book.OrderForm;

@Controller("/control/order/list")
public class OrderListAction extends Action {
	@Resource OrderService orderService;

	@Override @Permission(model="order",privilegeValue="view")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm formbean = (OrderForm) form;
		PageView<Order> pageView = new PageView<Order>(12,  formbean.getPage());
		OrderState state = formbean.getState()==null? OrderState.WAITCONFIRM : formbean.getState();
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "asc");
		if("true".equals(formbean.getQuery())){
			StringBuilder jpql = new StringBuilder();
			List<Object> params = new ArrayList<Object>();
			if(formbean.getUsername()!=null && !"".equals(formbean.getUsername().trim())){
				if(params.size()>0) jpql.append(" and ");
				jpql.append("o.buyer.username like ?").append(params.size()+1);
				params.add("%"+ formbean.getUsername().trim()+ "%");
			}
			if(formbean.getState()!=null){
				if(params.size()>0) jpql.append(" and ");
				jpql.append(" o.state=?").append(params.size()+1);
				params.add(formbean.getState());
			}
			if(formbean.getOrderid()!=null && !"".equals(formbean.getOrderid().trim())){
				if(params.size()>0) jpql.append(" and ");
				jpql.append(" o.orderid=?").append(params.size()+1);
				params.add(formbean.getOrderid());
			}
			if(formbean.getRecipients()!=null && !"".equals(formbean.getRecipients().trim())){
				if(params.size()>0) jpql.append(" and ");
				jpql.append(" o.orderDeliverInfo.recipients like ?").append(params.size()+1);
				params.add("%"+formbean.getRecipients()+ "%");
			}
			if(formbean.getBuyer()!=null && !"".equals(formbean.getBuyer().trim())){
				if(params.size()>0) jpql.append(" and ");
				jpql.append(" o.orderContactInfo.buyerName like ?").append(params.size()+1);
				params.add("%"+formbean.getBuyer()+ "%");
			}
			pageView.setQueryResult(orderService.getScrollData(pageView.getFirstResult(), pageView.getMaxresult(), 
					jpql.toString(), params.toArray(), orderby));
		}else{
			pageView.setQueryResult(orderService.getScrollData(pageView.getFirstResult(), pageView.getMaxresult(), 
				"o.state=?1", new Object[]{state}, orderby));
		}
		request.setAttribute("pageView", pageView);
		return mapping.findForward("list");
	}

}
