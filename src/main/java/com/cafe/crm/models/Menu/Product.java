package com.cafe.crm.models.Menu;


import javax.persistence.*;
import java.util.Set;


/**
 * Created by Sasha ins on 17.04.2017.
 */
@Entity
@Table(name = "product")
public class Product {


    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "specification")
    private String specification;

    @Column(name="cost")
    private long cost;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.PERSIST},mappedBy = "products")
    private Set<Category> categories;


    public Product() {
    }

    public Product(String name, String specification, long cost) {
        this.name = name;
        this.specification = specification;
        this.cost=cost;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
