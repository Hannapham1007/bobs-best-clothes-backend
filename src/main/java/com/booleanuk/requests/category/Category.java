package com.booleanuk.requests.category;

import com.booleanuk.requests.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column String description;

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties(value = "category", allowSetters = true)
    private List<Product> products;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Category(int id) {
        this.id = id;
    }
}
