package com.cafe.crm.models.worker;

import com.cafe.crm.models.shift.Shift;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "worker")
@Inheritance(strategy = InheritanceType.JOINED)
public class Worker implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "firstName", length = 256)
	private String firstName;

	@Column(name = "lastName", length = 256)
	private String lastName;

	@Column(name = "position", length = 256)
	private String position;

	@Column(name = "shiftSalary", nullable = true)
	private Long shiftSalary;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = Shift.class)
	@JoinTable(name = "permissions_allShifts",
			joinColumns = {@JoinColumn(name = "worker_id")},
			inverseJoinColumns = {@JoinColumn(name = "shift_id")})
	private Set<Shift> allShifts;

	@Column(name = "countShift", nullable = true)
	private Long countShift;

	@Column(name = "salary", nullable = true)
	private Long salary;

	public Worker() {
	}

	public Worker(String firstName, String lastName, String position, Long shiftSalary) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.position = position;
		this.shiftSalary = shiftSalary;
	}

	public Worker(Long id, String firstName, String lastName, String position, Long shiftSalary) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.position = position;
		this.shiftSalary = shiftSalary;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Long getShiftSalary() {
		return shiftSalary;
	}

	public void setShiftSalary(Long shiftSalary) {
		this.shiftSalary = shiftSalary;
	}

	public Set<Shift> getAllShifts() {
		return allShifts;
	}

	public void setAllShifts(Set<Shift> allShifts) {
		this.allShifts = allShifts;
	}

	public Long getCountShift() {
		return countShift;
	}

	public void setCountShift(Long countShift) {
		this.countShift = countShift;
	}

	public Long getSalary() {
		return salary;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}
}
