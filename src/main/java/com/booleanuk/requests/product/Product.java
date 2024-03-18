package com.booleanuk.requests.product;

import com.booleanuk.requests.cartItem.CartItem;
import com.booleanuk.requests.category.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private String description;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="category_id")
    @JsonIgnoreProperties("category")
    private Category category;

    @OneToOne
    @JoinColumn(name = "cart-item-id")
    CartItem cartItem;

    public Product(String title, float price, String imageURL, String description) {
        this.title = title;
        this.price = price;
        this.imageURL = imageURL;
        this.description = description;
    }

    public Product(int id) {this.id = id;}
}
