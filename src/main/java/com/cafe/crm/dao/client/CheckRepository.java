package com.cafe.crm.dao.client;

import com.cafe.crm.models.client.Check;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CheckRepository extends JpaRepository<Check, Long> {
}
