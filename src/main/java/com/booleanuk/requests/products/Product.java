package com.booleanuk.requests.products;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private float price;

    @Column
    private String imageURL;

    @Column
    private int quantity;

    @Column
    private boolean isInStock;

    @Column
    private String description;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Product(String title, float price, String imageURL, int quantity, boolean isInStock, String description) {
        this.title = title;
        this.price = price;
        this.imageURL = imageURL;
        this.quantity = quantity;
        this.isInStock = isInStock;
        this.description = description;
    }

    public Product(int id) {this.id = id;}
}
