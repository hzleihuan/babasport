package cn.itcast.web.action.product;

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
import cn.itcast.bean.product.Brand;
import cn.itcast.service.product.BrandService;
import cn.itcast.web.action.privilege.Permission;
import cn.itcast.web.formbean.product.BrandForm;


@Controller("/control/brand/list")
public class BrandAction extends Action {
	@Resource(name="brandServiceBean")
	private BrandService brandService;

	@Override @Permission(model="brand",privilegeValue="view")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		BrandForm formbean = (BrandForm) form;
		StringBuffer jpql = new StringBuffer("o.visible=?1");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		if("true".equals(formbean.getQuery())){
			jpql.append(" and o.name like ?"+ (params.size()+1));
			params.add("%"+ formbean.getName()+ "%");
		}
		PageView<Brand> pageView = new PageView<Brand>(12, formbean.getPage());
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("code", "desc");
		pageView.setQueryResult(brandService.getScrollData(pageView.getFirstResult(), 
				pageView.getMaxresult(), jpql.toString(), params.toArray(), orderby));
		request.setAttribute("pageView", pageView);		
		return mapping.findForward("list");
	}

}
