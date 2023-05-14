package com.wasiewicz.onlineshop.filmshop.service;

import com.wasiewicz.onlineshop.filmshop.dto.FilmDTO;
import com.wasiewicz.onlineshop.filmshop.mapper.FilmDtoMapper;
import com.wasiewicz.onlineshop.filmshop.model.Film;
import com.wasiewicz.onlineshop.filmshop.repository.FilmRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FilmService {


    private final FilmRepository filmRepository;
    private final FilmDtoMapper filmDtoMapper;

    public List<FilmDTO> getFilms(int limit) {
        Page<Film> films = filmRepository.findAll(PageRequest.of(0,limit));
        return films
                .stream()
                .map(filmDtoMapper)
                .collect(Collectors.toList());
    }

    public FilmDTO getSingleFilm(Long id) throws EntityNotFoundException {
        var singleFilm = filmRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product id is invalid " + id));
        return filmDtoMapper.apply(singleFilm);
    }
    public List<String> getAllCategories() {
        List<String> categories = filmRepository.findDistinctCategories();
        return categories.stream()
                .flatMap(category -> Stream.of(category.split("[,;]")))
                .map(String::trim)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<FilmDTO> getFilmsByCategory(String category, int limit) {
        List<Film> films = filmRepository.findByCategory("%" + category + "%", PageRequest.of(0, limit));
        return films
                .stream()
                .map(filmDtoMapper)
                .collect(Collectors.toList());
    }


    @Transactional
    public FilmDTO addFilm(FilmDTO filmDTO) {
        String title = filmDTO.title();
        if (title != null && filmRepository.findByTitle(title).isPresent())
            throw new EntityExistsException("Product with title " + title + " already exist");
        Film film = new Film();
        return getFilmDtoAndSave(filmDTO, film);
    }

    @Transactional
    public FilmDTO editFilm(FilmDTO filmDTO, Long id) {
        var filmEdited = filmRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product id is invalid " + id));

        return getFilmDtoAndSave(filmDTO, filmEdited);
    }

    public void deleteFilm(Long id) throws EntityNotFoundException {
        if (!filmRepository.existsById(id)) {
            throw new EntityNotFoundException("Film with id " + id + " does not exist");
        }
        filmRepository.deleteById(id);
    }

    private FilmDTO getFilmDtoAndSave(FilmDTO filmDTO, Film filmEdited) {
        filmEdited.setTitle(filmDTO.title());
        filmEdited.setDirector(filmDTO.director());
        filmEdited.setStars(filmDTO.stars());
        filmEdited.setCategory(filmDTO.category());
        filmEdited.setDescription(filmDTO.description());
        filmEdited.setRating(filmDTO.rating());
        filmEdited.setPrice(filmDTO.price());
        filmEdited.setInStock(filmDTO.inStock());
        filmEdited.setImageUrl(filmDTO.imageUrl());
        filmEdited = filmRepository.save(filmEdited);
        return filmDtoMapper.apply(filmEdited);
    }


}


