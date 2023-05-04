package com.wasiewicz.onlineshop.filmshop.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record ShoppingCartDto(
        Long id,
        Long userId,
        List<CartItemDto> cartItems,
        BigDecimal totalPrice

) {
}
