package com.cafe.crm.service.counting;


import java.time.LocalDate;

public interface CountingService {

    void shiftByDate(LocalDate start, LocalDate end);

    void countShift();

    void totalSalary();
}
