package com.cafe.crm.models.worker;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "manager")
public class Manager extends Worker implements UserDetails {

	@Column(name = "password")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
	@JoinTable(name = "manager_roles",
			joinColumns = {@JoinColumn(name = "worker_id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private Set<Role> roles;

	public Manager() {
		super();
	}

	public Manager(String actionForm) {
		super(actionForm);
	}

	public Manager(String firstName, String lastName, String email, String phone, List<Position> allPosition,
				   Long shiftSalary, Long salary, String password, Set<Role> roles, String actionForm,
				   Boolean enabled) {
		super(firstName, lastName, email, phone, allPosition, shiftSalary, salary, actionForm, enabled);
		this.password = password;
		this.roles = roles;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return getEnabled();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Manager manager = (Manager) o;

		return password != null ? password.equals(manager.password) : manager.password == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (password != null ? password.hashCode() : 0);
		return result;
	}
}
