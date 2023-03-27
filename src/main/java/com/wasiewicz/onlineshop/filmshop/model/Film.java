package com.wasiewicz.onlineshop.filmshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private double price;
    @NotNull
    private int inStock;
    @NotNull
    @NotEmpty
    private String imageUrl;

}
