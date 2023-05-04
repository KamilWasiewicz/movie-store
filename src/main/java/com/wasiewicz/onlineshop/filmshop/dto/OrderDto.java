package com.wasiewicz.onlineshop.filmshop.dto;

import com.wasiewicz.onlineshop.filmshop.model.Address;
import com.wasiewicz.onlineshop.filmshop.model.ShoppingCart;

import java.time.LocalDateTime;

public record OrderDto(
        Long id,
        String orderNumber,
        Long userId,
        ShoppingCart shoppingCart,
        Address address,
        LocalDateTime createdAt
) {
}
