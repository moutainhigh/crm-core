package com.cafe.crm.models.shift;

import javax.persistence.*;

@Entity
public class UserSalaryDetail {
	@Id
	@GeneratedValue
	private Long id;

	private Long userId;

	private int shiftSalary;

	private int bonus;

	private int totalSalary;

	@ManyToOne
	@JoinColumn(name = "shift_id", nullable = false)
	private Shift shift;

	public UserSalaryDetail(Long userId, int shiftSalary, int bonus, int totalSalary, Shift shift) {
		this.userId = userId;
		this.shiftSalary = shiftSalary;
		this.bonus = bonus;
		this.totalSalary = totalSalary;
		this.shift = shift;
	}

	public UserSalaryDetail() {
	}

	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getShiftSalary() {
		return shiftSalary;
	}

	public void setShiftSalary(int shiftSalary) {
		this.shiftSalary = shiftSalary;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public int getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(int totalSalary) {
		this.totalSalary = totalSalary;
	}
}
