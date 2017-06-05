package com.cafe.crm.models;


import com.cafe.crm.models.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card, Long> {
	@Query("SELECT u FROM Card u WHERE u.id =:id")
	Card getCardById(@Param("id") Long id);

}
