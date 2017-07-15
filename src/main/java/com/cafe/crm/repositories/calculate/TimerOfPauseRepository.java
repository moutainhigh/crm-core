package com.cafe.crm.repositories.calculate;


import com.cafe.crm.models.client.TimerOfPause;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerOfPauseRepository extends JpaRepository<TimerOfPause, Long> {


	TimerOfPause findTimerOfPauseByIdOfCalculate(Long id);

}
