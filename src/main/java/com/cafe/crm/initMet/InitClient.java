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
        client.setName("Данил");
        client.setSurname("Джавер");
        client.setPhoto("тут фото");
        client.setExistenceCard(true);
        client.setDiscount((long)99);
        client.setBalance((long)5000);
        client.setSpend((long)18000);
        clientRepository.save(client);

        Client client1 = new Client();
        client1.setName("Дима");
        client1.setSurname("Джавер");
        client1.setPhoto("тут фото");
        client1.setExistenceCard(true);
        client1.setDiscount((long)99);
        client1.setBalance((long)3000);
        client1.setSpend((long)20000);
        clientRepository.save(client1);

        Client client2 = new Client();
        client2.setName("Саша");
        client2.setSurname("Джавер");
        client2.setPhoto("тут фото");
        client2.setExistenceCard(true);
        client2.setDiscount((long)99);
        client2.setBalance((long)7000);
        client2.setSpend((long)13000);
        clientRepository.save(client2);



        Calculate calculate = new Calculate();
        calculate.setTimeStart(new Date());
        calculate.setTimeStop(new Date());
        calculate.setClient(client);
        calculate.setMenu("туточки меню выдвигается");
        calculate.setTimePrice((long)555);
        calculate.setAllPrice((long)1200);
        calculateRepository.save(calculate);

        Calculate calculate1 = new Calculate();
        calculate1.setTimeStart(new Date());
        calculate1.setTimeStop(new Date());
        calculate1.setClient(client);
        calculate1.setMenu("туточки меню выдвигается");
        calculate1.setTimePrice((long)300);
        calculate1.setAllPrice((long)1000);
        calculateRepository.save(calculate1);



    }
}
