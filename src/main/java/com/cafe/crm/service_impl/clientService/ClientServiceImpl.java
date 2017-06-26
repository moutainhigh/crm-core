package com.cafe.crm.service_impl.clientService;

import com.cafe.crm.dao.client.ClientRepository;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.service_abstract.clientService.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{
    @Autowired
    private ClientRepository clientRepository;

    public void save(Client client) {
        clientRepository.saveAndFlush(client);
    }

    public void saveAll(List<Client> clients) {
        clientRepository.save(clients);
    }

    public void delete(Client client) {
        clientRepository.delete(client);
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Client getOne(Long id) {
        return clientRepository.findOne(id);
    }

    public List<Client> getAllOpen() {
        return clientRepository.getAllOpen();
    }

    @Override
    public List<Client> findByEmailNotNullAndAdvertisingIsTrue() {
        return clientRepository.findByEmailNotNullAndAdvertisingIsTrue();
    }
}
