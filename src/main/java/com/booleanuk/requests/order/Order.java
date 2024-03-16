package com.booleanuk.requests.order;

import com.booleanuk.requests.cartItem.CartItem;
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
@Table(name = "shopping-sessions")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private float total;

    @OneToMany(mappedBy = "order")
    @JsonIgnoreProperties(value = "order", allowSetters = true)
    private List<CartItem> cartItems;

    public Order(float total) {
        this.total = total;
    }

    public Order(int id) {
        this.id = id;
    }
}
