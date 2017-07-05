package com.cafe.crm.models.shift;


import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.worker.Worker;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
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
	private Integer checkValue;// after change on set<>

	@Column(name = "isOpen") // shift is open ?
			Boolean isOpen;

	@OneToMany
	private Set<Calculate> allCalculate;

	@OneToMany
	private List<Client> clients;

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Worker.class)
	@JoinTable(name = "permissions_allShifts",
			joinColumns = {@JoinColumn(name = "shift_id")},
			inverseJoinColumns = {@JoinColumn(name = "worker_id")})
	private Set<Worker> users;

	public Set<Worker> getUsers() {
		return users;
	}

	public String getUsersNames() {   // return only names of workers of shift

		String names = "";
		for (Worker worker : users) {
			names += worker.getFirstName() + " ";

		}
		return names;
	}

	public Shift(LocalDate dateShift, Integer checkValue, Set<Worker> users) {
		this.dateShift = dateShift;
		this.checkValue = checkValue;
		this.users = users;
	}

	public void setUsers(Set<Worker> users) {
		this.users = users;
	}

	public Shift() {
	}

	public Set<Calculate> getAllCalculate() {
		return allCalculate;
	}

	public void setAllCalculate(Set<Calculate> allCalculate) {
		this.allCalculate = allCalculate;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
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
		return isOpen != null ? isOpen.equals(shift.isOpen) : shift.isOpen == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (dateShift != null ? dateShift.hashCode() : 0);
		result = 31 * result + (checkValue != null ? checkValue.hashCode() : 0);
		result = 31 * result + (isOpen != null ? isOpen.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Shift{" +
				"id=" + id +
				", dateShift=" + dateShift +
				", isOpen=" + isOpen +
				", users=" + users +
				'}';
	}
}
