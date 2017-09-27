package com.cafe.crm.models.property;

import com.cafe.crm.models.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "trash_property")
public class AllSystemProperty extends BaseEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column
	private String property;

	public AllSystemProperty() {
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

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AllSystemProperty that = (AllSystemProperty) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}
