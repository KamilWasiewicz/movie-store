package com.wasiewicz.onlineshop.filmshop.dto;

import java.math.BigDecimal;

public record CartItemDto(
        Long id,
        FilmDTO filmDTO,
        int quantity,
        BigDecimal price
) {
}