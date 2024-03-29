package cn.itcast.web.action.product;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.itcast.bean.PageView;
import cn.itcast.bean.product.ProductInfo;
import cn.itcast.service.product.ProductSearchService;
import cn.itcast.web.formbean.product.ProductQueryForm;
/**
 * ��Ʒ��ѯ
 */
@Controller("/product/query")
public class ProductQueryAction extends Action {
	@Resource ProductSearchService productSearchService;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductQueryForm formbean = (ProductQueryForm)form;
		PageView<ProductInfo> pageView = new PageView<ProductInfo>(12,formbean.getPage());
		pageView.setQueryResult(productSearchService.search(formbean.getWord(), 
				pageView.getFirstResult(), pageView.getMaxresult()));
		
		request.setAttribute("pageView", pageView);
		return mapping.findForward("list");
	}

}
