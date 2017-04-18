package com.cafe.crm.dao.dao_menu;

import com.cafe.crm.models.Menu.Product;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by User on 18.04.2017.
 */
public interface ProductRepository extends JpaRepository<Product,Long> {
}
