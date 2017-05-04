package com.cafe.crm.models.client;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name ="clients")
public class Client {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private Double price;

    private String description;

    private LocalTime timeStart;

    private Long number;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Calculate.class)
    @JoinTable(name = "clients_calculate",
            joinColumns = {@JoinColumn(name = "client_id")},
            inverseJoinColumns = {@JoinColumn(name = "calculate_id")})
    private Calculate calculate;

     public Client() {
    }

    public Double getPrice() {
        return price;
    }

    public Long getNumber() {
        return number;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public Calculate getCalculate() {
        return calculate;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public void setCalculate(Calculate calculate) {
        this.calculate = calculate;
    }
}
