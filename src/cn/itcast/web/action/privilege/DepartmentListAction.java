package cn.itcast.web.action.privilege;

import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.itcast.bean.PageView;
import cn.itcast.bean.privilege.Department;
import cn.itcast.service.privilege.DepartmentService;
import cn.itcast.web.formbean.privilege.DepartmentForm;
/**
 * 部门分页列表
 */
@Controller("/control/department/list")
public class DepartmentListAction extends Action {
	@Resource DepartmentService departmentService;
	
	@Override @Permission(model="department", privilegeValue="view")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentForm formbean = (DepartmentForm) form;
		PageView<Department> pageView = new PageView<Department>(12, formbean.getPage());
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		pageView.setQueryResult(departmentService.getScrollData(pageView.getFirstResult(), pageView.getMaxresult(), orderby));
		request.setAttribute("pageView", pageView);
		
		return mapping.findForward("list");
	}

}
