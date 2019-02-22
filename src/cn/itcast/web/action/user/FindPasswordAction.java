package cn.itcast.web.action.user;

import java.io.StringWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Controller;

import cn.itcast.bean.user.Buyer;
import cn.itcast.mail.EmailSender;
import cn.itcast.service.user.BuyerService;
import cn.itcast.utils.MD5;
import cn.itcast.web.formbean.user.FindPasswordForm;

@Controller("/user/post")
public class FindPasswordAction extends DispatchAction {
	@Resource BuyerService buyerService;
	/**
	 * 找回密码---发送邮件
	 */
	public ActionForward getpassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FindPasswordForm formbean = (FindPasswordForm)form;
		Buyer buyer = buyerService.find(formbean.getUsername().trim());
		if(buyer!=null){
			Template template = Velocity.getTemplate("mailContent.vm");
			VelocityContext context = new VelocityContext();
			context.put("username", buyer.getUsername());
			String validateCode = MD5.MD5Encode(buyer.getUsername()+ buyer.getPassword());
			context.put("validateCode", validateCode);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			writer.flush();
			String mailContent = writer.toString();//得到邮件内容
			EmailSender.send(buyer.getEmail(), "巴巴运动网--找回密码", mailContent, "text/html");
		}else{
			request.setAttribute("message", "用户名有误");
			request.setAttribute("urladdress", "/user/findpassword.do");
			return mapping.findForward("message");
		}
		return mapping.findForward("findPassword2");
	}
	/**
	 * 找回密码---显示密码修改界面
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FindPasswordForm formbean = (FindPasswordForm)form;
		Buyer buyer = buyerService.find(formbean.getUsername().trim());
		if(buyer!=null){
			String validateCode = MD5.MD5Encode(buyer.getUsername()+ buyer.getPassword());
			if(validateCode.equals(formbean.getValidateCode())){
				return mapping.findForward("findPassword3");
			}else{
				return mapping.findForward("errorresult");
			}
		}else{
			request.setAttribute("message", "用户名有误");
		}		
		request.setAttribute("urladdress", "/user/findpassword.do");
		return mapping.findForward("message");
	}
	/**
	 * 找回密码---显示密码修改界面
	 */
	public ActionForward changepassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FindPasswordForm formbean = (FindPasswordForm)form;
		Buyer buyer = buyerService.find(formbean.getUsername().trim());
		if(buyer!=null){
			String validateCode = MD5.MD5Encode(buyer.getUsername()+ buyer.getPassword());
			if(validateCode.equals(formbean.getValidateCode())){
				buyerService.updatePassword(buyer.getUsername(), formbean.getPassword());
				request.setAttribute("message", "密码修改成功");
				request.setAttribute("urladdress", "/user/logon.do");
				return mapping.findForward("message");
			}else{
				request.setAttribute("message", "来源不合法");
			}
		}else{
			request.setAttribute("message", "用户名有误");
		}		
		request.setAttribute("urladdress", "/user/findpassword.do");
		return mapping.findForward("message");
	}
	
}
