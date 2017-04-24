package com.cafe.crm.dao.client;

import com.cafe.crm.models.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<Client, Long> {

}
