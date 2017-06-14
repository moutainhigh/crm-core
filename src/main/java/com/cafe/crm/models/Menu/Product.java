package com.cafe.crm.models.Menu;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "Укажите название")
	@NotEmpty
	@Length(min = 1, max = 30)
	@Column(name = "name", nullable = false)
	private String name;

	@NotNull(message = "Укажите описание")
	@Column(name = "description")
	private String description;

	@NotNull(message = "Укажите цену")
	@Column(name = "cost")
	private Double cost;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Category.class)
	@JoinTable(name = "product_and_categories", joinColumns = {@JoinColumn(name = "category_id")}, inverseJoinColumns = {@JoinColumn(name = "product_id")})
	private Category category;

	public Product() {
	}

	public Product(String name, String description, Double cost) {
		this.name = name;
		this.description = description;
		this.cost = cost;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Product product = (Product) o;

		if (cost != product.cost) return false;
		if (id != null ? !id.equals(product.id) : product.id != null) return false;
		if (name != null ? !name.equals(product.name) : product.name != null) return false;
		return description != null ? description.equals(product.description) : product.description == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (cost != null ? cost.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", cost=" + cost +
				'}';
	}
}
