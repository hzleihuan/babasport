package cn.itcast.web.action.privilege;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.stereotype.Controller;

import cn.itcast.bean.privilege.Department;
import cn.itcast.service.privilege.DepartmentService;
import cn.itcast.utils.SiteUrl;
import cn.itcast.web.formbean.privilege.DepartmentForm;

@Controller("/control/department/manage")
public class DepartmentManageAction extends DispatchAction {
	@Resource DepartmentService departmentService;
	/**
	 * 部门添加界面
	 */
	@Permission(model="department", privilegeValue="insert")
	public ActionForward addDepartmentUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("adddepartment");
	}
	/**
	 * 添加部门
	 */
	@Permission(model="department", privilegeValue="insert")
	public ActionForward addDepartment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentForm formbean = (DepartmentForm)form;
		departmentService.save(new Department(formbean.getName()));
		
		request.setAttribute("message", "添加部门成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.department.list"));
		return mapping.findForward("message");
	}
	/**
	 * 部门修改界面
	 */
	@Permission(model="department", privilegeValue="update")
	public ActionForward editDepartmentUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentForm formbean = (DepartmentForm)form;
		Department department = departmentService.find(formbean.getDepartmentid());
		formbean.setName(department.getName());
		return mapping.findForward("editdepartment");
	}
	/**
	 * 修改部门
	 */
	@Permission(model="department", privilegeValue="update")
	public ActionForward editDepartment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentForm formbean = (DepartmentForm)form;
		Department department = departmentService.find(formbean.getDepartmentid());
		department.setName(formbean.getName());
		departmentService.update(department);
		
		request.setAttribute("message", "修改部门成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.department.list"));
		return mapping.findForward("message");
	}
	
	/**
	 * 删除部门
	 */
	@Permission(model="department", privilegeValue="delete")
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentForm formbean = (DepartmentForm)form;
		departmentService.delete((Serializable)formbean.getDepartmentid());
		
		request.setAttribute("message", "删除部门成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.department.list"));
		return mapping.findForward("message");
	}
	
}
