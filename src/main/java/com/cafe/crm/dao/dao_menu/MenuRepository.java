package com.cafe.crm.dao.dao_menu;

import com.cafe.crm.models.Menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by User on 18.04.2017.
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
