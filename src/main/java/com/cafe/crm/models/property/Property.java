package com.cafe.crm.models.property;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "property")
public class Property {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@NotEmpty
	private String name;

	private Integer value;

	private Boolean enable;

	public Boolean getEnable() {
		return enable;
	}

	public void setEnabled(Boolean enabled) {
		enable = enabled;
	}

	public Property(String name, Integer value, Boolean enable) {
		this.name = name;
		this.value = value;
		this.enable = enable;
	}

	public Property() {
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Integer getValue() {

		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Property property = (Property) o;

		if (id != null ? !id.equals(property.id) : property.id != null) return false;
		if (name != null ? !name.equals(property.name) : property.name != null) return false;
		if (value != null ? !value.equals(property.value) : property.value != null)
			return false;
		return enable != null ? enable.equals(property.enable) : property.enable == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (value != null ? value.hashCode() : 0);
		result = 31 * result + (enable != null ? enable.hashCode() : 0);
		return result;
	}



}
