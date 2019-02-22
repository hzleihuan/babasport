package cn.itcast.service.privilege.impl;

import org.springframework.stereotype.Service;

import cn.itcast.bean.privilege.Employee;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.privilege.EmployeeService;

@Service
public class EmployeeServiceBean extends DaoSupport<Employee> implements EmployeeService {
	
	public boolean validate(String username, String password){
		long count = (Long)em.createQuery("select count(o) from Employee o where o.username=?1 and o.password=?2")
		.setParameter(1, username).setParameter(2, password).getSingleResult();
		return count>0;
	}
	
	public boolean exist(String username){
		long count = (Long)em.createQuery("select count(o) from Employee o where o.username=?1")
			.setParameter(1, username).getSingleResult();
		return count>0;
	}
	
	public void leave(String username){
		em.createQuery("update Employee o set o.visible=?1 where o.username=?2")
		.setParameter(1, false).setParameter(2, username).executeUpdate();
	}
}
