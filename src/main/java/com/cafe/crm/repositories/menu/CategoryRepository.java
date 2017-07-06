package com.cafe.crm.repositories.menu;

import com.cafe.crm.models.Menu.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
}
