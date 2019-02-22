package cn.itcast.web.action.privilege;

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
import cn.itcast.bean.privilege.Employee;
import cn.itcast.service.privilege.EmployeeService;
import cn.itcast.web.formbean.privilege.EmployeeForm;
/**
 * 员工列表
 */
@Controller("/control/employee/list")
public class EmployeeListAction extends Action {
	@Resource EmployeeService employeeService;

	@Override @Permission(model="employee", privilegeValue="view")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeForm formbean = (EmployeeForm) form;
		PageView<Employee> pageView = new PageView<Employee>(12, formbean.getPage());
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("realname", "desc");
		if("true".equals(formbean.getQuery())){
			StringBuilder sb = new StringBuilder();
			List<Object> params = new ArrayList<Object>();
			if(formbean.getUsername()!=null && !"".equals(formbean.getUsername().trim())){
				sb.append(" o.username=?").append(params.size()+1);
				params.add(formbean.getUsername());				
			}
			if(formbean.getRealname()!=null && !"".equals(formbean.getRealname().trim())){
				if(params.size()>0)  sb.append(" and ");
				sb.append(" o.realname like ?").append(params.size()+1);
				params.add("%"+ formbean.getRealname()+ "%");				
			}
			if(formbean.getDepartmentid()!=null && formbean.getDepartmentid()>0){
				if(params.size()>0)  sb.append(" and ");
				sb.append(" o.department.id=?").append(params.size()+1);
				params.add(formbean.getDepartmentid());				
			}
			pageView.setQueryResult(
					employeeService.getScrollData(pageView.getFirstResult(), pageView.getMaxresult(),
							sb.toString(), params.toArray(), orderby));
		}else{
			pageView.setQueryResult(employeeService.getScrollData(pageView.getFirstResult(), pageView.getMaxresult(), orderby));
		}		
		request.setAttribute("pageView", pageView);
		return mapping.findForward("list");
	}

}
