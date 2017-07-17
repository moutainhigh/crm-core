package com.cafe.crm.models.menu;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotEmpty
    @Length(min = 1, max = 20)
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Product> products;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "categories")
    private Set<Menu> menus;

    private boolean dirtyProfit = true;

    private boolean floatingPrice;

    public Category(String name) {
        this.name = name;
    }

    public Category() {

    }

    public Long getId() {
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

    public boolean isDirtyProfit() {
        return dirtyProfit;
    }

    public void setDirtyProfit(boolean dirtyProfit) {
        this.dirtyProfit = dirtyProfit;
    }

    public boolean isFloatingPrice() {
        return floatingPrice;
    }

    public void setFloatingPrice(boolean floatingPrice) {
        this.floatingPrice = floatingPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != category.id) return false;
        return name != null ? name.equals(category.name) : category.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
