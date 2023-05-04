package com.wasiewicz.onlineshop.filmshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<CartItem> cartItems = new ArrayList<>();

    private BigDecimal totalPrice;
}
