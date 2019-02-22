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

import cn.itcast.bean.privilege.PrivilegeGroup;
import cn.itcast.bean.privilege.SystemPrivilege;
import cn.itcast.bean.privilege.SystemPrivilegePK;
import cn.itcast.service.privilege.PrivilegeGroupService;
import cn.itcast.service.privilege.SystemPrivilegeService;
import cn.itcast.utils.SiteUrl;
import cn.itcast.web.formbean.privilege.PrivilegeGroupForm;

@Controller("/control/privilegegroup/manage")
public class PrivilegeGroupManageAction extends DispatchAction {
	@Resource PrivilegeGroupService groupService;
	@Resource SystemPrivilegeService privilegeService;
	
	/**
	 * 删除权限组
	 */
	@Permission(model="privilegeGroup", privilegeValue="delete")
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrivilegeGroupForm formbean = (PrivilegeGroupForm) form;
		groupService.delete((Serializable)formbean.getGroupid());

		request.setAttribute("message", "删除权限组成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.privilegegroup.list"));
		return mapping.findForward("message");
	}
	/**
	 * 权限组添加界面
	 */
	@Permission(model="privilegeGroup", privilegeValue="insert")
	public ActionForward addUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//得到所有的系统权限,存放进request范围,key为privileges
		request.setAttribute("privileges", privilegeService.getScrollData().getResultlist());
		return mapping.findForward("addprivilegegroup");
	}
	
	/**
	 * 添加权限组
	 */
	@Permission(model="privilegeGroup", privilegeValue="insert")
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrivilegeGroupForm formbean = (PrivilegeGroupForm) form;
		PrivilegeGroup group = new PrivilegeGroup();
		group.setName(formbean.getName());
		for(SystemPrivilegePK id : formbean.getPrivileges()){
			group.addPrivilege(new SystemPrivilege(id));
		}
		groupService.save(group);
		request.setAttribute("message", "添加权限组成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.privilegegroup.list"));
		return mapping.findForward("message");
	}
	
	/**
	 * 权限组修改界面
	 */
	@Permission(model="privilegeGroup", privilegeValue="update")
	public ActionForward editUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrivilegeGroupForm formbean = (PrivilegeGroupForm) form;
		PrivilegeGroup group = groupService.find(formbean.getGroupid());
		formbean.setName(group.getName());
		request.setAttribute("privileges", privilegeService.getScrollData().getResultlist());
		request.setAttribute("selectprivileges", group.getPrivileges());
		return mapping.findForward("editprivilegegroup");
	}
	/**
	 * 修改权限组
	 */
	@Permission(model="privilegeGroup", privilegeValue="update")
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrivilegeGroupForm formbean = (PrivilegeGroupForm) form;
		PrivilegeGroup group = groupService.find(formbean.getGroupid());
		group.setName(formbean.getName());
		group.getPrivileges().clear();
		for(SystemPrivilegePK id : formbean.getPrivileges()){
			group.addPrivilege(new SystemPrivilege(id));
		}
		groupService.update(group);
		
		request.setAttribute("message", "修改权限组成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.privilegegroup.list"));
		return mapping.findForward("message");
	}
	
}
