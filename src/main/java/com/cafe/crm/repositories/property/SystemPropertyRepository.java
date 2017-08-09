package com.cafe.crm.repositories.property;


import com.cafe.crm.models.property.AllSystemProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemPropertyRepository extends JpaRepository<AllSystemProperty, Long> {

	AllSystemProperty findByNameIgnoreCase(String name);

	void deleteByName(String name);

}
