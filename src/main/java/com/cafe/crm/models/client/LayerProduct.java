package com.cafe.crm.models.client;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "layer_product")
public class LayerProduct {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String description;

	private Double cost;

	@ManyToMany(fetch = FetchType.LAZY)
	@JsonManagedReference
	@JoinTable(name = "client_layer_product",
			joinColumns = {@JoinColumn(name = "layer_product_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "client_id", referencedColumnName = "id")})
	private List<Client> clients;

	public LayerProduct() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		LayerProduct that = (LayerProduct) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		return name != null ? name.equals(that.name) : that.name == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
}
