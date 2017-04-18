package com.cafe.crm.models.Menu;

import com.cafe.crm.models.Role;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Sasna ins on 17.04.2017.
 */
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Product.class)
    @JoinTable(name = "productPermissions",
            joinColumns = {@JoinColumn(name = "category_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private Set<Role> products;


    @ManyToOne
    @JoinColumn(name = "menu_id")
    public Menu menu;

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getProducts() {
        return products;
    }

    public void setProducts(Set<Role> products) {
        this.products = products;
    }
}
