package com.cafe.crm.repositories.menu;

import com.cafe.crm.models.Menu.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
