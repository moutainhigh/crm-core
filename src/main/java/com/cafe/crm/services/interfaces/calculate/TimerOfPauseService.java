package com.cafe.crm.services.interfaces.calculate;


import com.cafe.crm.models.client.TimerOfPause;

public interface TimerOfPauseService {

    TimerOfPause getOne(Long id);

    void save(TimerOfPause timer);

    TimerOfPause findTimerOfPauseByIdOfClient(Long id);

}
