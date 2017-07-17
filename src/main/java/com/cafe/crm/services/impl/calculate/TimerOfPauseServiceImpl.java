package com.cafe.crm.services.impl.calculate;

import com.cafe.crm.models.client.TimerOfPause;
import com.cafe.crm.repositories.calculate.TimerOfPauseRepository;
import com.cafe.crm.services.interfaces.calculate.TimerOfPauseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimerOfPauseServiceImpl implements TimerOfPauseService {

    @Autowired
    private TimerOfPauseRepository timerOfPauseRepository;

    @Override
    public TimerOfPause getOne(Long id) {
        return timerOfPauseRepository.getOne(id);
    }

    @Override
    public void save(TimerOfPause timer) {
        timerOfPauseRepository.saveAndFlush(timer);
    }

    @Override
    public TimerOfPause findTimerOfPauseByIdOfClient(Long id) {
        return timerOfPauseRepository.findTimerOfPauseByIdOfClient(id);
    }
}
