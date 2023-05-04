package com.wasiewicz.onlineshop.filmshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "quantity")
    private int quantity;
    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @OneToOne
    @JoinColumn(name = "film_id", referencedColumnName = "id")
    private Film film;

}