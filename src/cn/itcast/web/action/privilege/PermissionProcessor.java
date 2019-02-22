package cn.itcast.web.action.privilege;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.web.struts.DelegatingRequestProcessor;

import cn.itcast.bean.privilege.Employee;
import cn.itcast.bean.privilege.PrivilegeGroup;
import cn.itcast.bean.privilege.SystemPrivilege;
import cn.itcast.bean.privilege.SystemPrivilegePK;
import cn.itcast.utils.SiteUrl;
import cn.itcast.utils.WebUtil;

public class PermissionProcessor extends DelegatingRequestProcessor {

	@Override
	protected ActionForward processActionPerform(HttpServletRequest request,
			HttpServletResponse response, Action action, ActionForm form,
			ActionMapping mapping) throws IOException, ServletException {
		if(validate(request, action, mapping)){//校验用户是否有权限
			return super.processActionPerform(request, response, action, form, mapping);
		}
		request.setAttribute("message", "你没有权限执行该操作");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.control.right"));
		return mapping.findForward("message");
	}
	/**
	 * 校验用户是否具有执行当前方法的权限
	 */
	private boolean validate(HttpServletRequest request, Action action, ActionMapping mapping) {
		Method currentMethod = getCurrentMethod(request, action, mapping);//得到当前执行的action方法
		if(currentMethod!=null && currentMethod.isAnnotationPresent(Permission.class)){
			Permission permission = currentMethod.getAnnotation(Permission.class);
			SystemPrivilege privilege = new SystemPrivilege(new SystemPrivilegePK(permission.model(), permission.privilegeValue()));
			Employee employee = WebUtil.getEmployee(request);
			for(PrivilegeGroup p : employee.getGroups()){
				if(p.getPrivileges().contains(privilege)) return true;
			}
			return false;
		}
		return true;
	}
	/**
	 * 得到当前执行的方法
	 */
	private Method getCurrentMethod(HttpServletRequest request, Action action, ActionMapping mapping) {
		String methodName = "execute";
		if(DispatchAction.class.isAssignableFrom(action.getClass())){//判断DispatchAction是否是action的父类
			methodName = request.getParameter(mapping.getParameter());
		}
		try {
			return  action.getClass().getMethod(methodName, ActionMapping.class, ActionForm.class,
				HttpServletRequest.class, HttpServletResponse.class);
		} catch (Exception e) {}
		return null;
	}

}
