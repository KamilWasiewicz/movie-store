package com.wasiewicz.onlineshop.filmshop.controller;

import com.wasiewicz.onlineshop.filmshop.dto.FilmDTO;
import com.wasiewicz.onlineshop.filmshop.service.FilmService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/films")
@RequiredArgsConstructor
@CrossOrigin
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    @Cacheable(value = "films")
    public List<FilmDTO> getFilms(@RequestParam(required = false, defaultValue = "20") Integer limit) {
        return filmService.getFilms(limit);
    }

    @GetMapping("/{id}")
    public FilmDTO getSingleFilm(@PathVariable Long id) throws EntityNotFoundException {
        return filmService.getSingleFilm(id);
    }
    @GetMapping("/categories")
    public List<String> getFilmCategories() {
        return filmService.getAllCategories();
    }

    @GetMapping("/category/{category}")
    @Cacheable(value = "filmsByCategoryCache")
    public List<FilmDTO> getProductsInCategory(
            @RequestParam(required = false, defaultValue = "20") int limit,
            @PathVariable String category
            ) {
        return filmService.getFilmsByCategory(category, limit);
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
