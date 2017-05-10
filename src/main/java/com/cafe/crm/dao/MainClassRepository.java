package com.cafe.crm.dao;

import com.cafe.crm.models.MainClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by User on 03.05.2017.
 */
@Repository
public interface MainClassRepository extends JpaRepository<MainClass, Long> {
}
