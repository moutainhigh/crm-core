package com.cafe.crm.models.shift;



import com.cafe.crm.models.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

    @Column(name = "checkValue")
    private Integer checkValue;//количество счетов за смену

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinTable(name = "usersOfShift",
            joinColumns = {@JoinColumn(name = "shift_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> users;

    public Set<User> getUsers() {
        return users;
    }


    public Shift(LocalDate dateShift, Integer checkValue, Set<User> users) {
        this.dateShift = dateShift;
        this.checkValue = checkValue;
        this.users = users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

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

    public Integer getCheckValue() {
        return checkValue;
    }

    public void setCheckValue(Integer checkValue) {
        this.checkValue = checkValue;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", dateShift=" + dateShift +
                ", checkValue=" + checkValue +
                '}';
    }
}
