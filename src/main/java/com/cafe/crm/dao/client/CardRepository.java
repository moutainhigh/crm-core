package com.cafe.crm.dao.client;

import com.cafe.crm.models.client.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}

