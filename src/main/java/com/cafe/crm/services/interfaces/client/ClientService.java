package com.cafe.crm.services.interfaces.client;


import com.cafe.crm.models.client.Client;

import java.util.List;

public interface ClientService {

	void save(Client client);

	void saveAll(List<Client> clients);

	void delete(Client client);

	List<Client> getAll();

	Client getOne(Long id);

	List<Client> getAllOpen();

	List<Client> findByIdIn(long[] ids);

	List<Client> findByEmailNotNullAndAdvertisingIsTrue();

}
