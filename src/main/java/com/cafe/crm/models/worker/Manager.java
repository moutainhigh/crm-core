package com.cafe.crm.models.worker;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;


@Entity
@Table(name = "manager")
@PrimaryKeyJoinColumn(name = "worker_id")
public class Manager extends Worker implements UserDetails {

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(name = "manager_roles",
            joinColumns = {@JoinColumn(name = "worker_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Manager() {
    }

    public Manager(String firstName, String lastName, String position, Long shiftSalary, String login, String password) {
        super(firstName, lastName, position, shiftSalary);
        this.login = login;
        this.password = password;
    }

    public Manager(Long id, String firstName, String lastName, String login, String password, String position,
                   Long shiftSalary) {
        super(id, firstName, lastName, position, shiftSalary);
        this.login = login;
        this.password = password;
    }

    public Manager(String firstName, String lastName, String login, String password, String position,
                   Long shiftSalary) {
        super(firstName, lastName, position, shiftSalary);
        this.login = login;
        this.password = password;
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
    public boolean isEnabled() {
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Manager manager = (Manager) o;

        if (login != null ? !login.equals(manager.login) : manager.login != null) return false;
        return password != null ? password.equals(manager.password) : manager.password == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
