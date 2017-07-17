package com.cafe.crm.models.goods;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table(name = "goods_category")
public class GoodsCategory {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Поле \"Название\" не может быть пустым")
    @Column(unique = true)
    private String name;

    public GoodsCategory() {
    }

    public GoodsCategory(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoodsCategory that = (GoodsCategory) o;

        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GoodsCategory{" +
                "name='" + name + '\'' +
                '}';
    }
}
