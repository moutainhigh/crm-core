package com.cafe.crm.initMet;

import com.cafe.crm.dao.client.CheckRepository;
import com.cafe.crm.dao.client.ClientRepository;
import com.cafe.crm.models.client.Check;
import com.cafe.crm.models.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InitClient {

    @Autowired
    private ClientRepository clientRepository;

  // @Autowired
  // private CheckRepository checkRepository;


    public void init() {
        clientRepository.save(new Client("name"));

/*
        Client client = new Client();
        client.setBalanceCard((long)500);
        client.setDiscount((long)10);
        client.setName("Danil");
        client.setPhoto("тут фото");
        //client.setQrCard(true);
        client.setSpentCard((long)5000);
        clientRepository.save(client);

        Check check = new Check();
        check.setAllPrice((long)5900);
        check.setMenu("тут меню");
        check.setTimeStart(new Date().getTime());
        check.setTimeStop(new Date().getTime());
        checkRepository.save(check);
*/


    }
}
