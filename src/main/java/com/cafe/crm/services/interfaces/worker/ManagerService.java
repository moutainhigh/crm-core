package com.cafe.crm.services.interfaces.worker;

import com.cafe.crm.models.worker.Manager;


public interface ManagerService {

    Manager getUserByEmail(String login);

    void save(Manager manager);
}
