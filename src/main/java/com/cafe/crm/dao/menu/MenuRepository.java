package com.cafe.crm.dao.menu;

import com.cafe.crm.models.Menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
