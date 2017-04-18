package com.cafe.crm.models.client;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "clients")
public class Client implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Client() {
    }

    public Client(String name) {
        this.name = name;
    }

    /*
    @Id
    @GeneratedValue
    private Long id;

    private String photo;

    private String name;

    private Long discount;

    private Long balanceCard;

    private Long spentCard;

    public Client() {
    }


        @OneToOne(fetch = FetchType.EAGER, targetEntity = Client.class)
        @JoinTable(name = "Client-Check",
                joinColumns = {@JoinColumn(name = "client_id")},
                inverseJoinColumns = {@JoinColumn(name = "check_id")})
        private Check check;


    public Long getId() {
        return id;
    }

    public String getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public boolean isQrCard() {
        return qrCard;
    }

    public Long getDiscount() {
        return discount;
    }

    public Long getBalanceCard() {
        return balanceCard;
    }

    public Long getSpentCard() {
        return spentCard;
    }

    public Check getCheck() {
        return check;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQrCard(boolean qrCard) {
        this.qrCard = qrCard;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public void setBalanceCard(Long balanceCard) {
        this.balanceCard = balanceCard;
    }

    public void setSpentCard(Long spentCard) {
        this.spentCard = spentCard;
    }

    public void setCheck(Check check) {
        this.check = check;
    }
    */
}
