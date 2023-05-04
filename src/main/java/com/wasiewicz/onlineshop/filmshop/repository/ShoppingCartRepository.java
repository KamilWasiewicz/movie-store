package com.wasiewicz.onlineshop.filmshop.repository;

import com.wasiewicz.onlineshop.filmshop.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
