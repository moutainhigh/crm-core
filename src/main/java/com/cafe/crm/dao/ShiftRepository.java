package com.cafe.crm.dao;

import com.cafe.crm.models.shift.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by User on 01.05.2017.
 */
public interface ShiftRepository extends JpaRepository<Shift, Long> {

}
