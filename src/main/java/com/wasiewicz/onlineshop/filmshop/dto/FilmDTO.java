package com.wasiewicz.onlineshop.filmshop.dto;

import com.wasiewicz.onlineshop.filmshop.model.Film;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmDTO {

    public FilmDTO(Film film) {
        this.setTitle(film.getTitle());
        this.setDirector(film.getDirector());
        this.setCategory(film.getCategory());
        this.setDescription(film.getDescription());
        this.setRating(film.getRating());
        this.setPrice(film.getPrice());
        this.setInStock(film.getInStock());
        this.setImageUrl(film.getImageUrl());
        this.setStars(film.getStars());
    }
    @NotNull
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String director;

    @NotBlank
    private String stars;

    @NotBlank
    private String category;

    @NotBlank
    private String description;

    @NotNull
    @Min(value = 0)
    private double rating;

    @NotNull
    @Min(value = 0)
    private BigDecimal price;

    @NotNull
    @Min(value = 0)
    private Integer inStock;

    @NotBlank
    private String imageUrl;


}
