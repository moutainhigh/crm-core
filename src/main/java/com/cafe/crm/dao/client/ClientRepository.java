package com.cafe.crm.dao.client;

import com.cafe.crm.models.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
