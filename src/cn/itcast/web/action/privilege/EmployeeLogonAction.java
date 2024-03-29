package cn.itcast.web.action.privilege;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.itcast.service.privilege.EmployeeService;
import cn.itcast.web.formbean.privilege.EmployeeForm;
/**
 * 员工登录
 */
@Controller("/employee/logon")
public class EmployeeLogonAction extends Action {
	@Resource EmployeeService employeeService;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeForm formbean = (EmployeeForm) form;
		if(formbean.getUsername()!=null && !"".equals(formbean.getUsername().trim())
				&& formbean.getPassword()!=null && !"".equals(formbean.getPassword().trim())){
			if(employeeService.validate(formbean.getUsername().trim(), formbean.getPassword().trim())){
				request.getSession().setAttribute("employee", employeeService.find(formbean.getUsername().trim()));
				return mapping.findForward("main");
			}
			request.setAttribute("message", "用户名或密码有误");
		}		
		return mapping.findForward("logon");
	}
}
