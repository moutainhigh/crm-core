package com.cafe.crm.initMet;

import com.cafe.crm.dao.client.ClientRepository;
import com.cafe.crm.dao.client.CalculateRepository;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InitClient {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CalculateRepository calculateRepository;


    public void init() {

        Client client = new Client();
        client.setName("Danil");
        client.setSurname("Джавер");
        client.setPhoto(null);
        client.setExistenceCard(false);
        client.setDiscount(null);
        client.setBalance(null);
        client.setSpend(null);
        clientRepository.save(client);

        Calculate calculate = new Calculate();
        calculate.setTimeStart(new Date());
        calculate.setTimeStop(new Date());
        calculate.setClient(client);
        calculate.setMenu(null);
        calculate.setTimePrice((long)555);
        calculate.setAllPrice((long)123123);
        calculateRepository.save(calculate);


    }
}
