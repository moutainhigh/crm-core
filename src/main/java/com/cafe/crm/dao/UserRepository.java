package com.cafe.crm.dao;

import com.cafe.crm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User,Long> {

    // для логина
    @Query("SELECT u FROM User u WHERE u.login =:name")
    User getUserByLogin(@Param("name")String login);

    // для смены
    @Query("SELECT u FROM User u WHERE u.name =:name")
    User getUserByNameForShift(@Param("name")String name);

}
