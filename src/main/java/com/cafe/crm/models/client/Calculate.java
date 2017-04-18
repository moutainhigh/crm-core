package com.cafe.crm.models.client;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="calculate")
public class Calculate {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private Date timeStart;

    private Date timeStop;

    private String menu;

    private Long timePrice;

    private Long allPrice;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = Client.class)
    @JoinTable(name = "client_calculate",
            joinColumns = {@JoinColumn(name = "calculate_id")},
            inverseJoinColumns = {@JoinColumn(name = "client_id")})
    private Client client;

    public Calculate() {
    }

    public Long getId() {
        return id;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public Date getTimeStop() {
        return timeStop;
    }

    public String getMenu() {
        return menu;
    }

    public Long getTimePrice() {
        return timePrice;
    }

    public Long getAllPrice() {
        return allPrice;
    }

    public Client getClient() {
        return client;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeStop(Date timeStop) {
        this.timeStop = timeStop;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public void setTimePrice(Long timePrice) {
        this.timePrice = timePrice;
    }

    public void setAllPrice(Long allPrice) {
        this.allPrice = allPrice;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
