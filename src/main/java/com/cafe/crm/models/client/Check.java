package com.cafe.crm.models.client;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "check")
public class Check implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private Long timeStart;

    private Long timeStop;

    private String menu;

    private Long allPrice;
/*
    @OneToOne(fetch = FetchType.EAGER, targetEntity = Client.class)
    @JoinTable(name = "Client-Check",
            joinColumns = {@JoinColumn(name = "check_id")},
            inverseJoinColumns = {@JoinColumn(name = "client_id")})
    private Client client;
*/
    public Long getId() {
        return id;
    }

    public Long getTimeStart() {
        return timeStart;
    }

    public Long getTimeStop() {
        return timeStop;
    }

    public String getMenu() {
        return menu;
    }

    public Long getAllPrice() {
        return allPrice;
    }
/*
    public Client getClient() {
        return client;
    }
*/
    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeStop(Long timeStop) {
        this.timeStop = timeStop;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public void setAllPrice(Long allPrice) {
        this.allPrice = allPrice;
    }
/*
    public void setClient(Client client) {
        this.client = client;
    }
    */
}
