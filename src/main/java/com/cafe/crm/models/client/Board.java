package com.cafe.crm.models.client;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name ="boards")
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Calculate.class)
    @JoinTable(name = "boards_calculations",
            joinColumns = {@JoinColumn(name = "board_id")},
            inverseJoinColumns = {@JoinColumn(name = "calculate_id")})
    private Set<Calculate> calculate;

    public Board() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Calculate> getCalculate() {
        return calculate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCalculate(Set<Calculate> calculate) {
        this.calculate = calculate;
    }
}
