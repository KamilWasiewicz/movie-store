package com.wasiewicz.onlineshop.filmshop.repository;

import com.wasiewicz.onlineshop.filmshop.model.Film;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class FilmRepositoryTest {

    @Autowired
    private FilmRepository filmRepository;

    @Test
    void testFindByTitle() {
        // given
        Film film = new Film(1L,
                "title",
                "director",
                "stars",
                "category",
                "description",
                10,
                BigDecimal.TWO,
                1,
                "image");

        filmRepository.save(film);

        // when
        Optional<Film> result = filmRepository.findByTitle("title");

        // then
        assertTrue(result.isPresent());
        assertEquals(film.getTitle(), result.get().getTitle());
        assertEquals(film.getDirector(), result.get().getDirector());
        assertEquals(film.getRating(), result.get().getRating());
    }
}
