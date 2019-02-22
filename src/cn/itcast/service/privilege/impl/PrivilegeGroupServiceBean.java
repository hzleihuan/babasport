package cn.itcast.service.privilege.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import cn.itcast.bean.privilege.Employee;
import cn.itcast.bean.privilege.PrivilegeGroup;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.privilege.PrivilegeGroupService;

@Service
public class PrivilegeGroupServiceBean extends DaoSupport<PrivilegeGroup> implements PrivilegeGroupService {

	@Override
	public void delete(Serializable... entityids) {
		for(Serializable id : entityids){
			PrivilegeGroup group = this.find(id);
			if(group!=null){
				for(Employee e : group.getEmployees()){
					e.getGroups().remove(group);
				}
				em.remove(group);
			}
		}
	}
}
