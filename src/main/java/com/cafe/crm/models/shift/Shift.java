package com.cafe.crm.models.shift;


import com.cafe.crm.models.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "Shift")
public class Shift {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "dateShift")
	private LocalDate dateShift;

	@Column(name = "checkValue")
	private Integer checkValue;// ПОСЛЕ ИНТЕГРАЦИИ СО СЧЕТАМИ ЗАМЕНИТЬ НА СЕТ СЧЕТОВ


	@Column(name = "isOpen") // открыта ли смена
			Boolean isOpen;

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = User.class)
	@JoinTable(name = "usersOfShift",
			joinColumns = {@JoinColumn(name = "shift_id")},
			inverseJoinColumns = {@JoinColumn(name = "user_id")})
	private Set<User> users;

	public Set<User> getUsers() {
		return users;
	}


	public Shift(LocalDate dateShift, Integer checkValue, Set<User> users) {
		this.dateShift = dateShift;
		this.checkValue = checkValue;
		this.users = users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Shift() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateShift() {
		return dateShift;
	}

	public void setDateShift(LocalDate dateShift) {
		this.dateShift = dateShift;
	}

	public Integer getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(Integer checkValue) {
		this.checkValue = checkValue;
	}

	public Boolean getOpen() {
		return isOpen;
	}

	public void setOpen(Boolean open) {
		isOpen = open;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Shift shift = (Shift) o;

		if (id != null ? !id.equals(shift.id) : shift.id != null) return false;
		if (dateShift != null ? !dateShift.equals(shift.dateShift) : shift.dateShift != null) return false;
		if (checkValue != null ? !checkValue.equals(shift.checkValue) : shift.checkValue != null) return false;
		if (isOpen != null ? !isOpen.equals(shift.isOpen) : shift.isOpen != null) return false;
		return users != null ? users.equals(shift.users) : shift.users == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (dateShift != null ? dateShift.hashCode() : 0);
		result = 31 * result + (checkValue != null ? checkValue.hashCode() : 0);
		result = 31 * result + (isOpen != null ? isOpen.hashCode() : 0);
		result = 31 * result + (users != null ? users.hashCode() : 0);
		return result;
	}
}
