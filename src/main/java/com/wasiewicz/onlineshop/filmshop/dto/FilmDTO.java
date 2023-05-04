package com.wasiewicz.onlineshop.filmshop.dto;

import java.math.BigDecimal;


public record FilmDTO(
        Long id,
        String title,
        String director,
        String stars,
        String category,
        String description,
        double rating,
        BigDecimal price,
        Integer inStock,
        String imageUrl
) {


}
