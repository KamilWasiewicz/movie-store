package com.wasiewicz.onlineshop.filmshop.service;

import com.wasiewicz.onlineshop.filmshop.dto.FilmDTO;
import com.wasiewicz.onlineshop.filmshop.model.Film;
import com.wasiewicz.onlineshop.filmshop.repository.FilmRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;

    public List<FilmDTO> getFilms() {
        List<Film> films = filmRepository.findAll();
        return films
                .stream()
                .map(this::getDtoFromFilm)
                .collect(Collectors.toList());
    }

    private FilmDTO getDtoFromFilm(Film film) {
        return new FilmDTO(film);
    }

    private Film getFilmFromDto(FilmDTO filmDTO) {
        return new Film(filmDTO);
    }

    public Film getSingleFilm(long id) throws EntityNotFoundException {
        Optional<Film> singleFilm = filmRepository.findById(id);
        if (singleFilm.isEmpty())
            throw new EntityNotFoundException("Product id is invalid " + id);
        return singleFilm.get();
    }


    public Film getFilmByRating(double rating) {
        return filmRepository.findByRating(rating).orElseThrow();
    }

    @Transactional
    public Film addFilm(FilmDTO filmDTO) {
        Film film = getFilmFromDto(filmDTO);
        return filmRepository.save(film);
    }


    @Transactional
    public Film editFilm(FilmDTO filmDTO) {
        Film filmEdited = getFilmFromDto(filmDTO);
        filmEdited.setTitle(filmDTO.getTitle());
        filmEdited.setDirector(filmDTO.getDirector());
        filmEdited.setCategory(filmDTO.getCategory());
        filmEdited.setDescription(filmDTO.getDescription());
        filmEdited.setRating(filmDTO.getRating());
        filmEdited.setPrice(filmDTO.getPrice());
        filmEdited.setInStock(filmDTO.getInStock());
        filmEdited.setImageUrl(filmDTO.getImageUrl());
        filmEdited.setStars(filmDTO.getStars());
        return filmRepository.save(filmEdited);
    }

    public void deleteFilm(long id) throws EntityNotFoundException {
        if (!filmRepository.existsById(id)) {
            throw new EntityNotFoundException("Film with id " + id + " does not exist");
        }
        filmRepository.deleteById(id);
    }
}


