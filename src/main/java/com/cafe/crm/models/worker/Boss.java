package com.cafe.crm.models.worker;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "boss")
@PrimaryKeyJoinColumn(name = "worker_id")
public class Boss extends Worker implements UserDetails  {

	@Column(name = "login")
	private String login;

	@Column(name = "password")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
	@JoinTable(name = "boss_roles",
			joinColumns = {@JoinColumn(name = "worker_id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private Set<Role> roles;

	@Column(name = "enabled", nullable = false)
	private Boolean enabled = true;

	public Boss(String login, String password) {
	}

	public Boss() {
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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
		return login;
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

		Boss boss = (Boss) o;

		if (login != null ? !login.equals(boss.login) : boss.login != null) return false;
		return password != null ? password.equals(boss.password) : boss.password == null;
	}

	@Override
	public int hashCode() {
		int result = login != null ? login.hashCode() : 0;
		result = 31 * result + (password != null ? password.hashCode() : 0);
		return result;
	}

	@Override

	public boolean isEnabled() {
		return enabled;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
