package com.geniusee.cinema.dao;

import com.geniusee.cinema.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MovieDAO {

    Movie add(Movie movie);

    Movie update(Movie movie);

    boolean deleteById(Long id);

    Optional<Movie> findById(Long id);

    List<Movie> getAll();

    Page<Movie> fetchMoviesWhereDescriptionContains(String description, Pageable pageable);

    Page<Movie> fetchAllMoviesWhereNameStartsWith(String name, Integer page, Integer size, Pageable pageable);
}
