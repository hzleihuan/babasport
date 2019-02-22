package cn.itcast.service.privilege.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import cn.itcast.bean.privilege.Department;
import cn.itcast.bean.privilege.Employee;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.privilege.DepartmentService;

@Service
public class DepartmentServiceBean extends DaoSupport<Department> implements DepartmentService {

	@Override
	public void delete(Serializable... entityids) {
		for(Serializable id : entityids){
			Department department = em.find(Department.class, id);
			for(Employee employee : department.getEmployees()){
				employee.setDepartment(null);
			}
			em.remove(department);
		}		
	}

}
