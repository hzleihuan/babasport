package cn.itcast.web.action.product;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.stereotype.Controller;

import cn.itcast.bean.product.ProductType;
import cn.itcast.service.product.ProductTypeService;
import cn.itcast.utils.SiteUrl;
import cn.itcast.web.action.privilege.Permission;
import cn.itcast.web.formbean.product.ProductTypeForm;


@Controller("/control/product/type/manage")
public class ProductTypeManageAction extends DispatchAction {
	@Resource(name="productTypeServiceBean")
	private ProductTypeService productTypeService;
	
	/**
	 * 查询界面
	 */
	@Permission(model="productType",privilegeValue="view")
	public ActionForward queryUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("query");
	}
	
	/**
	 * 类别添加界面
	 */
	@Permission(model="productType",privilegeValue="insert")
	public ActionForward addUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("add");
	}
	
	/**
	 * 类别添加
	 */
	@Permission(model="productType",privilegeValue="insert")
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductTypeForm formbean = (ProductTypeForm) form;
		ProductType type = new ProductType(formbean.getName(),formbean.getNote());
		if(formbean.getParentid()!=null && formbean.getParentid()>0) type.setParent(new ProductType(formbean.getParentid()));
		productTypeService.save(type);
		request.setAttribute("message", "添加类别成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.product.type.list"));
		return mapping.findForward("message");
	}
	
	/**
	 * 类别修改界面
	 */
	@Permission(model="productType",privilegeValue="update")
	public ActionForward editUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductTypeForm formbean = (ProductTypeForm) form;
		ProductType type = productTypeService.find(formbean.getTypeid());
		formbean.setName(type.getName());
		formbean.setNote(type.getNote());
		return mapping.findForward("edit");
	}
	
	/**
	 * 类别修改
	 */
	@Permission(model="productType",privilegeValue="update")
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductTypeForm formbean = (ProductTypeForm) form;
		ProductType type = productTypeService.find(formbean.getTypeid());
		type.setName(formbean.getName());
		type.setNote(formbean.getNote());
		productTypeService.update(type);
		request.setAttribute("message", "修改类别成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.product.type.list"));
		return mapping.findForward("message");
	}
}
