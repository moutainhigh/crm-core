package com.cafe.crm.repositories.client;

import com.cafe.crm.models.card.Card;
import com.cafe.crm.models.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c where c.state = true AND c.deleteState = false")
    List<Client> getAllOpen();

    List<Client> findByIdIn(long[] ids);

    List<Client> findByCardId(Long cardId);

    @Query("SELECT c.card FROM Client c WHERE c.id IN ?1 ")
    Set<Card> findCardByClientIdIn(long[] clientsIds);

}
