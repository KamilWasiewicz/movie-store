package com.wasiewicz.onlineshop.filmshop.controller;

import com.wasiewicz.onlineshop.filmshop.dto.FilmDTO;
import com.wasiewicz.onlineshop.filmshop.model.Film;
import com.wasiewicz.onlineshop.filmshop.service.FilmService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<FilmDTO> getFilms() {
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    public FilmDTO getSingleFilm(@PathVariable Long id) throws EntityNotFoundException {
        return filmService.getSingleFilm(id);
    }


    @PostMapping
    public FilmDTO addFilm(@RequestBody FilmDTO film) {
        return filmService.addFilm(film);
    }

    @PutMapping("/{id}")
    public FilmDTO editFilm(@PathVariable Long id,@RequestBody FilmDTO film) {
        return filmService.editFilm(film,id);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id) throws EntityNotFoundException {
        filmService.deleteFilm(id);
    }

}
