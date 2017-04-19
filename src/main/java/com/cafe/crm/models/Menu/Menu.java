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

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Category.class)
    @JoinTable(name = "allmenu",
            joinColumns = {@JoinColumn(name = "menu_id",referencedColumnName = "id",nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "category_id",referencedColumnName = "id")})
    private Set<Category> categories;



    public Menu() {
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Menu(String name) {
        this.name = name;
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
}
