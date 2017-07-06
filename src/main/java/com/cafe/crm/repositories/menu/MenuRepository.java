package com.cafe.crm.repositories.menu;

import com.cafe.crm.models.Menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MenuRepository extends JpaRepository<Menu, Long> {
}
