package com.wasiewicz.onlineshop.filmshop.service;

import com.wasiewicz.onlineshop.filmshop.dto.FilmDTO;
import com.wasiewicz.onlineshop.filmshop.mapper.FilmDtoMapper;
import com.wasiewicz.onlineshop.filmshop.model.Film;
import com.wasiewicz.onlineshop.filmshop.repository.FilmRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmRepository mockFilmRepository;
    @Mock
    private FilmDtoMapper mockFilmDtoMapper;

    private FilmService filmServiceUnderTest;

    @BeforeEach
    void setUp() {
        filmServiceUnderTest = new FilmService(mockFilmRepository, mockFilmDtoMapper);
    }

    @Test
    void testGetFilms() {
        // Setup
        final List<FilmDTO> expectedResult = List.of(
                new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0, new BigDecimal("0.00"), 0,
                        "imageUrl"));

        // Configure FilmRepository.findAll(...).
        final Film film = new Film();
        film.setTitle("title");
        film.setDirector("director");
        film.setStars("stars");
        film.setCategory("category");
        film.setDescription("description");
        film.setRating(0.0);
        film.setPrice(new BigDecimal("0.00"));
        film.setInStock(0);
        film.setImageUrl("imageUrl");
        final List<Film> films = List.of(film);
        when(mockFilmRepository.findAll()).thenReturn(films);

        // Run the test
        final List<FilmDTO> result = filmServiceUnderTest.getFilms();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetFilms_FilmRepositoryReturnsNoItems() {
        // Setup
        when(mockFilmRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<FilmDTO> result = filmServiceUnderTest.getFilms();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetSingleFilm() {
        // Setup
        final FilmDTO expectedResult = new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0,
                new BigDecimal("0.00"), 0, "imageUrl");

        // Configure FilmRepository.findById(...).
        final Film film1 = new Film();
        film1.setTitle("title");
        film1.setDirector("director");
        film1.setStars("stars");
        film1.setCategory("category");
        film1.setDescription("description");
        film1.setRating(0.0);
        film1.setPrice(new BigDecimal("0.00"));
        film1.setInStock(0);
        film1.setImageUrl("imageUrl");
        final Optional<Film> film = Optional.of(film1);
        when(mockFilmRepository.findById(0L)).thenReturn(film);

        // Configure FilmDtoMapper.apply(...).
        final FilmDTO filmDTO = new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0,
                new BigDecimal("0.00"), 0, "imageUrl");
        final Film film2 = new Film();
        film2.setTitle("title");
        film2.setDirector("director");
        film2.setStars("stars");
        film2.setCategory("category");
        film2.setDescription("description");
        film2.setRating(0.0);
        film2.setPrice(new BigDecimal("0.00"));
        film2.setInStock(0);
        film2.setImageUrl("imageUrl");
        when(mockFilmDtoMapper.apply(film2)).thenReturn(filmDTO);

        // Run the test
        final FilmDTO result = filmServiceUnderTest.getSingleFilm(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetSingleFilm_FilmRepositoryReturnsAbsent() {
        // Setup
        when(mockFilmRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> filmServiceUnderTest.getSingleFilm(0L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void testAddFilm_ThrowsEntityExistsException() {
        // Setup
        final FilmDTO filmDTO = new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0,
                new BigDecimal("0.00"), 0, "imageUrl");

        // Configure FilmRepository.findByTitle(...).
        final Film film1 = new Film();
        film1.setTitle("title");
        film1.setDirector("director");
        film1.setStars("stars");
        film1.setCategory("category");
        film1.setDescription("description");
        film1.setRating(0.0);
        film1.setPrice(new BigDecimal("0.00"));
        film1.setInStock(0);
        film1.setImageUrl("imageUrl");
        final Optional<Film> film = Optional.of(film1);
        when(mockFilmRepository.findByTitle("title")).thenReturn(film);

        // Run the test
        assertThatThrownBy(() -> filmServiceUnderTest.addFilm(filmDTO)).isInstanceOf(EntityExistsException.class);
    }

    @Test
    void testAddFilm_FilmRepositoryFindByTitleReturnsAbsent() {
        // Setup
        final FilmDTO filmDTO = new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0,
                new BigDecimal("0.00"), 0, "imageUrl");
        final FilmDTO expectedResult = new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0,
                new BigDecimal("0.00"), 0, "imageUrl");
        when(mockFilmRepository.findByTitle("title")).thenReturn(Optional.empty());

        // Configure FilmRepository.save(...).
        final Film film = new Film();
        film.setTitle("title");
        film.setDirector("director");
        film.setStars("stars");
        film.setCategory("category");
        film.setDescription("description");
        film.setRating(0.0);
        film.setPrice(new BigDecimal("0.00"));
        film.setInStock(0);
        film.setImageUrl("imageUrl");
        final Film entity = new Film();
        entity.setTitle("title");
        entity.setDirector("director");
        entity.setStars("stars");
        entity.setCategory("category");
        entity.setDescription("description");
        entity.setRating(0.0);
        entity.setPrice(new BigDecimal("0.00"));
        entity.setInStock(0);
        entity.setImageUrl("imageUrl");
        when(mockFilmRepository.save(entity)).thenReturn(film);

        // Configure FilmDtoMapper.apply(...).
        final FilmDTO filmDTO1 = new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0,
                new BigDecimal("0.00"), 0, "imageUrl");
        final Film film1 = new Film();
        film1.setTitle("title");
        film1.setDirector("director");
        film1.setStars("stars");
        film1.setCategory("category");
        film1.setDescription("description");
        film1.setRating(0.0);
        film1.setPrice(new BigDecimal("0.00"));
        film1.setInStock(0);
        film1.setImageUrl("imageUrl");
        when(mockFilmDtoMapper.apply(film1)).thenReturn(filmDTO1);

        // Run the test
        final FilmDTO result = filmServiceUnderTest.addFilm(filmDTO);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testEditFilm() {
        // Setup
        final FilmDTO filmDTO = new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0,
                new BigDecimal("0.00"), 0, "imageUrl");
        final FilmDTO expectedResult = new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0,
                new BigDecimal("0.00"), 0, "imageUrl");

        // Configure FilmRepository.findById(...).
        final Film film1 = new Film();
        film1.setTitle("title");
        film1.setDirector("director");
        film1.setStars("stars");
        film1.setCategory("category");
        film1.setDescription("description");
        film1.setRating(0.0);
        film1.setPrice(new BigDecimal("0.00"));
        film1.setInStock(0);
        film1.setImageUrl("imageUrl");
        final Optional<Film> film = Optional.of(film1);
        when(mockFilmRepository.findById(0L)).thenReturn(film);

        // Configure FilmRepository.save(...).
        final Film film2 = new Film();
        film2.setTitle("title");
        film2.setDirector("director");
        film2.setStars("stars");
        film2.setCategory("category");
        film2.setDescription("description");
        film2.setRating(0.0);
        film2.setPrice(new BigDecimal("0.00"));
        film2.setInStock(0);
        film2.setImageUrl("imageUrl");
        final Film entity = new Film();
        entity.setTitle("title");
        entity.setDirector("director");
        entity.setStars("stars");
        entity.setCategory("category");
        entity.setDescription("description");
        entity.setRating(0.0);
        entity.setPrice(new BigDecimal("0.00"));
        entity.setInStock(0);
        entity.setImageUrl("imageUrl");
        when(mockFilmRepository.save(entity)).thenReturn(film2);

        // Configure FilmDtoMapper.apply(...).
        final FilmDTO filmDTO1 = new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0,
                new BigDecimal("0.00"), 0, "imageUrl");
        final Film film3 = new Film();
        film3.setTitle("title");
        film3.setDirector("director");
        film3.setStars("stars");
        film3.setCategory("category");
        film3.setDescription("description");
        film3.setRating(0.0);
        film3.setPrice(new BigDecimal("0.00"));
        film3.setInStock(0);
        film3.setImageUrl("imageUrl");
        when(mockFilmDtoMapper.apply(film3)).thenReturn(filmDTO1);

        // Run the test
        final FilmDTO result = filmServiceUnderTest.editFilm(filmDTO, 0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testEditFilm_FilmRepositoryFindByIdReturnsAbsent() {
        // Setup
        final FilmDTO filmDTO = new FilmDTO(0L, "title", "director", "stars", "category", "description", 0.0,
                new BigDecimal("0.00"), 0, "imageUrl");
        when(mockFilmRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> filmServiceUnderTest.editFilm(filmDTO, 0L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void testDeleteFilm() {
        // Setup
        when(mockFilmRepository.existsById(0L)).thenReturn(false);

        // Run the test
        filmServiceUnderTest.deleteFilm(0L);

        // Verify the results
        verify(mockFilmRepository).deleteById(0L);
    }
}
