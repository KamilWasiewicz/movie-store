package com.wasiewicz.onlineshop.filmshop.mapper;

import com.wasiewicz.onlineshop.filmshop.dto.FilmDTO;
import com.wasiewicz.onlineshop.filmshop.model.Film;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class FilmDtoMapper implements Function<Film, FilmDTO> {
    @Override
    public FilmDTO apply(Film film) {
        return new FilmDTO(
                film.getId(),
                film.getTitle(),
                film.getDirector(),
                film.getStars(),
                film.getCategory(),
                film.getDescription(),
                film.getRating(),
                film.getPrice(),
                film.getInStock(),
                film.getImageUrl()
        );
    }
}
