package com.wasiewicz.onlineshop.filmshop.model;

import com.wasiewicz.onlineshop.filmshop.dto.FilmDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String director;

    @NotNull
    @NotEmpty
    private String stars;

    @NotNull
    @NotEmpty
    private String category;

    @NotNull
    @NotEmpty
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @NotNull
    private double rating;

    @NotNull
    private BigDecimal price;

    @NotNull
    private int inStock;

    @NotNull
    @NotEmpty
    private String imageUrl;

    public Film(FilmDTO filmDTO) {
        this.title = filmDTO.getTitle();
        this.director = filmDTO.getDirector();
        this.category = filmDTO.getCategory();
        this.description = filmDTO.getDescription();
        this.rating = filmDTO.getRating();
        this.price = filmDTO.getPrice();
        this.inStock = filmDTO.getInStock();
        this.imageUrl = filmDTO.getImageUrl();
        this.stars = filmDTO.getStars();
    }
}
