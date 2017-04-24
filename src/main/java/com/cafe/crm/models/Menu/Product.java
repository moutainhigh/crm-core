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


    @Column(name = "description")
    private String description;

    @Column(name="cost")
    private long cost;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Category.class)
    @JoinTable(name = "product_and_categories",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<Category>category;


    public Product() {
    }

    public Product(String name, String description, long cost) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<Category> getCategory() {
        return category;
    }

    public void setCategory(Set<Category> category) {
        this.category = category;
    }
}
