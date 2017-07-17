package com.cafe.crm.repositories.card;

import com.cafe.crm.models.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("SELECT u FROM Card u WHERE u.id =:id")
    Card getCardById(@Param("id") Long id);

    Card findByEmail(String email);

    Card findBySurname(String name);

    Card findByPhoneNumber(String phone);

    Card findByNameAndSurname(String name, String surname);

    Card findBySurnameAndName(String surname, String name);

    @Query("SELECT u FROM Card u WHERE u.surname =:name")
    List<Card> findByListSurname(@Param("name") String name);

    List<Card> findByEmailNotNullAndAdvertisingIsTrue();

}

