package com.cafe.crm.repositories.property;


import com.cafe.crm.models.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("select b from Property b where b.name = :name AND b.company.id = :companyId")
     Property getByNameAndCompanyId(@Param("name") String name,
                                    @Param("companyId") Long companyId);

    List<Property> findByCompanyId(Long companyId);
}
