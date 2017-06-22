package com.cafe.crm.models.advertising;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Advertising {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private String imageUrl;
}
