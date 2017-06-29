package com.cafe.crm.models.worker;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "boss")
public class Boss extends Worker implements UserDetails {

	@Column(name = "password")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
	@JoinTable(name = "boss_roles",
			joinColumns = {@JoinColumn(name = "worker_id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private Set<Role> roles;

	public Boss() {
		super();
	}

	public Boss(String actionForm) {
		super(actionForm);
	}

	public Boss(String firstName, String lastName, String email, Long phone, List<Position> allPosition,
				Long shiftSalary, Long countShift, Long salary, String password, Set<Role> roles, String actionForm,
				Boolean enabled) {
		super(firstName, lastName, email, phone, allPosition, shiftSalary, countShift, salary, actionForm, enabled);
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Boss boss = (Boss) o;

		return password != null ? password.equals(boss.password) : boss.password == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (password != null ? password.hashCode() : 0);
		return result;
	}

	@Override
	public boolean isEnabled() {
		return getEnabled();
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
