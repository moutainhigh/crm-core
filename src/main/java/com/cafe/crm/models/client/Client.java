package com.cafe.crm.models.client;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name ="clients")
public class Client {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String name;

    private String surname;

    private String photo;

    private boolean existenceCard;

    private Long discount;

    private Long balance;

    private Long spend;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Calculate.class)
    @JoinTable(name = "client_calculate",
            joinColumns = {@JoinColumn(name = "client_id")},
            inverseJoinColumns = {@JoinColumn(name = "calculate_id")})
    private Set<Calculate> calculate;

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoto() {
        return photo;
    }

    public boolean isExistenceCard() {
        return existenceCard;
    }

    public Long getDiscount() {
        return discount;
    }

    public Long getBalance() {
        return balance;
    }

    public Long getSpend() {
        return spend;
    }

    public Set<Calculate> getCalculate() {
        return calculate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setExistenceCard(boolean existenceCard) {
        this.existenceCard = existenceCard;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void setSpend(Long spend) {
        this.spend = spend;
    }

    public void setCalculate(Set<Calculate> calculate) {
        this.calculate = calculate;
    }
}
