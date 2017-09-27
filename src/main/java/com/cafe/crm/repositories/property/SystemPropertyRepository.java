package com.cafe.crm.repositories.property;


import com.cafe.crm.models.property.AllSystemProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemPropertyRepository extends JpaRepository<AllSystemProperty, Long> {

	AllSystemProperty findByNameIgnoreCaseAndCompanyId(String name, Long companyId);

	void deleteByNameAndCompanyId(String name, Long companyId);

	List<AllSystemProperty> findByCompanyId(Long companyId);
}
