package com.cafe.crm.dao.property;


import com.cafe.crm.models.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property,Long>{
}
