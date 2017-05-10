package com.cafe.crm.models;


import javax.persistence.*;

/**
 * Created by Sasha ins on 03.05.2017.
 */
@Entity
@Table(name = "main")
public class MainClass {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;


	@Column(name = "ShiftId")  // id открытой в данный смены
	private Long ShiftId;

	@Column(name = "isOpen") // открыта ли смена
			Boolean isOpen;


	public MainClass(Long shiftId, Boolean isOpen) {
		ShiftId = shiftId;
		this.isOpen = isOpen;
	}

	public MainClass() {
	}

	public Long getShiftId() {
		return ShiftId;
	}

	public void setShiftId(Long shiftId) {
		ShiftId = shiftId;
	}

	public Boolean getOpen() {
		return isOpen;
	}

	public void setOpen(Boolean open) {
		isOpen = open;
	}
}
