package cn.itcast.bean.privilege;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
/**
 * 权限组(角色)
 */
@Entity
public class PrivilegeGroup {
	private Integer id;
	private String name;
	private Set<SystemPrivilege> privileges = new HashSet<SystemPrivilege>();
	private Set<Employee> employees = new HashSet<Employee>();
	
	public PrivilegeGroup(){}
	
	public PrivilegeGroup(Integer id) {
		this.id = id;
	}
	
	@ManyToMany(mappedBy="groups",cascade=CascadeType.REFRESH)
	public Set<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
	@Id @GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(length=20,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
	@JoinTable(name="group_privilege",joinColumns=@JoinColumn(name="groupid"),
			inverseJoinColumns={@JoinColumn(name="model", referencedColumnName="model"),
								@JoinColumn(name="privilegeValue", referencedColumnName="privilegeValue")})
	public Set<SystemPrivilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Set<SystemPrivilege> privileges) {
		this.privileges = privileges;
	}
	/**
	 * 添加权限
	 * @param privilege 权限
	 */
	public void addPrivilege(SystemPrivilege privilege) {
		this.privileges.add(privilege);
	}
}
