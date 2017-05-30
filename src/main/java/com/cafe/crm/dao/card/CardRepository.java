package com.cafe.crm.dao.card;

import com.cafe.crm.models.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}

