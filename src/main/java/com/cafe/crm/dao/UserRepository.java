package com.cafe.crm.dao;

import com.cafe.crm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by User on 18.04.2017.
 */
public interface UserRepository extends JpaRepository<User,Long> {
}
