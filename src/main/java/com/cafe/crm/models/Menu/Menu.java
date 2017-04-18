package com.cafe.crm.models.Menu;

import com.cafe.crm.models.Role;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Sasha ins on 17.04.2017.
 */
@Entity
@Table(name="menu")
public class Menu {


    @Id
    @GeneratedValue
    private long id;


    @Column(name="name")
     private String name;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Category.class)
    @JoinTable(name = "productPermissions",
            joinColumns = {@JoinColumn(name = "category_id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    private Set<Role> categories;



    public Menu() {
    }



}
