package com.cafe.crm.repositories.property;


import com.cafe.crm.models.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("select b from Property b where b.name = :name")
     Property getByName(@Param("name") String name);
}
