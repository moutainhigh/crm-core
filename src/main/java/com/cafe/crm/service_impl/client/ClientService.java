package com.cafe.crm.service_impl.client;

import com.cafe.crm.dao.client.ClientRepository;
import com.cafe.crm.models.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public void add(Client client) {
        clientRepository.saveAndFlush(client);
    }

    public void delete(Client client) {
        clientRepository.delete(client);
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Client getOne(Long id) {
        return clientRepository.getOne(id);
    }
}
