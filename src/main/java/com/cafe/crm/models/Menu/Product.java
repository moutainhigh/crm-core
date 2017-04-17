package com.cafe.crm.models.Menu;


import javax.persistence.*;


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


    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category category;

    public Product() {
    }

    public Product(String name, String specification) {
        this.name = name;
        this.specification = specification;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
