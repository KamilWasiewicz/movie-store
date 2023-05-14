package com.wasiewicz.onlineshop.filmshop.repository;

import com.wasiewicz.onlineshop.filmshop.model.Film;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    Optional<Film> findByTitle(String title);
    @Query("SELECT DISTINCT p.category FROM Film p")
    List<String> findDistinctCategories();
    @Query("SELECT f FROM Film f WHERE f.category LIKE %:category%")
    List<Film> findByCategory(@Param("category") String category, PageRequest pageable);
}
