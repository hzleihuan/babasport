package cn.itcast.web.filter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Aspect @Component
public class PermissionInterceptor {
	@Pointcut("execution (org.apache.struts.action.ActionForward cn.itcast.web.action..*.*(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse))")
	private void actionMethod() {}//声明一个切入点
	
	@Around("actionMethod()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("执行的方法:"+ pjp.getSignature().getName());
		//if(){//判断用户是否在权限
			Object result = pjp.proceed();
		//}
		return result;
	}
}
