package cn.itcast.web.action.privilege;

import java.io.File;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.stereotype.Controller;

import cn.itcast.bean.privilege.Department;
import cn.itcast.bean.privilege.Employee;
import cn.itcast.bean.privilege.IDCard;
import cn.itcast.bean.privilege.PrivilegeGroup;
import cn.itcast.bean.user.Gender;
import cn.itcast.service.privilege.DepartmentService;
import cn.itcast.service.privilege.EmployeeService;
import cn.itcast.service.privilege.PrivilegeGroupService;
import cn.itcast.utils.SiteUrl;
import cn.itcast.web.formbean.privilege.EmployeeForm;
/**
 * 员工信息维护
 */
@Controller("/control/employee/manage")
public class EmployeeManageAction extends DispatchAction {
	@Resource EmployeeService employeeService;
	@Resource DepartmentService departmentService;
	@Resource PrivilegeGroupService groupService;
	
	
	/**
	 * 权限设置界面
	 */
	@Permission(model="employee", privilegeValue="privilegeGroupSet")
	public ActionForward privilegeGroupSetUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeForm formbean = (EmployeeForm) form;
		request.setAttribute("groups", groupService.getScrollData().getResultlist());
		Employee employee = employeeService.find(formbean.getUsername().trim());
		request.setAttribute("usergroups", employee.getGroups());
		return mapping.findForward("privilegeSet");
	}
	/**
	 * 权限设置
	 */
	@Permission(model="employee", privilegeValue="privilegeGroupSet")
	public ActionForward privilegeGroupSet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeForm formbean = (EmployeeForm) form;
		Employee employee = employeeService.find(formbean.getUsername().trim());
		employee.getGroups().clear();
		if(formbean.getGroupids()!=null){
			for(Integer id : formbean.getGroupids()){
				employee.addPrivilegeGroup(new PrivilegeGroup(id));
			}
		}
		employeeService.update(employee);
		
		request.setAttribute("message", "设置成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.employee.list"));
		return mapping.findForward("message");
	}
	
	/**
	 * 添加员工界面
	 */
	@Permission(model="employee", privilegeValue="reg")
	public ActionForward regEmployeeUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//1>获取所有的部门列表存放进行request范围,key为departments
		EmployeeForm formbean = (EmployeeForm) form;
		formbean.setGender(Gender.MAN);
		request.setAttribute("departments", departmentService.getScrollData().getResultlist());
		return mapping.findForward("addEmployee");
	}
	
	/**
	 * 添加员工
	 */
	@Permission(model="employee", privilegeValue="reg")
	public ActionForward regEmployee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeForm formbean = (EmployeeForm) form;
		if(!this.employeeService.exist(formbean.getUsername().trim())){
			Employee employee = new Employee(formbean.getUsername().trim());
			employee.setDegree(formbean.getDegree());
			if(formbean.getDepartmentid()!=null && formbean.getDepartmentid()>0)
				employee.setDepartment(new Department(formbean.getDepartmentid()));
			employee.setEmail(formbean.getEmail());
			employee.setGender(formbean.getGender());
			employee.setIdCard(new IDCard(formbean.getCardno(), formbean.getAddress(), formbean.getBirthday()));
			employee.setPassword(formbean.getPassword());
			employee.setPhone(formbean.getPhone());
			employee.setRealname(formbean.getRealname());
			employee.setSchool(formbean.getSchool());
			if(formbean.getPicture()!=null && formbean.getPicture().getFileSize()>0 &&
					EmployeeForm.validateImageFileType(formbean.getPicture())){
				String imageName = UUID.randomUUID().toString()+ "."+ EmployeeForm.getExt(formbean.getPicture());
				String strDir = "/images/employee/"+ employee.getUsername();
				File saveDir = new File(request.getSession().getServletContext().getRealPath(strDir));
				EmployeeForm.saveFile(saveDir, imageName, formbean.getPicture().getFileData());
				employee.setImageName(imageName);
			}
			employeeService.save(employee);
			
			request.setAttribute("message", "添加员工成功");
			request.setAttribute("urladdress", SiteUrl.readUrl("control.employee.list"));
		}else{
			request.setAttribute("message", "该用户名已经存在");
			request.setAttribute("urladdress", SiteUrl.readUrl("control.employee.addUI"));
		}
		//1>判断用户名是否存在,如果存在,提示"该用户名已经存在",否则执行下面步骤
		//2>获取请求参数构造Employee对象和IDCard对象,把IDCard赋给Employee对象
		//3>如果用户上传的照片,先验证照片是否在图片格式( formbean.validateImageFileType(formfile) ),如果是图片格式才把照片保存在/images/employee/用户账号/路径下.保存的文件名采用UUID生成.
		//4>把文件名称设置进Employee对象.
		//5>调用业务bean保存Employee对象
		return mapping.findForward("message");
	}
	/**
	 * 标志员工离职
	 */
	@Permission(model="employee", privilegeValue="leave")
	public ActionForward leave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeForm formbean = (EmployeeForm) form;
		employeeService.leave(formbean.getUsername());
		
		request.setAttribute("message", "设置成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.employee.list"));
		return mapping.findForward("message");
	}
	
	/**
	 * 员工信息修改界面
	 */
	@Permission(model="employee", privilegeValue="update")
	public ActionForward editEmployeeUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeForm formbean = (EmployeeForm) form;
		Employee employee = employeeService.find(formbean.getUsername().trim());
		
		formbean.setDegree(employee.getDegree());
		formbean.setEmail(employee.getEmail());
		formbean.setGender(employee.getGender());
		if(employee.getDepartment()!=null)
			formbean.setDepartmentid(employee.getDepartment().getId());
		formbean.setCardno(employee.getIdCard().getCardno());
		formbean.setAddress(employee.getIdCard().getAddress());
		formbean.setBirthday(employee.getIdCard().getBirthday());
		formbean.setPassword(employee.getPassword());
		formbean.setPhone(employee.getPhone());
		formbean.setRealname(employee.getRealname());
		formbean.setSchool(employee.getSchool());
		
	 	request.setAttribute("departments", departmentService.getScrollData().getResultlist());
	 	request.setAttribute("selectdepartmentid", employee.getDepartment().getId());
	 	request.setAttribute("imagePath", employee.getImageFullpath());
		return mapping.findForward("editEmployee");
	}
	/**
	 * 修改员工信息
	 */
	@Permission(model="employee", privilegeValue="update")
	public ActionForward editEmployee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeForm formbean = (EmployeeForm) form;
		Employee employee = employeeService.find(formbean.getUsername().trim());
		employee.setDegree(formbean.getDegree());
		employee.setDepartment(new Department(formbean.getDepartmentid()));
		employee.setEmail(formbean.getEmail());
		employee.setGender(formbean.getGender());
		employee.getIdCard().setCardno(formbean.getCardno());
		employee.getIdCard().setAddress(formbean.getAddress());
		employee.getIdCard().setBirthday(formbean.getBirthday());
		employee.setPhone(formbean.getPhone());
		employee.setRealname(formbean.getRealname());
		employee.setSchool(formbean.getSchool());
		if(formbean.getPicture()!=null && formbean.getPicture().getFileSize()>0 &&
				EmployeeForm.validateImageFileType(formbean.getPicture())){
			String imageName = UUID.randomUUID().toString()+ "."+ EmployeeForm.getExt(formbean.getPicture());
			String strDir = "/images/employee/"+ employee.getUsername();
			File saveDir = new File(request.getSession().getServletContext().getRealPath(strDir));
			EmployeeForm.saveFile(saveDir, imageName, formbean.getPicture().getFileData());
			employee.setImageName(imageName);
		}
		employeeService.update(employee);
		
		request.setAttribute("message", "修改员工信息成功");
		request.setAttribute("urladdress", SiteUrl.readUrl("control.employee.list"));
		return mapping.findForward("message");
	}
	/**
	 * 校验用户名是否存在
	 */
	public ActionForward exist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeForm formbean = (EmployeeForm) form;
		if(employeeService.exist(formbean.getUsername().trim())){
			request.setAttribute("message", "用户已经存在");
		}else{
			request.setAttribute("message", "该用户名可以使用");
		}
		return mapping.findForward("usernameIsExsit");
	}
	
	
}
