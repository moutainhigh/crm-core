package com.cafe.crm.repositories.menu;


import com.cafe.crm.models.menu.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsRepository extends JpaRepository<Ingredients,Long>{

	Ingredients findByName(String name);

}
