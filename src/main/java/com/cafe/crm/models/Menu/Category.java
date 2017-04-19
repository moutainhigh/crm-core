package com.cafe.crm.models.Menu;

import com.cafe.crm.models.Role;
import com.cafe.crm.models.User;

import javax.persistence.*;
import java.util.List;
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

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Product.class)
    @JoinTable(name = "productPermissions",
            joinColumns = {@JoinColumn(name = "category_id",referencedColumnName = "id",nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "product_id",referencedColumnName = "id")})
    private Set<Product> products;



    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.PERSIST},mappedBy = "categories")
    private Set<Menu> menus;

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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }
}
