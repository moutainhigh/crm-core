package com.cafe.crm.dao.menu;

import com.cafe.crm.models.Menu.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
