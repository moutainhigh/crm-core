package com.cafe.crm.models.shift;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * СМЕНЫ (первоначальный вариант)
 */

@Entity
@Table(name = "Shift")
public class Shift {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "dateShift")
    private LocalDate dateShift;

    @Column(name = "checkCount")
    private Integer checkCount;

    public Shift() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateShift() {
        return dateShift;
    }

    public void setDateShift(LocalDate dateShift) {
        this.dateShift = dateShift;
    }

    public Integer getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(Integer checkCount) {
        this.checkCount = checkCount;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", dateShift=" + dateShift +
                ", checkValue=" + checkCount +
                '}';
    }
}
