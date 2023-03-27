package com.wasiewicz.onlineshop.filmshop.controller;

import com.wasiewicz.onlineshop.filmshop.model.Film;
import com.wasiewicz.onlineshop.filmshop.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<Film> getFilms() {
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    public Film getSingleFilm(@PathVariable long id) {
        return filmService.getSingleFilm(id);
    }

    @GetMapping("/ratings/{rating}")
    public Film getFilmByRating(@PathVariable double rating) {
        return filmService.getFilmByRating(rating);
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping("/{id}")
    public Film editFilm(@RequestBody Film film) {
        return filmService.editFilm(film);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable long id) {
        filmService.deleteFilm(id);
    }

}
