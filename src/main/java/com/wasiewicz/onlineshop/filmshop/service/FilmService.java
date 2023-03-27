package com.wasiewicz.onlineshop.filmshop.service;

import com.wasiewicz.onlineshop.filmshop.model.Film;
import com.wasiewicz.onlineshop.filmshop.repository.FilmRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;

    public List<Film> getFilms() {
        return filmRepository.findAll();
    }

    public Film getSingleFilm(long id) {
        return filmRepository.findById(id).orElseThrow();
    }


    public Film getFilmByRating(double rating) {
        return filmRepository.findByRating(rating).orElseThrow();
    }

    @Transactional
    public Film addFilm(Film film) {
        return filmRepository.save(film);
    }

    @Transactional
    public Film editFilm(Film film) {
        Film filmEdited = filmRepository.findById(film.getId()).orElseThrow();
        filmEdited.setTitle(film.getTitle());
        filmEdited.setDirector(film.getDirector());
        filmEdited.setCategory(film.getCategory());
        filmEdited.setDescription(film.getDescription());
        filmEdited.setRating(film.getRating());
        filmEdited.setPrice(film.getPrice());
        filmEdited.setInStock(film.getInStock());
        filmEdited.setImageUrl(film.getImageUrl());
        filmEdited.setStars(film.getStars());
        return filmEdited;
    }

    public void deleteFilm(long id) {
        filmRepository.deleteById(id);
    }
}

